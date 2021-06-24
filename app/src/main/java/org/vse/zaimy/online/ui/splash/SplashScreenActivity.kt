package org.vse.zaimy.online.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.RemoteException
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.JsonSyntaxException
import com.my.tracker.MyTracker
import com.scottyab.rootbeer.RootBeer
import com.yandex.metrica.AppMetricaDeviceIDListener
import com.yandex.metrica.YandexMetrica
import org.vse.zaimy.online.Application
import org.vse.zaimy.online.Application.Companion.cardsCredit
import org.vse.zaimy.online.Application.Companion.cardsDebit
import org.vse.zaimy.online.Application.Companion.cardsInstallment
import org.vse.zaimy.online.R
import org.vse.zaimy.online.pojo.actual_backend.ActualBackendResponse
import org.vse.zaimy.online.pojo.date.DateResponse
import org.vse.zaimy.online.pojo.db.Card
import org.vse.zaimy.online.pojo.db.DbResponse
import org.vse.zaimy.online.pojo.db.Proposition
import org.vse.zaimy.online.ui.MainActivity
import org.vse.zaimy.online.ui.default_views.DefaultViewActivity
import org.vse.zaimy.online.ui.no_internet.NoInternetConnectionDialog
import org.vse.zaimy.online.ui.proposition.DetailActivity
import org.vse.zaimy.online.util.FirebaseAnalyticsUtil
import org.vse.zaimy.online.util.LocalSharedUtil
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.*


class SplashScreenActivity : AppCompatActivity(), AdvertisingClientOnLoadComplete, ActualDataView,
        NoInternetConnectionDialog.OnRefreshResponse, AppMetricaDeviceIDListener {

    private lateinit var actualBackendFolder: String
    private lateinit var color: String
    private lateinit var fcm: String
    private lateinit var advertisingId: String
    private lateinit var presenter: ActualDataPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        presenter = ActualDataPresenter(this, this)

        YandexMetrica.reportEvent("ourstart_app")
        //Handler(Looper.getMainLooper()).postDelayed({ openNextScreen() }, 200L)
    }

    private fun openNextScreen() {
        var nextIntent: Intent
        if (intent.getStringExtra("type_push") == null) {
            nextIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        } else {
            if (intent.getStringExtra("id") != null) {
                try {
                    var proposition: Proposition? = null
                    val id: String = intent.getStringExtra("id") as String
                    when (intent.getStringExtra("type_push")) {
                        "cards_credit" -> {
                            proposition = Application.data.cards[0].cardsCredit[id.toInt()]
                        }
                        "cards_debit" -> {
                            proposition = Application.data.cards[1].cardsDebit[id.toInt()]
                        }
                        "cards_installment" -> {
                            proposition = Application.data.cards[2].cardsInstallment[id.toInt()]
                        }
                        "credits" -> {
                            proposition = Application.data.credits[id.toInt()]
                        }
                        "loans" -> {
                            proposition = Application.data.loans[id.toInt()]
                        }
                    }

                    if (proposition != null) {
                        nextIntent = Intent(this@SplashScreenActivity, DetailActivity::class.java)
                        nextIntent.putExtra("proposition", proposition)
                    } else {
                        nextIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    }
                } catch (e: java.lang.Exception) {
                    nextIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                }
            } else {
                nextIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                nextIntent.putExtra("type_push", intent.getStringExtra("type_push"))
            }
        }
        finish()
        startActivity(nextIntent)

    }


    override fun onPostResume() {
        super.onPostResume()
        val cacheExpiration = 3600L

        try {
            Application.firebaseRemoteConfig.fetch(cacheExpiration)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            Application.firebaseRemoteConfig.fetchAndActivate()

                        val text = Application.firebaseRemoteConfig.getString("color")
                        color = text
                        AdvertisingClientAsyncTask(this, this)
                                .execute()
                    }
        } catch (e: Exception) {
            Log.d("Seting_error-", "1")
        }
    }

    @SuppressLint("HardwareIds")
    fun createAPIUrl() {
        if (isSIMInserted(this)) {
            if (isOnline(this)) {
                val p1: String = getSimCountryIso()
                val p2: String = color
                val p3: String = isUserRooted()
                val p4: String = Locale.getDefault().country
                val p5: String = "6ad789c9-43b0-4be5-b8da-70f9a5803d3c"
                val p6: String = Settings.Secure.getString(
                        contentResolver,
                        Settings.Secure.ANDROID_ID
                )
                val p7: String = fcm
                val p8: String = advertisingId
                val p9: String = MyTracker.getInstanceId(this)

                val p10: String =
                        packageManager.getPackageInfo(packageName, 0).versionCode.toString()

                Application.aff_sub1 = p9
                Application.aff_sub3 = fcm
                Application.aff_sub4 = "not_available"
                Application.aff_sub5 = p8
                Application.aff_sub2 = "not_available"

                YandexMetrica.requestAppMetricaDeviceID(this)

                val referrerClient: InstallReferrerClient =
                        InstallReferrerClient.newBuilder(this).build()
                referrerClient.startConnection(object : InstallReferrerStateListener {
                    override fun onInstallReferrerSetupFinished(responseCode: Int) {
                        when (responseCode) {
                            InstallReferrerClient.InstallReferrerResponse.OK -> try {
                                val response: ReferrerDetails = referrerClient.installReferrer
                                val referrer: String = response.installReferrer

                                val maps: Map<String, String> = splitQuery(referrer)

                                if (maps["gclid"] != null) {
                                    Application.aff_sub2 = maps["gclid"].toString()
                                } else {
                                    if (maps["utm_source"] != null && maps["utm_medium"] != null)
                                        Application.aff_sub2 =
                                                maps["utm_source"].toString() + "_" + maps["utm_medium"].toString()
                                }
                            } catch (e: RemoteException) {
                                e.printStackTrace()
                            }
                            InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
                            }
                            InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                            }
                        }
                        referrerClient.endConnection()
                    }

                    override fun onInstallReferrerServiceDisconnected() {
                        println()
                    }


                })

                presenter.getActualBackend(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)
            } else {
                NoInternetConnectionDialog.display(supportFragmentManager, this)
            }
        } else {
            val intent = Intent(this@SplashScreenActivity, DefaultViewActivity::class.java)
            val cn = intent.component
            val mainIntent: Intent = Intent.makeRestartActivityTask(cn)
            startActivity(mainIntent)
        }
    }

    @Throws(UnsupportedEncodingException::class)
    fun splitQuery(url: String): Map<String, String> {
        val query_pairs: MutableMap<String, String> = LinkedHashMap()
        val query: String = url
        val pairs = query.split("&").toTypedArray()
        for (pair in pairs) {
            val idx = pair.indexOf("=")
            query_pairs[URLDecoder.decode(pair.substring(0, idx), "UTF-8")] =
                    URLDecoder.decode(pair.substring(idx + 1), "UTF-8")
        }
        return query_pairs
    }

    private fun isSIMInserted(context: Context): Boolean {
        return TelephonyManager.SIM_STATE_ABSENT != (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).simState
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    private fun isUserRooted(): String {
        val rootBeer = RootBeer(this)
        return if (rootBeer.isRooted) {
            "granted"
        } else {
            null.toString()
        }
    }

    private fun getSimCountryIso(): String {
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (tm.simCountryIso == null || tm.simCountryIso == "")
            null.toString()
        else
            tm.simCountryIso
    }

    override fun onAdvertisingClientLoadComplete(result: String) {
        FirebaseMessaging.getInstance().token.addOnSuccessListener { instanceIdResult ->
            fcm = instanceIdResult
            advertisingId = result
            createAPIUrl()
        }
    }

    private fun isDateActual(date: String): Boolean {
        return LocalSharedUtil().getLastUpdateDBDate(this).equals(date)
    }

    override fun onActualBackendComplete(response: ActualBackendResponse) {
        //response.actualbackend = null
        if (response.actualbackend == null || response.actualbackend.equals("null") || response.actualbackend.contains(
                        ".htm"
                )
        ) {
            val eventParameters: MutableMap<String, Any> = HashMap()
            eventParameters["GAID"] = Application.aff_sub5

            YandexMetrica.reportEvent("actualbackend_null", eventParameters)

            MyTracker.trackEvent("actualbackend_null")

            val params = Bundle()
            FirebaseAnalyticsUtil.getInstance(this)!!.logEvent("actualbackend_null", params)


            val intent = Intent(this@SplashScreenActivity, DefaultViewActivity::class.java)
            val cn = intent.component
            val mainIntent: Intent = Intent.makeRestartActivityTask(cn)
            startActivity(mainIntent)
        } else {
            actualBackendFolder = response.actualbackend
            presenter.getActualDate(actualBackendFolder)
            YandexMetrica.reportEvent("requestdate")

            MyTracker.trackEvent("requestdate")
            val params = Bundle()
            FirebaseAnalyticsUtil.getInstance(this)!!.logEvent("requestdate", params)
        }
    }

    override fun onActualDateComplete(response: DateResponse) {
        if (isDateActual(response.date)) {
            try {
                Application.data = LocalSharedUtil().getDBDate(this)
                initDataCards()
                openNextScreen()
            } catch (e: JsonSyntaxException) {
                e.printStackTrace()
                presenter.getActualDB(actualBackendFolder)
                YandexMetrica.reportEvent("requestdb")
                MyTracker.trackEvent("requestdb")
                val params = Bundle()
                FirebaseAnalyticsUtil.getInstance(this)!!.logEvent("requestdb", params)
            }
        } else {
            LocalSharedUtil().setLastUpdateDBDate(response.date, this)
            presenter.getActualDB(actualBackendFolder)
            YandexMetrica.reportEvent("requestdb")
            MyTracker.trackEvent("requestdb")
            val params = Bundle()
            FirebaseAnalyticsUtil.getInstance(this)!!.logEvent("requestdb", params)
        }
    }

    override fun onActualDBComplete(response: DbResponse) {
        LocalSharedUtil().setDBDate(response, this)
        Application.data = response
        initDataCards()
        openNextScreen()
    }

    private fun initDataCards() {
        if (Application.data.cards != null)
            for (card: Card in Application.data.cards) {
                if (card.cardsDebit != null)
                    cardsDebit = card.cardsDebit
                if (card.cardsCredit != null)
                    cardsCredit = card.cardsCredit
                if (card.cardsInstallment != null)
                    cardsInstallment = card.cardsInstallment
            }
    }

    override fun onInternetError() {
        NoInternetConnectionDialog.display(supportFragmentManager, this)
    }

    override fun refreshResponse() {
        createAPIUrl()
    }

    override fun onLoaded(p0: String?) {
        Application.aff_sub7 = p0.toString()
    }

    override fun onError(p0: AppMetricaDeviceIDListener.Reason) {
        println(p0)
    }
}
