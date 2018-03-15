package cz.levinzonr.stackquestions.screens.questionslist


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.stackquestions.R
import cz.levinzonr.stackquestions.model.QuestionResponce
import cz.levinzonr.stackquestions.presenter.ListPresenter
import cz.levinzonr.stackquestions.screens.ViewCallBacks
import cz.levinzonr.trendee.screens.artistslist.VerticalSpaceDecoration


/**
 * A simple [Fragment] subclass.
 */
class QuestionLIstFragment : Fragment(), ViewCallBacks<QuestionResponce> {

    lateinit var presenter: ListPresenter
    lateinit var adapter: QuestionListAdapter

    companion object {
        const val TAG = "QuestionsListFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        presenter = ListPresenter()
        adapter = QuestionListAdapter(context)
        val view = inflater!!.inflate(R.layout.fragment_question_list, container, false)
        initRecyclerView(view.findViewById(R.id.recycler_view))
        presenter.attachView(this)
        presenter.getQuestionsPage()
        return view
    }

    fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(VerticalSpaceDecoration())
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onLoadingStarted() {
        Log.d(TAG, "on Loading started")
    }

    override fun onLoadingFinished(result: QuestionResponce) {
        Log.d(TAG, "on Loading finished")
        adapter.setItems(result.items)
    }

    override fun onError() {
        Log.d(TAG, "on Error")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}// Required empty public constructor
