package cz.levinzonr.stackquestions.model

/**
 * Created by nomers on 3/15/18.
 */
class QuestionResponce(
        val items: List<Question>,
        val hasMore: Boolean,
        val quotaMax: Int,
        val quotaRemaining: Int
        ) {
    override fun toString(): String {
        return "QuestionResponce(items=$items, hasMore=$hasMore, quotaMax=$quotaMax, quotaRemaining=$quotaRemaining)"
    }
}