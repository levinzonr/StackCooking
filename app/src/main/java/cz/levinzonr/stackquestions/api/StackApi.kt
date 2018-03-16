package cz.levinzonr.stackquestions.api

import cz.levinzonr.stackquestions.model.QuestionResponce
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by nomers on 3/15/18.
 */
interface StackApi {

    @GET("questions?filter=withbody&order=desc&sort=creation&site=cooking&pagesize=10")
    fun fetchQuestionsPage(@Query("page") page: Int) : Observable<QuestionResponce>

}