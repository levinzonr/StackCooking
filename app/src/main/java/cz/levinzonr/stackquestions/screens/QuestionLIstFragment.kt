package cz.levinzonr.stackquestions.screens


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.stackquestions.R
import cz.levinzonr.stackquestions.model.QuestionResponce
import cz.levinzonr.stackquestions.presenter.ListPresenter
import cz.levinzonr.stackquestions.presenter.Presenter


/**
 * A simple [Fragment] subclass.
 */
class QuestionLIstFragment : Fragment(), ViewCallBacks<QuestionResponce> {

    lateinit var presenter: ListPresenter

    companion object {
        const val TAG = "QuestionsListFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        presenter = ListPresenter()
        presenter.fetchQuestionsPage()
        return inflater!!.inflate(R.layout.fragment_question_list, container, false)
    }

    override fun onLoadingStarted() {
        Log.d(TAG, "on Loading started")
    }

    override fun onLoadingFinished(result: QuestionResponce) {
        Log.d(TAG, "on Loading finished")
    }

    override fun onError() {
        Log.d(TAG, "on Error")
    }
}// Required empty public constructor
