package org.vse.zaimy.online.ui.propositions

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
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
import org.vse.zaimy.online.util.Utils


class CardsFragment : Fragment(), OnPropositionClickListener,
    NoInternetConnectionDialog.OnRefreshResponse {
    private lateinit var thisView: View
    private lateinit var mainActivity: MainActivity
    private lateinit var list: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: PropositionsAdapter
    private lateinit var listData: MutableList<Proposition>
    private lateinit var lastActiveTabsView: View
    private lateinit var selectProposition: Proposition
    private lateinit var debet: LinearLayout
    private lateinit var credit: LinearLayout
    private lateinit var rasstroch: LinearLayout


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
        thisView = inflater.inflate(R.layout.fragment_cards, container, false)

        return thisView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = view.findViewById(R.id.cards_list)
        layoutManager = LinearLayoutManager(mainActivity)
        adapter = PropositionsAdapter()

        if (cardsDebit != null)
            adapter.setData(cardsDebit as MutableList<Proposition>)
        else
            adapter.clear()

        adapter.listener = this

        list.layoutManager = layoutManager
        list.adapter = adapter

        val info: ImageView = view.findViewById(R.id.info)

        info.setOnClickListener { startActivity(Intent(context, TermsOfUseActivity::class.java)) }

        initTabs()
    }

    private fun initTabs() {
        debet = thisView.findViewById(R.id.debet)
        credit = thisView.findViewById(R.id.credit)
        rasstroch = thisView.findViewById(R.id.rasstroch)

        debet.setOnClickListener { onTabClick(debet) }
        credit.setOnClickListener { onTabClick(credit) }
        rasstroch.setOnClickListener { onTabClick(rasstroch) }

        initTabsVisible()

        if (arguments?.getString("type_push") != null) {
            when (arguments?.getString("type_push") as String) {
                "cards_credit" -> {
                    if (cardsCredit != null)
                        onTabClick(credit)
                }
                "cards_debit" -> {
                    if (cardsDebit != null)
                        onTabClick(debet)
                }
                "cards_installment" -> {
                    if (cardsInstallment != null)
                        onTabClick(rasstroch)
                }
            }
        }
    }

    private fun onTabClick(view: View) {
        lastActiveTabsView.setBackgroundColor(Color.TRANSPARENT)
        lastActiveTabsView = view
        view.setBackgroundResource(R.drawable.ic_tabs_selected)

        when (view.id) {
            R.id.debet -> {
                if (cardsDebit != null)
                    adapter.setData(cardsDebit as MutableList<Proposition>)
                else
                    adapter.clear()
            }
            R.id.credit -> {
                if (cardsCredit != null)
                    adapter.setData(cardsCredit as MutableList<Proposition>)
                else
                    adapter.clear()
            }
            R.id.rasstroch -> {
                if (cardsInstallment != null)
                    adapter.setData(cardsInstallment as MutableList<Proposition>)
                else
                    adapter.clear()
            }
        }

    }

    private fun initTabsVisible() {
        lastActiveTabsView = debet
        if (cardsDebit == null) {
            lastActiveTabsView = credit
            debet.visibility = View.GONE

            onTabClick(credit)
        }
        if (cardsCredit == null) {
            credit.visibility = View.GONE

            if (cardsDebit == null) {
                lastActiveTabsView = rasstroch
                onTabClick(rasstroch)
            }
        }
        if (cardsInstallment == null) {
            rasstroch.visibility = View.GONE
        }
    }

    override fun onDetailClick(proposition: Proposition) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("proposition", proposition)
        startActivity(intent)
    }

    override fun onOrderClick(proposition: Proposition) {
        selectProposition = proposition
        openOrder()
    }

    private fun openOrder() {
        if (Utils.isOnline(mainActivity)) {
            val intent = Intent(context, OrderActivity::class.java)
            intent.putExtra("url", selectProposition.order)
            intent.putExtra("from", "offerwall")
            intent.putExtra("offer", selectProposition.itemId)
            startActivity(intent)
        } else
            NoInternetConnectionDialog.display(mainActivity.supportFragmentManager, this)
    }

    override fun refreshResponse() {
        openOrder()
    }

}