package org.vse.zaimy.online.ui.extra_fields

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


class ExtraFieldsFragment : Fragment(){
    private lateinit var thisView: View
    private lateinit var mainActivity: MainActivity




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


}