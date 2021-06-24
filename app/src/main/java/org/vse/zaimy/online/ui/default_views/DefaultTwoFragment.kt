package org.vse.zaimy.online.ui.default_views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import org.vse.zaimy.online.R


class DefaultTwoFragment : Fragment() {
    private lateinit var thisView: View
    private lateinit var defaultViewActivity: DefaultViewActivity
    private lateinit var navController: NavController
    private lateinit var back: ImageView
    private lateinit var fio: EditText
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var checkboxBlock: LinearLayout
    private lateinit var checkbox: AppCompatCheckBox
    private lateinit var order: LinearLayout
    private lateinit var backBottom: CardView

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
        thisView = inflater.inflate(R.layout.fragment_default_2, container, false)

        return thisView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        back = thisView.findViewById(R.id.back)
        backBottom = thisView.findViewById(R.id.back_bottom)
        checkboxBlock = thisView.findViewById(R.id.checkbox_block)
        checkbox = thisView.findViewById(R.id.checkbox)
        pass = thisView.findViewById(R.id.pass)
        order = thisView.findViewById(R.id.order)
        email = thisView.findViewById(R.id.email)
        phone = thisView.findViewById(R.id.phone)
        fio = thisView.findViewById(R.id.fio)

        back.setOnClickListener { defaultViewActivity.onBackPressed() }
        backBottom.setOnClickListener { defaultViewActivity.onBackPressed() }
        order.setOnClickListener {
            if (isValidData())
                navController.navigate(R.id.fragment_default_3)
        }

        checkboxBlock.setOnClickListener { checkbox.isChecked = !checkbox.isChecked }
        checkbox.setOnClickListener { checkbox.isChecked = !checkbox.isChecked }

    }

    private fun isValidData(): Boolean {
        return (pass.text.isNotEmpty() &&
                email.text.isNotEmpty() &&
                phone.text.isNotEmpty() &&
                fio.text.isNotEmpty() &&
                checkbox.isChecked)
    }


}