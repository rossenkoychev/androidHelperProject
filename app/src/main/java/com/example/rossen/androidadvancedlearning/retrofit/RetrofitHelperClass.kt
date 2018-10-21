package com.example.rossen.androidadvancedlearning.retrofit

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import retrofit2.Response

/**
 * TODO: Add file description
 *
 * @author rosen.koychev
 */
class RetrofitHelperClass {

    val queryParamsObservable: Subject<String> = PublishSubject.create()
    val purchaseResultObservable: Subject<WacomPurchaseResult> = PublishSubject.create()
    val skuDetailsObservable: Subject<WacomSkuDetailsResult> = PublishSubject.create()
    val isServerAvailable: Subject<Boolean> = PublishSubject.create()

    fun querySkuDetails() {
        var disposable: Disposable? = null

        disposable = WacomServerClient.querySkuDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            val skuDetails = extractSkuDetails(response)
                            skuDetailsObservable.onNext(WacomSkuDetailsResult(skuDetails, 0))
                            disposable?.dispose()
                        },
                        { _ ->
                            skuDetailsObservable.onNext(WacomSkuDetailsResult(listOf(), 0))
                            disposable?.dispose()
                        })
    }

    fun queryPurchaseDetails() {
        //  var jsonParameters= JsonObject(stringParameters)
        val rootObject = JsonObject()
        rootObject.addProperty("SkuId", "com.wacom.bamboopapertab.pack.prostyles")
        rootObject.addProperty("Price", "1.00")
        rootObject.addProperty("Currency", "JPY")
        rootObject.addProperty("WacomId", "WacomUserId123")

//        val mock = queryPurchaseDetailsMock("com.wacom.bamboopapertab.pack.prostyles", "description body", "1.00",
//                "JPY")
        var disposable: Disposable? = null

        disposable = WacomServerClient.createPaymentInfo(rootObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            if (response.isSuccessful) {
                                val result = response.body()
                                result?.let {
                                    queryParamsObservable.onNext(response.body().toString())
                                    // queryParamsObservable.onNext(mock)
                                    disposable?.dispose()
                                }
                            }
                        },
                        { _ ->
                            disposable?.dispose()
                        })
    }

    fun checkServerAvailability() {
        var disposable: Disposable? = null
        disposable = WacomServerClient.checkServer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            if (response.isSuccessful) {
                                isServerAvailable.onNext(true)
                                disposable?.dispose()
                            }
                        },
                        { _ ->
                            isServerAvailable.onNext(false)
                            disposable?.dispose()

                        })
    }

    //region Private Methods
    private fun extractSkuDetails(response: Response<JsonArray>?): List<SkuDetails> {
        response?.let { _ ->
            if (response.isSuccessful) {
                val result = response.body()
                result?.let {
                    return List(result.size()) { i -> SkuDetails(result.get(i).toString()) }
                }
            }
        }
        return emptyList()
    }
}

class WacomPurchaseResult {

}

class SkuDetails(skuDetails: String) {

}

class WacomSkuDetailsResult(skuDetails: List<SkuDetails>, responseCode: Int) {

}
