package cz.levinzonr.stackquestions.persistence

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import cz.levinzonr.stackquestions.model.QuestionResponce
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

/**
 * Created by nomers on 3/15/18.
 */
class StackClient  {

    private val gson : Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()

    val service : StackApi = retrofit.create(StackApi::class.java)

    companion object {
        const val ROOT_URL = "https://api.stackexchange.com/2.2/"

        var instance: StackClient? = null

        fun instance() : StackClient {
            if (instance == null) {
                instance = StackClient()
            }
            return instance as StackClient
        }
    }

    fun fetchQuestionsPage(page: Int): Observable<QuestionResponce> {
        return service.fetchQuestionsPage(page)
    }

}