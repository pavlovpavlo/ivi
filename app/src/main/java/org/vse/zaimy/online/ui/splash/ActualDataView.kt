package org.vse.zaimy.online.ui.splash

import org.vse.zaimy.online.pojo.actual_backend.ActualBackendResponse
import org.vse.zaimy.online.pojo.date.DateResponse
import org.vse.zaimy.online.pojo.db.DbResponse

interface ActualDataView {
    fun onActualBackendComplete(response: ActualBackendResponse)
    fun onActualDateComplete(response: DateResponse)
    fun onActualDBComplete(response: DbResponse)
    fun onInternetError()
}