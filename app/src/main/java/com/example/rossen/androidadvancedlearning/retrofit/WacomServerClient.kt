package com.example.rossen.androidadvancedlearning.retrofit

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Class containing setings and web cals used to reach wacom server
 *
 * @author rosen.koychev
 */
object WacomServerClient {

    private const val WACOM_SKU_DETAILS_BASE_URL = "https://wacompaymentalipay.azurewebsites.net/api/"
    private val wacomSkuService: WacomSkuService
    private val wacomGatewayService: WacomGatewayService

    init {
        val gson = GsonBuilder()
                .setLenient()
                .create()
        val wacomRetrofit = Retrofit.Builder()
                .baseUrl(WACOM_SKU_DETAILS_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()

        wacomSkuService = wacomRetrofit.create(WacomSkuService::class.java)
        wacomGatewayService = wacomRetrofit.create(WacomGatewayService::class.java)

    }

    fun querySkuDetails(): Observable<Response<JsonArray>> {
        return wacomSkuService.querySkuDetails()
    }

    fun createPaymentInfo(entryInfo: JsonObject): Observable<Response<String>> {
        return wacomGatewayService.createPaymentInfo(entryInfo)
    }

    fun checkServer(): Observable<Response<String>> {
        return wacomGatewayService.checkServer()
    }

    fun queryPurchases(entryInfo: JsonObject): Observable<Response<WacomPurchaseResult>> {
        return wacomGatewayService.queryPurchases(entryInfo)
    }
}