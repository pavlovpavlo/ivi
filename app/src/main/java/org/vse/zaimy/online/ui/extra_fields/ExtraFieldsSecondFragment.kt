package org.vse.zaimy.online.ui.extra_fields

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.my.tracker.MyTracker
import com.yandex.metrica.YandexMetrica
import org.vse.zaimy.online.Application
import org.vse.zaimy.online.Application.Companion.cardsCredit
import org.vse.zaimy.online.Application.Companion.cardsDebit
import org.vse.zaimy.online.Application.Companion.cardsInstallment
import org.vse.zaimy.online.R
import org.vse.zaimy.online.pojo.db.Card
import org.vse.zaimy.online.pojo.db.Proposition
import org.vse.zaimy.online.ui.MainActivity
import org.vse.zaimy.online.ui.default_views.DefaultViewActivity
import org.vse.zaimy.online.ui.no_internet.NoInternetConnectionDialog
import org.vse.zaimy.online.ui.order.OrderActivity
import org.vse.zaimy.online.ui.politic.TermsOfUseActivity
import org.vse.zaimy.online.ui.proposition.DetailActivity
import org.vse.zaimy.online.ui.proposition.OnPropositionClickListener
import org.vse.zaimy.online.util.FirebaseAnalyticsUtil
import org.vse.zaimy.online.util.Utils
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ExtraFieldsSecondFragment : Fragment(){
    private lateinit var thisView: View
    private lateinit var mainActivity: MainActivity

    lateinit var web: WebView
    var isInit: Boolean = false
    var isError: Boolean = false


    private var imagePathCallback: ValueCallback<Array<Uri?>>? = null
    private var cameraImagePath: String? = null

    private val RECORD_REQUEST_CODE = 101


    private val CAMERA_REQUEST_CODE = 113
    private val REQUEST_SELECT_FILE = 13
    private val INTENT_FILE_TYPE = "*/*"
    private val CAMERA_PHOTO_PATH_POSTFIX = "file:"
    private val PHOTO_NAME_POSTFIX = "JPEG_"
    private val PHOTO_FORMAT = ".jpg"
    private lateinit var test: Intent
    var context: MainActivity? = null



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mainActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisView = inflater.inflate(R.layout.fragment_extra_field, container, false)

        web = thisView.findViewById(R.id.webview)


        val back: ImageView =thisView.findViewById(R.id.back)
        val title: TextView =thisView.findViewById(R.id.title)
        title.text = Application.data.appConfig.extraField6

        back.setOnClickListener {
            if (web.canGoBack()) {
                web.goBack()
            }
        }


        initWebView(false)



        return thisView
    }



    internal inner class MyWebChromeClient : WebChromeClient() {

        override fun onPermissionRequest(request: PermissionRequest?) {
            //  super.onPermissionRequest(request)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context!!.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    web.reload()
                    requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
                } else {
                    request?.grant(request.resources)
                }
            } else {
                request?.grant(request.resources)
            }

        }


        override fun onGeolocationPermissionsShowPrompt(
            origin: String,
            callback: GeolocationPermissions.Callback
        ) {
            // Always grant permission since the app itself requires location
            // permission and the user has therefore already granted it
            callback.invoke(origin, true, false)
        }


        @RequiresApi(Build.VERSION_CODES.M)
        override fun onShowFileChooser(
            view: WebView?,
            filePath: ValueCallback<Array<Uri?>>?,
            fileChooserParams: FileChooserParams?
        ): Boolean {
            //setupPermissions()

            imagePathCallback?.onReceiveValue(null)
            imagePathCallback = null
            imagePathCallback = filePath


            val takePictureIntent = createImageCaptureIntent()

            val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
            contentSelectionIntent.type = INTENT_FILE_TYPE
            contentSelectionIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            val intentArray: Array<Intent?>


            intentArray = arrayOf(takePictureIntent)

            val chooserIntent = Intent(Intent.ACTION_CHOOSER)
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Выберите")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
            chooserIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            try {
                startActivityForResult(chooserIntent, REQUEST_SELECT_FILE)
            } catch (e: ActivityNotFoundException) {
                imagePathCallback = null
                cameraImagePath = null

                Toast.makeText(
                    getContext(),
                    "Выберите",
                    Toast.LENGTH_LONG
                ).show()

                return false
            }


            return true


//        override fun onShowFileChooser(
//                webView: WebView,
//                filePathCallback: ValueCallback<Array<Uri>>,
//                fileChooserParams: FileChooserParams
//        ): Boolean {
//            //   setupPermissions()
//            mFilePathCallback = filePathCallback
//            val intent = Intent(Intent.ACTION_GET_CONTENT)
//            intent.type = "*/*"
//            startActivityForResult(
//                    Intent.createChooser(intent, "Image Browser"),
//                    FILECHOOSER_RESULTCODE
//            )
//            return true
//        }
        }

    }

    private fun createImageCaptureIntent(): Intent? {
        var captureImageIntent: Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (captureImageIntent?.resolveActivity(this.requireContext().packageManager) != null) {
            var imageFile: File? = null

            try {
                imageFile = createImageFile()
                captureImageIntent.putExtra("CameraImagePath", cameraImagePath)
            } catch (ex: IOException) {
                ex.printStackTrace()
            }

            if (imageFile != null) {
                cameraImagePath = CAMERA_PHOTO_PATH_POSTFIX + imageFile.absolutePath
                captureImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile))
            } else {
                captureImageIntent = null
            }
        }

        return captureImageIntent
    }

    private fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat.getDateInstance().format(Date())
        val imageFileName = PHOTO_NAME_POSTFIX + timeStamp + "_"
        val storageDir =this.requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(imageFileName, PHOTO_FORMAT, storageDir)
    }
/////
/////

    private fun initWebView(boolean: Boolean) {
        isInit = true
        //web.visibility = View.INVISIBLE

        web =thisView.findViewById(R.id.webview)
        CookieManager.setAcceptFileSchemeCookies(true)

        val webSettings: WebSettings = web.settings

        webSettings.loadWithOverviewMode = true

        webSettings.javaScriptEnabled = true
        //   webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings.allowContentAccess = true
        webSettings.domStorageEnabled = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false

        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            webSettings.setGeolocationEnabled(true)
        }
        webSettings.databaseEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.setAppCacheEnabled(true)
        webSettings.allowFileAccess = true
        webSettings.setSupportZoom(false)
        webSettings.allowContentAccess = true
        webSettings.domStorageEnabled = true
        webSettings.mediaPlaybackRequiresUserGesture = true
        //    web.getSettings().setLoadsImagesAutomatically(true);

        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            webSettings.apply {
                setGeolocationEnabled(true)
            }
        }
        web.settings.useWideViewPort = true
        web.isScrollbarFadingEnabled = true

        web.isHorizontalScrollBarEnabled = false
        web.isScrollbarFadingEnabled = true
        web.scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
        web.overScrollMode = View.OVER_SCROLL_NEVER


        web.settings.javaScriptCanOpenWindowsAutomatically = true
        web.settings.setAppCacheEnabled(false)
        web.settings.databaseEnabled = true
        web.settings.domStorageEnabled = true
        web.settings.allowFileAccess = true
        web.settings.allowContentAccess = true
        web.setDownloadListener(DownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (this.requireContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        CAMERA_REQUEST_CODE
                    )
                } else {

                    val request: DownloadManager.Request = DownloadManager.Request(
                        Uri.parse(url)
                    )

                    request.allowScanningByMediaScanner()
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) //Notify client once download is completed!
                    request.setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        Uri.parse(url).getQueryParameter("file")
                    )
                    val dm: DownloadManager =
                        this.requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    dm.enqueue(request)
                    Toast.makeText(
                        getContext(),
                        "Скачивание файла",  //To notify the Client that the file is being downloaded
                        Toast.LENGTH_LONG
                    ).show()

                }
            } else {

                val request: DownloadManager.Request = DownloadManager.Request(
                    Uri.parse(url)
                )

                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) //Notify client once download is completed!
                request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    Uri.parse(url).getQueryParameter("file")
                )
                val dm: DownloadManager =
                    this.requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                dm.enqueue(request)
                Toast.makeText(
                    getContext(),
                    "Файл скачан",  //To notify the Client that the file is being downloaded
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) { // Hide the zoom controls for HONEYCOMB+
            webSettings.displayZoomControls = false
        }

        web.webViewClient = object : WebViewClient() {
            // lateinit var swipeRefreshLayout: SwipeRefreshLayout
            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {

                when (errorCode) {
                    ERROR_BAD_URL, ERROR_CONNECT, ERROR_TIMEOUT, ERROR_HOST_LOOKUP -> {
//                        NoInternetConnectionDialog.display(
//                            supportFragmentManager,
//                            this@OrderActivity
//                        )
                       // web.visibility = View.INVISIBLE
                       // isError = true
                    }
                }

            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: String
            ): Boolean {
                //   swipeRefreshLayout.isEnabled=false;
                // startAnim();


                Log.e("tag", "onLoadResource66666: $url")


                if (url.contains("https://") || (url.contains("http://"))) {
                    //https://gorod.swipegame.in/auth


                    if (url.contains("vk.com")) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        ContextCompat.startActivity(view.context, intent, null)

                        return true

                    }

                    if (url.contains("intent://maps.yandex.ru")) {
                        return try {
                            var a = "yandexmaps" + url.subSequence(6, url.length)
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(a))
                            ContextCompat.startActivity(view.context, intent, null)
                            true
                        } catch (E: Exception) {
                            true
                        }


                    }

                    return false
                } else {
                    if (url == "surgut://device_settings") {
                        try {
                            val i = Intent()
                            i.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            i.addCategory(Intent.CATEGORY_DEFAULT)
                            i.data = Uri.parse("package:" + context!!.packageName)
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                            i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                            context!!.startActivity(i)
                        } catch (e: Exception) {
                            try {
                                val intent = Intent()
                                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                val uri = Uri.fromParts("package",context!!.packageName, null)
                                intent.data = uri
                                context!!.startActivity(intent)
                            } catch (e: java.lang.Exception) {


                            }
                        }

                        try {
                            val settingsIntent: Intent =
                                Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    .putExtra(Settings.EXTRA_APP_PACKAGE,context!!.packageName)
                                    .putExtra(Settings.EXTRA_CHANNEL_ID, "delivery_channel_01")


                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .putExtra(Settings.EXTRA_APP_PACKAGE, context!!.packageName)



                            startActivity(settingsIntent)
                        } catch (e: Exception) {
                            try {
                                val settingsIntent: Intent =
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .putExtra(Settings.EXTRA_APP_PACKAGE, context!!.packageName)
                                startActivity(settingsIntent)
                            } catch (E: java.lang.Exception) {


                            }

                        }
                    } else {

                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        ContextCompat.startActivity(view.context, intent, null)

                    }

                }
                return true
            }

            override fun onLoadResource(view: WebView, url: String) {
                super.onLoadResource(view, url)
                Log.e("tag", "onLoadResource87878: $url")


            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                web.visibility = View.VISIBLE

                if (!isError)
                    web.visibility = View.VISIBLE

            }

        }

        web.webChromeClient = WebChromeClient()

        web.webChromeClient = MyWebChromeClient()


        // web.clearCache(true);

      //  val url = createWebUrl()
        web.loadUrl(createWebUrl())

        if (!boolean) {

            val url = ""//createWebUrl()
         //   web.loadUrl(url)


        } else {

            web.reload()
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(web, true)
        } else {
            CookieManager.getInstance().setAcceptCookie(true)
        }


    }

    private fun createWebUrl(): String {
        var aff_sub1: String = Application.aff_sub1
        var aff_sub2: String = Application.aff_sub2
        var aff_sub3: String = if (Application.aff_sub3 == "")
            "not_available"
        else
            Application.aff_sub3
        var aff_sub4: String = Application.aff_sub4
        var aff_sub5: String = if (Application.aff_sub5 == "")
            "not_available"
        else
            Application.aff_sub5


        return Application.data.appConfig.extraField4 + "&aff_sub1=$aff_sub1&aff_sub2=$aff_sub2&aff_sub3=$aff_sub3" +
                "&aff_sub4=$aff_sub4&aff_sub5=$aff_sub5"
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != REQUEST_SELECT_FILE || imagePathCallback == null) return

        var results: Array<Uri?>? = null

        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (data == null) {
                if (cameraImagePath != null) results = arrayOf(Uri.parse(cameraImagePath))
            } else {

                val dataString = data.dataString
                val clipData = data.clipData
                if (clipData != null) {

                    results = arrayOfNulls(clipData.itemCount)
                    for (i in 0 until clipData.itemCount) {
                        val item = clipData.getItemAt(i)
                        results[i] = item.uri
                    }
                }
                if (dataString != null) {
                    results = arrayOf(Uri.parse(dataString))
                }

                // val dataString = data.dataString
                //  if (dataString != null) results = arrayOf(Uri.parse(dataString))


            }
        }
        imagePathCallback?.onReceiveValue(results)
        imagePathCallback = null

    }


//    override fun onBackPressed() {
//
//        if (web.canGoBack()) {
//            web.goBack()
//        } else {
//            super.onBackPressed()
//        }

        //    Log.d("exit", "activity")

   // }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == RECORD_REQUEST_CODE) {
//            /*if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                initWebView(false)
//            } else {
//                initWebView(false)
//            }*/
//        }
//    }

    private fun setupPermissions() {
        val isGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        if (isGranted) {

        } else {
            makeRequest()
        }

    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this.requireContext() as Activity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
            RECORD_REQUEST_CODE
        )
    }


}