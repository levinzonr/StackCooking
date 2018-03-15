package cz.levinzonr.stackquestions.model

import java.util.*

/**
 * Created by nomers on 3/15/18.
 */
class Question(
        val questionId: Int,
        val title: String,
        val answersCount: Int,
        val isAnswered: Boolean,
        val link: String,
        val tags: Array<String>,
        val viewCount: Int
) {
    override fun toString(): String {
        return "Question(questionId=$questionId, title='$title', answersCount=$answersCount, isAnswered=$isAnswered, link='$link', tags=${Arrays.toString(tags)}, viewCount=$viewCount)"
    }
}