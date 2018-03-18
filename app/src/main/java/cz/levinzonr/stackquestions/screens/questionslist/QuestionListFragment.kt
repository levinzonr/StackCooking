package cz.levinzonr.stackquestions.screens.questionslist


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import cz.levinzonr.stackquestions.R
import cz.levinzonr.stackquestions.persistence.CacheProvider
import cz.levinzonr.stackquestions.model.Question
import cz.levinzonr.stackquestions.model.QuestionResponce
import cz.levinzonr.stackquestions.presenter.ListPresenter
import cz.levinzonr.stackquestions.screens.ViewCallBacks
import cz.levinzonr.stackquestions.screens.viewutils.InfiniteScrollListener
import cz.levinzonr.trendee.screens.artistslist.VerticalSpaceDecoration


/**
 * A simple [Fragment] subclass.
 */
class QuestionListFragment : Fragment(), ViewCallBacks<QuestionResponce>,
        QuestionListAdapter.ItemClickListener, InfiniteScrollListener.InfiniteScrollCallbacks{

    lateinit var presenter: ListPresenter
    lateinit var adapter: QuestionListAdapter
    lateinit var questionResponce: QuestionResponce
    lateinit var recyclerView: RecyclerView
    lateinit var listener: OnListFragmentInteractionListener
    var savedPosition: Int? = null
    lateinit var scrollListener: InfiniteScrollListener

    interface OnListFragmentInteractionListener {
        fun onQuestionSelected(question: Question)

    }

    companion object {
        const val TAG = "QuestionsListFragment"
        const val SAVE_POS = "SavedScrollPosition"
        const val START_PAGE = 1
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        presenter = ListPresenter()
        adapter = QuestionListAdapter(context, this)
        val view = inflater!!.inflate(R.layout.fragment_question_list, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        initRecyclerView(recyclerView)
        presenter.attachView(this)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as OnListFragmentInteractionListener
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val manager = recyclerView.layoutManager as LinearLayoutManager
        outState?.putInt(SAVE_POS, manager.findLastVisibleItemPosition())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            savedPosition  = savedInstanceState.getInt(SAVE_POS)
            Toast.makeText(context, savedPosition.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemSelected(question: Question) {
        listener.onQuestionSelected(question)
    }

    override fun onRepeatButtonClicked() {
        presenter.getQuestionsPage(scrollListener.currentPage)
        recyclerView.post {
            adapter.error = false
        }
        Toast.makeText(context, "Repeatin..${scrollListener.currentPage}", Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(VerticalSpaceDecoration())
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        scrollListener = InfiniteScrollListener(this, layoutManager)
        recyclerView.addOnScrollListener(scrollListener)
    }

    override fun onLoadMore(pageToLoad: Int) {
        Log.d(TAG, "load next $pageToLoad")
        if (pageToLoad == START_PAGE || questionResponce.hasMore) {
            presenter.getQuestionsPage(pageToLoad)
        } else {
            Log.d(TAG, "End of the list")
            Toast.makeText(context, "End of the list", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onLoadingStarted() {
        Log.d(TAG, "on Loading started")
        recyclerView.post({
            adapter.isLoading = true
        })
    }

    override fun restoreFromCache(data: CacheProvider.CachedData) {
        questionResponce = data.items.last()
        scrollListener.currentPage = data.latstPage
        recyclerView.post {
            val allItems = ArrayList<Question>()
            for (item in data.items) {
                allItems.addAll(item.items)
            }
            adapter.setItems(allItems)
        }
        Log.d(TAG, "Restored from cache")
        if (savedPosition != null) {
            recyclerView.scrollToPosition(savedPosition!!)
        }
    }

    override fun onLoadingFinished(result: QuestionResponce) {
        Log.d(TAG, "on Loading finished")
        questionResponce = result
        recyclerView.post({
            adapter.isLoading = false
            adapter.error = false
            adapter.setItems(result.items)
        })    }

    override fun onError() {
        Log.d(TAG, "on Error")
        recyclerView.post {
            adapter.isLoading = false
            adapter.error = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}// Required empty public constructor
