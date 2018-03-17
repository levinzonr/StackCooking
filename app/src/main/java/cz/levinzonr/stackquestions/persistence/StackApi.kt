package cz.levinzonr.stackquestions.persistence

import cz.levinzonr.stackquestions.model.QuestionResponce
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by nomers on 3/15/18.
 */
interface StackApi {

    @GET("questions?filter=withbody&order=desc&sort=creation&site=cooking&pagesize=5")
    fun fetchQuestionsPage(@Query("page") page: Int) : Observable<QuestionResponce>

}