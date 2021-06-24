package org.vse.zaimy.online.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.ahmadrosid.svgloader.SvgLoader
import com.facebook.appevents.ml.Utils
import com.squareup.picasso.Picasso
import org.vse.zaimy.online.Application
import org.vse.zaimy.online.R
import org.vse.zaimy.online.ui.default_views.DefaultViewActivity


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var lastActiveTabsView: View
    private lateinit var tabExtraFields: View
    private lateinit var tabExtraFieldsSecond: View
    private lateinit var tabExtraFieldsContainer: LinearLayout
    private lateinit var tabExtraFieldsSecondContainer: LinearLayout
    private lateinit var tabLoansContainer: LinearLayout
    private lateinit var tabCardsContainer: LinearLayout
    private lateinit var tabCreditsContainer: LinearLayout
    private lateinit var mainTabContainer: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    override fun onBackPressed() {
        try {
            (lastActiveTabsView as ViewGroup).getChildAt(2).visibility = View.INVISIBLE

            lastActiveTabsView = if (navController.previousBackStackEntry?.destination!!.id == R.id.loansFragment) {
                findViewById(R.id.tab_loans)
            }
            else if (navController.previousBackStackEntry?.destination!!.id == R.id.cardsFragment) {
                findViewById(R.id.tab_cards)
            }
            else if (navController.previousBackStackEntry?.destination!!.id == R.id.creditsFragment) {
                findViewById(R.id.tab_credits)
            }
            else if (navController.previousBackStackEntry?.destination!!.id == R.id.extraFieldsFragment) {
                findViewById(R.id.tab_extra_fields)
            }
            else if (navController.previousBackStackEntry?.destination!!.id == R.id.extraFieldsSecondFragment) {
                findViewById(R.id.tab_extra_fields_second)
            }
            else findViewById(R.id.tab_loans)

            (lastActiveTabsView as ViewGroup).getChildAt(2).visibility = View.VISIBLE
        } catch (e: Exception) {
        }

        super.onBackPressed()
    }


    @SuppressLint("CutPasteId")
    private fun initViews() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        mainTabContainer = findViewById(R.id.main_tab_container)
        tabExtraFieldsContainer = (findViewById<View>(R.id.tab_extra_fields)).parent as LinearLayout
        tabExtraFieldsSecondContainer =
            (findViewById<View>(R.id.tab_extra_fields_second)).parent as LinearLayout
        tabLoansContainer = (findViewById<View>(R.id.tab_loans)).parent as LinearLayout
        tabCardsContainer = (findViewById<View>(R.id.tab_cards)).parent as LinearLayout
        tabCreditsContainer = (findViewById<View>(R.id.tab_credits)).parent as LinearLayout
        tabExtraFields = (findViewById<View>(R.id.tab_extra_fields)) as LinearLayout
        tabExtraFieldsSecond = (findViewById<View>(R.id.tab_extra_fields_second)) as LinearLayout

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
                        (lastActiveTabsView as ViewGroup).getChildAt(2).visibility = View.INVISIBLE
                        lastActiveTabsView = findViewById(R.id.tab_cards)
                        (lastActiveTabsView as ViewGroup).getChildAt(2).visibility = View.VISIBLE

                        val bundle = Bundle()
                        bundle.putString("type_push", intent.getStringExtra("type_push"))
                        val navBuilder = NavOptions.Builder()
                        val navOptions: NavOptions =
                            navBuilder.setPopUpTo(R.id.cardsFragment, true).build()
                        navController.navigate(R.id.cardsFragment, bundle, navOptions)
                    }
                }
                "credits" -> {
                    if (Application.data.credits != null) {
                        (lastActiveTabsView as ViewGroup).getChildAt(2).visibility = View.INVISIBLE
                        lastActiveTabsView = findViewById(R.id.tab_credits)
                        (lastActiveTabsView as ViewGroup).getChildAt(2).visibility = View.VISIBLE
                        val navBuilder = NavOptions.Builder()
                        val navOptions: NavOptions =
                            navBuilder.setPopUpTo(R.id.creditsFragment, true).build()
                        navController.navigate(R.id.creditsFragment, null, navOptions)
                    }
                }
                "loans" -> {
                    if (Application.data.loans != null) {
                        (lastActiveTabsView as ViewGroup).getChildAt(2).visibility = View.INVISIBLE
                        lastActiveTabsView = findViewById(R.id.tab_loans)
                        (lastActiveTabsView as ViewGroup).getChildAt(2).visibility = View.VISIBLE
                        val navBuilder = NavOptions.Builder()
                        val navOptions: NavOptions =
                            navBuilder.setPopUpTo(R.id.loansFragment, true).build()
                        navController.navigate(R.id.loansFragment, null, navOptions)
                    }
                }

            }
        }

    }

    private fun initTabs() {
        var countTabs = 5

        lastActiveTabsView = findViewById(R.id.tab_extra_fields)
        if (Application.data.appConfig.extraField0 == null || Application.data.appConfig.extraField0.equals(
                "null"
            )
        ) {
            lastActiveTabsView = findViewById(R.id.tab_extra_fields_second)
            (lastActiveTabsView as ViewGroup).getChildAt(2).visibility = View.VISIBLE
            tabExtraFieldsContainer.visibility = View.GONE
            tabLoansContainer.gravity = Gravity.CENTER
            countTabs--

            val navBuilder = NavOptions.Builder()
            val navOptions: NavOptions =
                navBuilder.setPopUpTo(R.id.extraFieldsSecondFragment, true).build()
            navController.navigate(R.id.extraFieldsSecondFragment, null, navOptions)
        } else {
            tabLoansContainer.gravity = Gravity.CENTER
            if (Application.data.appConfig.extraField3.contains(".png", true))
                Picasso.get().load(Application.data.appConfig.extraField3)
                    .into(((tabExtraFields as ViewGroup).getChildAt(0) as ImageView))
            else
                SvgLoader.pluck()
                    .with(this)
                    .load(
                        Application.data.appConfig.extraField3,
                        ((tabExtraFields as ViewGroup).getChildAt(2) as ImageView)
                    )

            ((tabExtraFields as ViewGroup).getChildAt(1) as TextView).text =
                Application.data.appConfig.extraField1
        }

        if (Application.data.appConfig.extraField4 == null || Application.data.appConfig.extraField4.equals(
                "null"
            )
        ) {
            lastActiveTabsView = findViewById(R.id.tab_loans)
            (lastActiveTabsView as ViewGroup).getChildAt(2).visibility = View.VISIBLE
            tabExtraFieldsSecondContainer.visibility = View.GONE
            tabLoansContainer.gravity = Gravity.START
            countTabs--

            val navBuilder = NavOptions.Builder()
            val navOptions: NavOptions = navBuilder.setPopUpTo(R.id.loansFragment, true).build()
            navController.navigate(R.id.loansFragment, null, navOptions)
        } else {
            tabLoansContainer.gravity = Gravity.CENTER
            if (Application.data.appConfig.extraField7.contains(".png", true))
                Picasso.get().load(Application.data.appConfig.extraField7)
                    .into(((tabExtraFieldsSecond as ViewGroup).getChildAt(0) as ImageView))
            else
                SvgLoader.pluck()
                    .with(this)
                    .load(
                        Application.data.appConfig.extraField7,
                        ((tabExtraFieldsSecond as ViewGroup).getChildAt(2) as ImageView)
                    )

            ((tabExtraFieldsSecond as ViewGroup).getChildAt(1) as TextView).text =
                Application.data.appConfig.extraField5
        }

        if (Application.data.loans == null) {
            lastActiveTabsView = findViewById(R.id.tab_cards)
            (lastActiveTabsView as ViewGroup).getChildAt(2).visibility = View.VISIBLE
            tabLoansContainer.visibility = View.GONE
            tabCreditsContainer.gravity = Gravity.CENTER
            countTabs--

            val navBuilder = NavOptions.Builder()
            val navOptions: NavOptions = navBuilder.setPopUpTo(R.id.cardsFragment, true).build()
            navController.navigate(R.id.cardsFragment, null, navOptions)
        }
        if (Application.data.cards == null) {
            tabCardsContainer.visibility = View.GONE
            tabLoansContainer.gravity = Gravity.CENTER
            tabCreditsContainer.gravity = Gravity.CENTER
            countTabs--

            if (Application.data.loans == null) {
                lastActiveTabsView = findViewById(R.id.tab_credits)
                (lastActiveTabsView as ViewGroup).getChildAt(2).visibility = View.VISIBLE
                val navBuilder = NavOptions.Builder()
                val navOptions: NavOptions =
                    navBuilder.setPopUpTo(R.id.creditsFragment, true).build()
                navController.navigate(R.id.creditsFragment, null, navOptions)
            }
        }
        if (Application.data.credits == null) {
            tabCreditsContainer.visibility = View.GONE
            tabLoansContainer.gravity = Gravity.CENTER
            countTabs--
        }

        val scale = resources.displayMetrics.density
        if(countTabs == 5){
            mainTabContainer.setPadding((10*scale + 0.5f).toInt(),(2*scale + 0.5f).toInt(),(10*scale + 0.5f).toInt(),(9*scale + 0.5f).toInt())
        }else{
            if(countTabs == 4){
                mainTabContainer.setPadding((25*scale + 0.5f).toInt(),(2*scale + 0.5f).toInt(),(20*scale + 0.5f).toInt(),(9*scale + 0.5f).toInt())
            }else{
                mainTabContainer.setPadding((40*scale + 0.5f).toInt(),(2*scale + 0.5f).toInt(),(28*scale + 0.5f).toInt(),(9*scale + 0.5f).toInt())
            }
        }

        if (Application.data.loans == null && Application.data.cards == null && Application.data.credits == null
            && Application.data.appConfig.extraField0 == null  && Application.data.appConfig.extraField4 == null) {
            val intent = Intent(this@MainActivity, DefaultViewActivity::class.java)
            val cn = intent.component
            val mainIntent: Intent = Intent.makeRestartActivityTask(cn)
            startActivity(mainIntent)
        }
    }

    fun onTabClick(view: View) {
        (lastActiveTabsView as ViewGroup).getChildAt(2).visibility = View.INVISIBLE
        lastActiveTabsView = view
        (lastActiveTabsView as ViewGroup).getChildAt(2).visibility = View.VISIBLE

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
            R.id.tab_extra_fields -> {
                navController.navigate(R.id.extraFieldsFragment, null)
            }
            R.id.tab_extra_fields_second -> {
                navController.navigate(R.id.extraFieldsSecondFragment, null)
            }
        }

    }

}