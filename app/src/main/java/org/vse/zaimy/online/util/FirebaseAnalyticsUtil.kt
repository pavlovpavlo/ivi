package org.vse.zaimy.online.util

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseAnalyticsUtil {

    companion object {

        private var mFirebaseAnalytics: FirebaseAnalytics? = null
        fun getInstance(context: Context?): FirebaseAnalytics? {
            if (mFirebaseAnalytics == null) {
                mFirebaseAnalytics = FirebaseAnalytics.getInstance(context!!)
            }
            return mFirebaseAnalytics
        }
    }

    fun sendDefaultEvent(args: Map<String?, String?>, event_name: String?, context: Context?) {
        val params = Bundle()
        for ((key, value) in args) params.putString(key, value)
        params.putString("first", "commit")
        getInstance(context)!!.logEvent(event_name!!, params)
    }
}