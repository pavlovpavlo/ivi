package org.vse.zaimy.online.ui.default_views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.vse.zaimy.online.R


class DefaultFourFragment : Fragment(){
    private lateinit var thisView: View
    private lateinit var defaultViewActivity: DefaultViewActivity
    private lateinit var code: TextView

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
        thisView = inflater.inflate(R.layout.fragment_default_4, container, false)

        return thisView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        code = thisView.findViewById(R.id.code)

        code.text = code.text.toString().replace("XXXXX", (10000 until 99999).random().toString())
    }


}