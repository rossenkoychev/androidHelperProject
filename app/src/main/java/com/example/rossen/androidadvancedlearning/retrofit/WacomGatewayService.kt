package com.example.rossen.androidadvancedlearning.retrofit

import com.google.gson.JsonObject

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Interface that contains all web services exposed by wacom server
 *
 * @author rosen.koychev
 */
interface WacomGatewayService {

    @POST("Payment/PrepareOrderInfo")
    fun createPaymentInfo(@Body enryInfo:JsonObject): Observable<Response<String>>

    @GET("Payment/CheckServer")
    fun checkServer(): Observable<Response<String>>

    @GET("Payment/QueryPurchases")
    fun queryPurchases(@Body entryInfo:JsonObject): Observable<Response<WacomPurchaseResult>>
}