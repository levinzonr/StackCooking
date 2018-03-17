package cz.levinzonr.stackquestions.screens

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import cz.levinzonr.stackquestions.R
import cz.levinzonr.stackquestions.model.Question
import cz.levinzonr.stackquestions.screens.questiondetail.QuestionDetailActivity
import cz.levinzonr.stackquestions.screens.questionslist.QuestionListFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), QuestionListFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQuestionSelected(question: Question) {
        QuestionDetailActivity.startAsIntent(this, question)
    }
}
