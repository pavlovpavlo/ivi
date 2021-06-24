package org.vse.zaimy.online.util

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import org.vse.zaimy.online.pojo.db.DbResponse


class LocalSharedUtil {
    fun setLastUpdateDBDate(value: String?, context: Context?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString("ivi_zaim_last_update_date", value)
        editor.apply()
    }

    fun getLastUpdateDBDate(context: Context?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString("ivi_zaim_last_update_date", "")
    }

    fun setDBDate(value: DbResponse?, context: Context?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        val gson = Gson()
        val json = gson.toJson(value)
        editor.putString("ivi_zaim_db_data", json)
        editor.apply()
    }

    fun getDBDate(context: Context?): DbResponse {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()

        return  gson.fromJson(preferences.getString("ivi_zaim_db_data", ""), DbResponse::class.java)
    }
}