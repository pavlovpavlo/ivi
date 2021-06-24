package org.vse.zaimy.online.ui.default_views

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import org.vse.zaimy.online.R
import java.lang.NullPointerException


class DefaultThreeFragment : Fragment() {
    private lateinit var thisView: View
    private lateinit var defaultViewActivity: DefaultViewActivity
    private lateinit var navController: NavController
    private lateinit var timerText: TextView
    private var countDownTimer: CountDownTimer? = null
    private var minuteCountTimer = 2
    private var secondsCountTimer = 60

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
        thisView = inflater.inflate(R.layout.fragment_default_3, container, false)

        return thisView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)

        timerText = thisView.findViewById(R.id.timer_text)
        addTimeListener()
    }

    private fun startTimer() {
        countDownTimer!!.start()
    }

    private fun addTimeListener() {
        countDownTimer = object : CountDownTimer(
            ((minuteCountTimer * secondsCountTimer + secondsCountTimer + 2) * 1000).toLong(),
            1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                if (secondsCountTimer == 0 && minuteCountTimer == 0) {
                    countDownTimer!!.onFinish()
                } else {
                    if (secondsCountTimer - 1 == -1) {
                        minuteCountTimer--
                        secondsCountTimer = 59
                    } else secondsCountTimer--
                    timerText.text =
                        (if (minuteCountTimer < 10) "0$minuteCountTimer" else minuteCountTimer).toString() + ":" + if (secondsCountTimer < 10) "0$secondsCountTimer" else secondsCountTimer
                }
            }

            override fun onFinish() {
                navController.navigate(R.id.fragment_default_4)
            }
        }
    }


    override fun onStop() {
        try {
            super.onStop()
            countDownTimer!!.cancel()
        } catch (e: NullPointerException) {
        }
    }

    override fun onStart() {
        super.onStart()
        if (countDownTimer != null)
            startTimer()
    }

}