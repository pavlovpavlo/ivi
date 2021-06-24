package org.vse.zaimy.online.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import org.vse.zaimy.online.Application
import org.vse.zaimy.online.R
import org.vse.zaimy.online.pojo.db.Proposition
import org.vse.zaimy.online.ui.default_views.DefaultViewActivity
import org.vse.zaimy.online.ui.order.OrderActivity
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var lastActiveTabsView: View
    private lateinit var tabLoansContainer: LinearLayout
    private lateinit var tabCardsContainer: LinearLayout
    private lateinit var tabCreditsContainer: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    override fun onBackPressed() {
        try {
            lastActiveTabsView.setBackgroundColor(Color.TRANSPARENT)

            lastActiveTabsView =
                if (navController.previousBackStackEntry?.destination!!.id == R.id.loansFragment)
                    findViewById(R.id.tab_loans)
                else {
                    if (navController.previousBackStackEntry?.destination!!.id == R.id.cardsFragment)
                        findViewById(R.id.tab_cards)
                    else
                        findViewById(R.id.tab_credits)
                }

            lastActiveTabsView.setBackgroundResource(R.drawable.ic_tabs_selected)
        } catch (e: Exception) {
        }

        super.onBackPressed()
    }

    @SuppressLint("CutPasteId")
    private fun initViews() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        tabLoansContainer = (findViewById<View>(R.id.tab_loans)).parent as LinearLayout
        tabCardsContainer = (findViewById<View>(R.id.tab_cards)).parent as LinearLayout
        tabCreditsContainer = (findViewById<View>(R.id.tab_credits)).parent as LinearLayout

        initTabs()

        if (intent.getStringExtra("type_push") != null) {
            when (intent.getStringExtra("type_push")) {
                "cards_credit", "cards_debit", "cards_installment" -> {
                    if (intent.getStringExtra("type_push")
                            .equals("cards_credit") && Application.cardsCredit != null
                        || intent.getStringExtra("type_push")
                            .equals("cards_debit") && Application.cardsDebit != null
                        || intent.getStringExtra("type_push")
                            .equals("cards_installment") && Application.cardsInstallment != null
                    ) {
                        lastActiveTabsView.setBackgroundColor(Color.TRANSPARENT)
                        lastActiveTabsView = findViewById(R.id.tab_cards)
                        lastActiveTabsView.setBackgroundResource(R.drawable.ic_tabs_selected)

                        val bundle = Bundle()
                        bundle.putString("type_push", intent.getStringExtra("type_push"))
                        val navBuilder = NavOptions.Builder()
                        val navOptions: NavOptions =
                            navBuilder.setPopUpTo(R.id.cardsFragment, true).build()
                        navController.navigate(R.id.cardsFragment, bundle, navOptions)
                    }
                }
                "credits" -> {
                    if(Application.data.credits !=null) {
                        lastActiveTabsView.setBackgroundColor(Color.TRANSPARENT)
                        lastActiveTabsView = findViewById(R.id.tab_credits)
                        lastActiveTabsView.setBackgroundResource(R.drawable.ic_tabs_selected)
                        val navBuilder = NavOptions.Builder()
                        val navOptions: NavOptions =
                            navBuilder.setPopUpTo(R.id.creditsFragment, true).build()
                        navController.navigate(R.id.creditsFragment, null, navOptions)
                    }
                }
                "loans" -> {
                    if(Application.data.loans !=null) {
                        lastActiveTabsView.setBackgroundColor(Color.TRANSPARENT)
                        lastActiveTabsView = findViewById(R.id.tab_loans)
                        lastActiveTabsView.setBackgroundResource(R.drawable.ic_tabs_selected)
                        val navBuilder = NavOptions.Builder()
                        val navOptions: NavOptions =
                            navBuilder.setPopUpTo(R.id.loansFragment, true).build()
                        navController.navigate(R.id.loansFragment, null, navOptions)
                    }
                }

            }
        }

    }

    private fun initTabs(){
        lastActiveTabsView = findViewById(R.id.tab_loans)
        if(Application.data.loans == null){
            lastActiveTabsView = findViewById(R.id.tab_cards)
            tabLoansContainer.visibility = View.GONE
            tabCreditsContainer.gravity = Gravity.CENTER

            val navBuilder = NavOptions.Builder()
            val navOptions: NavOptions = navBuilder.setPopUpTo(R.id.cardsFragment, true).build()
            navController.navigate(R.id.cardsFragment, null, navOptions)
        }
        if(Application.data.cards == null){
            tabCardsContainer.visibility = View.GONE
            tabLoansContainer.gravity = Gravity.CENTER
            tabCreditsContainer.gravity = Gravity.CENTER

            if(Application.data.loans == null) {
                lastActiveTabsView = findViewById(R.id.tab_credits)
                val navBuilder = NavOptions.Builder()
                val navOptions: NavOptions =
                    navBuilder.setPopUpTo(R.id.creditsFragment, true).build()
                navController.navigate(R.id.creditsFragment, null, navOptions)
            }
        }
        if(Application.data.credits == null){
            tabCreditsContainer.visibility = View.GONE
            tabLoansContainer.gravity = Gravity.CENTER
        }

        if(Application.data.loans == null && Application.data.cards == null && Application.data.credits == null){
            val intent = Intent(this@MainActivity, DefaultViewActivity::class.java)
            val cn = intent.component
            val mainIntent: Intent = Intent.makeRestartActivityTask(cn)
            startActivity(mainIntent)
        }
    }

    fun onTabClick(view: View) {
        lastActiveTabsView.setBackgroundColor(Color.TRANSPARENT)
        lastActiveTabsView = view
        view.setBackgroundResource(R.drawable.ic_tabs_selected)

        when (view.id) {
            R.id.tab_loans -> {
                val navBuilder = NavOptions.Builder()
                val navOptions: NavOptions = navBuilder.setPopUpTo(R.id.loansFragment, true).build()
                navController.navigate(R.id.loansFragment, null, navOptions)
            }
            R.id.tab_cards -> {
                navController.navigate(R.id.cardsFragment, null)
            }
            R.id.tab_credits -> {
                navController.navigate(R.id.creditsFragment, null)
            }
        }

    }

}