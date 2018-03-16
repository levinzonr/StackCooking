package cz.levinzonr.stackquestions.model

import java.util.*

/**
 * Created by nomers on 3/15/18.
 */
class Question(
        val questionId: Int,
        val title: String,
        val answerCount: Int,
        val isAnswered: Boolean,
        val link: String,
        val tags: Array<String>,
        val viewCount: Int,
        val owner: User?
) {



    inner class User(
            val userId: Int?,
            val displayName: String?,
            val profileImage: String?


    ) {
        override fun toString(): String {
            return "User(userId=$userId, displayName=$displayName, profileImage=$profileImage)"
        }
    }

    override fun toString(): String {
        return "Question(questionId=$questionId, title='$title', answerCount=$answerCount, " +
                "isAnswered=$isAnswered, link='$link', tags=${Arrays.toString(tags)}, " +
                "viewCount=$viewCount, owner=$owner)"
    }
}