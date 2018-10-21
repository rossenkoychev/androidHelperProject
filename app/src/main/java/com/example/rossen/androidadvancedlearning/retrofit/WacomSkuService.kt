package com.example.rossen.androidadvancedlearning.retrofit

import com.google.gson.JsonArray
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

/**
 * TODO: Add file description
 *
 * @author rosen.koychev
 */
interface WacomSkuService {

    @GET("GetProducts")
    fun querySkuDetails(): Observable<Response<JsonArray>>
}