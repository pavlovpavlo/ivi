package org.vse.zaimy.online.ui.splash

import android.content.Context
import android.os.AsyncTask
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException

class AdvertisingClientAsyncTask internal constructor(ctx: Context, loadCompleteAdvertisingClient: AdvertisingClientOnLoadComplete) :
    AsyncTask<Void, Void, String>() {

    private val context: Context = ctx
    private val listener: AdvertisingClientOnLoadComplete = loadCompleteAdvertisingClient

    override fun doInBackground(vararg params: Void?): String? {
        var idInfo: AdvertisingIdClient.Info? = null
        try {
            idInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        var advertId: String? = null
        try {
            advertId = idInfo?.id
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return advertId
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        listener!!.onAdvertisingClientLoadComplete(result!!)
    }
}