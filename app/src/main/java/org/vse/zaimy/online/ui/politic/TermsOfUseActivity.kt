package org.vse.zaimy.online.ui.politic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import org.vse.zaimy.online.Application
import org.vse.zaimy.online.R

class TermsOfUseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_of_use)

        initViews()
    }

    private fun initViews() {
        val text: TextView = findViewById(R.id.text)
        val back: ImageView = findViewById(R.id.back)

        back.setOnClickListener { onBackPressed() }
        text.text = Html.fromHtml(Application.data.appConfig.userTermHtml)
    }
}