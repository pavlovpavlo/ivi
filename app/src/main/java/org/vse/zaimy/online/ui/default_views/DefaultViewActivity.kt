package org.vse.zaimy.online.ui.default_views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.vse.zaimy.online.R

class DefaultViewActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default_view)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.fragment_default_3 ||
            navController.currentDestination?.id == R.id.fragment_default_4
        )
            finish()
        else
            super.onBackPressed()
    }
}