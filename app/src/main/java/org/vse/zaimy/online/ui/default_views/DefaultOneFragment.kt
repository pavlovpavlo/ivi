package org.vse.zaimy.online.ui.default_views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import org.vse.zaimy.online.R


class DefaultOneFragment : Fragment(), SeekBar.OnSeekBarChangeListener{
    private lateinit var thisView: View
    private lateinit var defaultViewActivity: DefaultViewActivity
    private lateinit var navController: NavController
    private lateinit var backBtn: ImageView
    private lateinit var seekBar: SeekBar
    private lateinit var sumText: TextView
    private lateinit var order: LinearLayout
    private lateinit var clearFilter: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DefaultViewActivity) {
            defaultViewActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisView = inflater.inflate(R.layout.fragment_default_1, container, false)

        return thisView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        clearFilter = thisView.findViewById(R.id.clear_filter)
        order = thisView.findViewById(R.id.order)
        sumText = thisView.findViewById(R.id.summ_text)
        seekBar = thisView.findViewById(R.id.seekBar)
        backBtn = thisView.findViewById(R.id.back)

        backBtn.setOnClickListener {defaultViewActivity.finish()}
        order.setOnClickListener {navController.navigate(R.id.fragment_default_2)}

        clearFilter.setOnClickListener{
            seekBar.progress = 5000
        }

        seekBar.setOnSeekBarChangeListener(this)
    }

    private fun priceToText(price: String): String? {
        return if (price.length > 3) StringBuffer(price).reverse().insert(3, " ").reverse()
            .toString() else price
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        sumText.text = priceToText((progress + 5000).toString()) + " ₽"
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }


}