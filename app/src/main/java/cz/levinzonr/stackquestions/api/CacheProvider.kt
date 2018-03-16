package cz.levinzonr.stackquestions.api

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import cz.levinzonr.stackquestions.model.Question
import cz.levinzonr.stackquestions.model.QuestionResponce
import java.io.File

/**
 * Created by nomers on 3/16/18.
 */
class CacheProvider(val context: Context) {
    val gson: Gson = Gson()
    val dir: File = context.cacheDir
    var cachedData: CachedData? = null
    init {
        val file = File(dir, FILE_NAME)
        if (file.exists()) {
            val json = String(file.readBytes())
            cachedData = gson.fromJson(json, CachedData::class.java)
        }

    }
    companion object {
        const val FILE_NAME = "CachedData"
        const val TAG = "CacheProvider"
    }

    inner class CachedData(
            var lastUpdated: Long,
            var latstPage: Int,
            var items: ArrayList<QuestionResponce>
    )


    fun writePage(page: Int, response:QuestionResponce) {
        val file = File(dir, FILE_NAME)
        if (!file.exists()) {
            val list = ArrayList<QuestionResponce>()
            list.add(response)
            cachedData = CachedData(System.currentTimeMillis(), page, list)
        } else {
            cachedData?.items?.add(response)
            cachedData?.lastUpdated = System.currentTimeMillis()
            cachedData?.latstPage = page
        }
        val json = gson.toJson(cachedData)
        file.writeBytes(json.toByteArray())
    }

    fun getPage(page: Int): QuestionResponce {
        val file = File(dir.path, page.toString())
        val json = String(file.readBytes())
        val responce = gson.fromJson(json, QuestionResponce::class.java)
        return responce
    }

}