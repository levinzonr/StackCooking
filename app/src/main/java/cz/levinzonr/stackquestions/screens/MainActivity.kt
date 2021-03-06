package cz.levinzonr.stackquestions.screens

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import cz.levinzonr.stackquestions.R
import cz.levinzonr.stackquestions.model.Question
import cz.levinzonr.stackquestions.screens.questiondetail.QuestionDetailActivity
import cz.levinzonr.stackquestions.screens.questiondetail.QuestionDetailFragment
import cz.levinzonr.stackquestions.screens.questionslist.QuestionListFragment

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), QuestionListFragment.OnListFragmentInteractionListener {

    private var isEmptyView: Boolean = true

    companion object {
        private const val SAVED_IS_EMPTY = "isEmptyView"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        if (savedInstanceState != null) {
            val empty = savedInstanceState.getBoolean(SAVED_IS_EMPTY)
            setEmptyView(empty)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(SAVED_IS_EMPTY, isEmptyView)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (detail_container != null) {
            if (supportFragmentManager.backStackEntryCount == 0) {
                setEmptyView(true)
            }
        }
    }

    private fun setEmptyView(value: Boolean) {
        isEmptyView = value
        if (!isEmptyView) {
            empty_view?.visibility = View.GONE
            detail_container?.visibility = View.VISIBLE
        } else {
            empty_view?.visibility = View.VISIBLE
            detail_container?.visibility = View.GONE
        }
    }

    override fun onQuestionSelected(question: Question) {
        if (detail_container != null) {
            setEmptyView(false)
            supportFragmentManager.beginTransaction()
                        .replace(R.id.detail_container, QuestionDetailFragment.newInstance(question), "Tag")
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()
        } else {
            QuestionDetailActivity.startAsIntent(this, question)
        }
    }
}
