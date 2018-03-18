package cz.levinzonr.stackquestions.screens.questiondetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cz.levinzonr.stackquestions.R
import cz.levinzonr.stackquestions.model.Question

import kotlinx.android.synthetic.main.activity_question_detail.*

class QuestionDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_detail)
        setSupportActionBar(toolbar)

        mountDetailFragment(intent.getParcelableExtra(EXTRA_QUESTION))

    }

    private fun mountDetailFragment(question: Question) {
        val fm = supportFragmentManager
        if (fm.findFragmentById(R.id.container) == null) {
            fm.beginTransaction()
                    .replace(R.id.container, QuestionDetailFragment.newInstance(question))
                    .commit()
        }
    }

    companion object {

        private const val EXTRA_QUESTION = "ExtraQuestion"
        private const val TAG = "QuestionDetailActivity"

        fun startAsIntent(context: Context, question: Question) {
            val intent = Intent(context, QuestionDetailActivity::class.java)
            intent.putExtra(EXTRA_QUESTION, question)
            context.startActivity(intent)
        }

    }

}
