package cz.levinzonr.stackquestions.screens.questiondetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import cz.levinzonr.stackquestions.R
import cz.levinzonr.stackquestions.model.Question

import kotlinx.android.synthetic.main.activity_question_detail.*

class QuestionDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_detail)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    companion object {

        fun startAsIntent(context: Context, question: Question) {
            val intent = Intent(context, QuestionDetailActivity::class.java)
            context.startActivity(intent)
        }

    }

}
