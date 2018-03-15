package cz.levinzonr.stackquestions.api

import cz.levinzonr.stackquestions.model.QuestionResponce
import retrofit2.http.GET
import rx.Observable

/**
 * Created by nomers on 3/15/18.
 */
interface StackApi {

    @GET("questions?filter=withbody&order=desc&sort=creation&site=cooking&pagesize=5&page=1")
    fun fetchQuestionsPage() : Observable<QuestionResponce>

}