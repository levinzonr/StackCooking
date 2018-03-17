package cz.levinzonr.stackquestions.screens.questiondetail


import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.text.Spanned
import android.util.Xml
import android.view.*
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.squareup.picasso.Picasso

import cz.levinzonr.stackquestions.R
import cz.levinzonr.stackquestions.model.Question
import kotlinx.android.synthetic.main.fragment_question_detail.*
import kotlinx.android.synthetic.main.item_question.*
import java.net.Proxy


/**
 * A simple [Fragment] subclass.
 */
class QuestionDetailFragment : Fragment() {

    companion object {

        private const val BUNDLE_QUESTION = "QuestionToDisplay"

        fun newInstance(question: Question) : QuestionDetailFragment {
            val fragment = QuestionDetailFragment()
            val bundle =  Bundle()
            bundle.putParcelable(BUNDLE_QUESTION, question)
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater!!.inflate(R.layout.fragment_question_detail, container, false)
    }

    fun String.fromHtml(): Spanned {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            return Html.fromHtml(this)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val question : Question = arguments.getParcelable(BUNDLE_QUESTION)
        author_name.text = question.owner?.displayName
        questiondetail_title.text = question.title
        question_body.text = question.body.fromHtml()
        Picasso.get().load(question.owner?.profileImage).into(author_image)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val question : Question = arguments.getParcelable(BUNDLE_QUESTION)
        return when (item?.itemId) {
            R.id.action_share -> {
                val shareIntent = Intent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, question.link)
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_msg)))
                true
            }
           R.id.action_browser -> {
               val viewIntent = Intent()
               viewIntent.action = Intent.ACTION_VIEW
               viewIntent.data = Uri.parse(question.link)
               startActivity(viewIntent)
               true
           }
           else -> {
               throw IllegalArgumentException("Not supposed to happen")
           }
        }
    }

}// Required empty public constructor
