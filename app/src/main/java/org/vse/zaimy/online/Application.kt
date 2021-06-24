package org.vse.zaimy.online

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.my.tracker.MyTracker
import com.yandex.metrica.YandexMetrica

import com.yandex.metrica.YandexMetricaConfig
import org.vse.zaimy.online.pojo.db.DbResponse
import org.vse.zaimy.online.pojo.db.Proposition


class Application : Application() {
    companion object {
        lateinit var config: YandexMetricaConfig
        lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
        lateinit var data: DbResponse
        var cardsCredit: List<Proposition>? = null
        var cardsDebit: List<Proposition>? = null
        var cardsInstallment: List<Proposition>? = null
        lateinit var aff_sub1 : String
        lateinit var aff_sub2 : String
        lateinit var aff_sub3 : String
        lateinit var aff_sub4 : String
        lateinit var aff_sub5 : String
        var aff_sub7 : String = ""
    }

    override fun onCreate() {
        super.onCreate()
        // Creating an extended library configuration.
        var config =  YandexMetricaConfig.newConfigBuilder("6ad789c9-43b0-4be5-b8da-70f9a5803d3c").build()

        config.userProfileID
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(applicationContext, config)
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(this)


        MyTracker.initTracker("92354547247968730109", this)
        FirebaseApp.initializeApp(this)
        try {
            firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
            val configSettings = FirebaseRemoteConfigSettings.Builder().build()
            firebaseRemoteConfig.setConfigSettingsAsync(configSettings) //Если используете старую версию и метод setConfigSettingsAsync из строки выше отсутствует
        } catch (e: Exception) {
            Log.d("Seting_error-", "1")
        }
    }
}