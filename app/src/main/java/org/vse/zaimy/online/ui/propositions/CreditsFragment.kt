package org.vse.zaimy.online.ui.propositions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.vse.zaimy.online.Application
import org.vse.zaimy.online.R
import org.vse.zaimy.online.pojo.db.Proposition
import org.vse.zaimy.online.ui.MainActivity
import org.vse.zaimy.online.ui.no_internet.NoInternetConnectionDialog
import org.vse.zaimy.online.ui.order.OrderActivity
import org.vse.zaimy.online.ui.politic.TermsOfUseActivity
import org.vse.zaimy.online.ui.proposition.DetailActivity
import org.vse.zaimy.online.ui.proposition.OnPropositionClickListener
import org.vse.zaimy.online.util.Utils


class CreditsFragment : Fragment(), OnPropositionClickListener,
    NoInternetConnectionDialog.OnRefreshResponse {
    private lateinit var thisView: View
    private lateinit var mainActivity: MainActivity
    private lateinit var list: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: PropositionsAdapter
    private lateinit var listData: MutableList<Proposition>
    private lateinit var selectProposition: Proposition

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
        thisView = inflater.inflate(R.layout.fragment_credits, container, false)

        return thisView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = view.findViewById(R.id.cards_list)
        layoutManager = LinearLayoutManager(mainActivity)
        adapter = PropositionsAdapter()

        if (Application.data.credits != null)
            adapter.setData(Application.data.credits as MutableList<Proposition>)
        else
            adapter.clear()

        adapter.listener = this

        list.layoutManager = layoutManager
        list.adapter = adapter

        val info: ImageView = view.findViewById(R.id.info)

        info.setOnClickListener { startActivity(Intent(context, TermsOfUseActivity::class.java)) }
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