package org.vse.zaimy.online.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.my.tracker.MyTracker
import com.yandex.metrica.YandexMetrica
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.vse.zaimy.online.api.ApiFactory
import org.vse.zaimy.online.api.ApiService
import org.vse.zaimy.online.util.FirebaseAnalyticsUtil
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ActualDataPresenter(view: ActualDataView, ctx: Context) {

    private var actualDataView: ActualDataView = view
    private var contex: Context = ctx

    @SuppressLint("CheckResult")
    fun getActualBackend(
        p1: String, p2: String, p3: String, p4: String, p5: String,
        p6: String, p7: String, p8: String, p9: String, p10: String
    ) {
        val apiService: ApiService = ApiFactory.createRetrofit(ApiService::class)

        apiService.getActualBackend(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                actualDataView.onActualBackendComplete(response.body()!!)
            }) { throwable ->
                if (throwable is SocketTimeoutException || throwable is UnknownHostException || throwable is IOException)
                    actualDataView.onInternetError()
                YandexMetrica.reportEvent("backend_unavailable")
                MyTracker.trackEvent("backend_unavailable")
                val params = Bundle()
                FirebaseAnalyticsUtil.getInstance(contex)!!.logEvent("backend_unavailable", params)
            }
    }

    @SuppressLint("CheckResult")
    fun getActualDB(actualbackend: String) {
        val apiService: ApiService = ApiFactory.createRetrofit(ApiService::class)

        apiService.getActualDB(actualbackend)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                actualDataView.onActualDBComplete(response.body()!!)
            }) { throwable ->
                if (throwable is SocketTimeoutException || throwable is UnknownHostException)
                    actualDataView.onInternetError()
                YandexMetrica.reportEvent("backend_unavailable")
                MyTracker.trackEvent("backend_unavailable")
                val params = Bundle()
                FirebaseAnalyticsUtil.getInstance(contex)!!.logEvent("backend_unavailable", params)
            }
    }

    @SuppressLint("CheckResult")
    fun getActualDate(actualbackend: String) {
        val apiService: ApiService = ApiFactory.createRetrofit(ApiService::class)

        apiService.getActualDate(actualbackend)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                actualDataView.onActualDateComplete(response.body()!!)
            }) { throwable ->
                if (throwable is SocketTimeoutException || throwable is UnknownHostException)
                    actualDataView.onInternetError()
                YandexMetrica.reportEvent("backend_unavailable")
                MyTracker.trackEvent("backend_unavailable")
                val params = Bundle()
                FirebaseAnalyticsUtil.getInstance(contex)!!.logEvent("backend_unavailable", params)
            }
    }

}