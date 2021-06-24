package org.vse.zaimy.online.ui.no_internet

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import org.vse.zaimy.online.R
import org.vse.zaimy.online.util.Utils
import java.io.UnsupportedEncodingException


class NoInternetConnectionDialog : DialogFragment() {
    private var isReloadClose = false

    interface OnRefreshResponse {
        @Throws(UnsupportedEncodingException::class)
        fun refreshResponse()
    }

    override fun onDestroy() {
        if (!isReloadClose) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
            requireActivity().finish()
        }
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            dialog.window
                ?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.dialog_no_internet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isReloadClose = false
        view.findViewById<View>(R.id.reload).setOnClickListener {
            if (Utils.isOnline(requireContext())) {
                try {
                    isReloadClose = true
                    onRefreshResponse!!.refreshResponse()
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }
                dismiss()
            }
        }
    }

    companion object {
        const val TAG = "NoInternerConnectionDialog"
        var dialog: NoInternetConnectionDialog? = null
        private var onRefreshResponse: OnRefreshResponse? = null

        fun showShiftsDialog(time: String?) {
            val DIALOG_ALERT = "dialog_alert"

        }


        fun display(
            fragmentManager: FragmentManager?,
            refreshResponse: OnRefreshResponse?
        ): NoInternetConnectionDialog? {
            onRefreshResponse = refreshResponse
            dialog = NoInternetConnectionDialog()
            dialog!!.show(fragmentManager!!, TAG)
            return dialog
        }

        fun dismissCurrent() {
            dialog!!.dismiss()
        }
    }
}