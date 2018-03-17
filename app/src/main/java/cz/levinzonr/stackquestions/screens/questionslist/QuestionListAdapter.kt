package cz.levinzonr.stackquestions.screens.questionslist

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.squareup.picasso.Picasso
import cz.levinzonr.stackquestions.R
import cz.levinzonr.stackquestions.model.Question
import kotlinx.android.synthetic.main.item_error.view.*
import kotlinx.android.synthetic.main.item_loading_indicator.view.*
import kotlinx.android.synthetic.main.item_question.view.*

/**
 * Created by nomers on 3/15/18.
 */
class QuestionListAdapter(private val context: Context, private val listener: ItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val items: ArrayList<Question> = ArrayList()

    var error: Boolean = false
    set(value){
        field = value
        notifyDataSetChanged()
    }

    var isLoading: Boolean = false
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onItemSelected(question: Question)
        fun onRepeatButtonClicked()
    }

    companion object {
        const val VIEW_TYPE_LOADER = 1
        const val VIEW_TYPE_ITEM = 2
        const val VIEW_TYPE_ERROR = 3
    }

    inner class ErrorHolder(val view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.button_repeat.setOnClickListener({
                listener.onRepeatButtonClicked()
            })
        }
    }

    inner class LoaderHolder(view: View) : RecyclerView.ViewHolder(view) {
        val progressBar: ProgressBar = view.progressbar
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(question: Question) {

            view.setOnClickListener {
                listener.onItemSelected(question)
            }

            view.question_title.text = question.title
            view.question_times_viewed.text = question.viewCount.toString()
            if (question.owner != null) {
                view.question_author_name.text = question.owner.displayName
                Picasso.get().load(question.owner.profileImage).into(view.question_author_image)
            }
            if (question.isAnswered) {
              view.question_status_label.visibility = View.VISIBLE
              view.question_status_icon.visibility = View.VISIBLE

            } else {
                view.question_status_label.visibility = View.INVISIBLE
                view.question_status_icon.visibility = View.INVISIBLE
            }
            view.question_times_answered.text = question.answerCount.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_ITEM ->  ViewHolder(layoutInflater.inflate(R.layout.item_question, parent, false))
            VIEW_TYPE_LOADER -> LoaderHolder(layoutInflater.inflate(R.layout.item_loading_indicator, parent, false))
            VIEW_TYPE_ERROR -> ErrorHolder(layoutInflater.inflate(R.layout.item_error, parent, false))
            else -> throw IllegalArgumentException()

        }
    }

    fun setItems(list: List<Question>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size + 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as? ViewHolder)?.bindView(items[position])
        if (holder is LoaderHolder) {
            if (isLoading)
                holder.progressBar.visibility = View.VISIBLE

            else
                holder.progressBar.visibility = View.GONE
        }
        if (holder is ErrorHolder) {
            if (error) {
                holder.view.visibility = View.VISIBLE
            } else {
                holder.view.visibility = View.GONE
            }
        }



    }

    override fun getItemViewType(position: Int): Int {
        if (position == items.size) {
            return VIEW_TYPE_LOADER
        } else if (position == items.size + 1) {
            return VIEW_TYPE_ERROR
        }
        return VIEW_TYPE_ITEM
    }
}