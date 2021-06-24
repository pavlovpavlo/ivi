package org.vse.zaimy.online.ui.proposition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import org.vse.zaimy.online.R
import org.vse.zaimy.online.pojo.db.Proposition
import org.vse.zaimy.online.ui.order.OrderActivity
import java.lang.Exception

class DetailActivity : AppCompatActivity() {

    lateinit var proposition: Proposition


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (intent.getSerializableExtra("proposition") != null)
            proposition = intent.getSerializableExtra("proposition") as Proposition

        initViews()
    }

    private fun initViews() {
        try {
            val name: TextView = findViewById(R.id.proposal_name)
            val description: TextView = findViewById(R.id.description)
            val summTextTitle: TextView = findViewById(R.id.summ_title)
            val summText: TextView = findViewById(R.id.summ_text)
            val percentText: TextView = findViewById(R.id.percent_text)
            val percentTextTitle: TextView = findViewById(R.id.percent_text_title)
            val image: RoundedImageView = findViewById(R.id.proposal_image)
            val back: ImageView = findViewById(R.id.back)
            val quiwi: ImageView = findViewById(R.id.quiwi)
            val world: ImageView = findViewById(R.id.world)
            val yandexMoney: ImageView = findViewById(R.id.yandex_money)
            val nal: ImageView = findViewById(R.id.nal)
            val visa: ImageView = findViewById(R.id.visa)
            val mastercard: ImageView = findViewById(R.id.mastercard)
            val orderText: TextView = findViewById(R.id.order_text)
            val order: LinearLayout = findViewById(R.id.order)
            val score: Array<ImageView> = arrayOf(
                findViewById(R.id.star_1),
                findViewById(R.id.star_2),
                findViewById(R.id.star_3),
                findViewById(R.id.star_4),
                findViewById(R.id.star_5),
            )

            if (proposition.screen == null || proposition.screen.equals("") || proposition.screen.equals(
                    "https://condomtra.xyz/"
                )
            )
                (image.parent as View).visibility = View.GONE
            else
                Picasso.get().load(proposition.screen).into(image)

            if (proposition.name != null) {
                name.text = proposition.name

                if (proposition.name.length > 12)
                    name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)

                if (proposition.name.length >= 20)
                    name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            }

            if (proposition.description != null)
                description.text = Html.fromHtml(proposition.description)

            if ((proposition.summMid == null || proposition.summMid.equals("")) ||
                    (proposition.summMax == null || proposition.summMax.equals("")) ||
                    (proposition.summPostfix == null || proposition.summPostfix.equals(""))) {
                summText.visibility = View.GONE
                summTextTitle.visibility = View.GONE
            } else
                summText.text =
                        "${proposition.summMid} ${proposition.summMax} ${proposition.summPostfix}"


            if (proposition.hidePercentFields != null && proposition.hidePercentFields.equals("1") ||
                    (proposition.percentPrefix == null || proposition.percentPrefix.equals("")) ||
                    (proposition.percent == null || proposition.percent.equals("")) ||
                    (proposition.percentPostfix == null || proposition.percentPostfix.equals(""))) {
                percentText.visibility = View.GONE
                percentTextTitle.visibility = View.GONE
            } else
                percentText.text =
                        "${proposition.percentPrefix} ${proposition.percent} ${proposition.percentPostfix}"

            if (proposition.orderButtonText != null)
                orderText.text = proposition.orderButtonText

            if (proposition.score != null && proposition.score.equals(""))
                proposition.score = "0"

            if (proposition.score != null) {
                val count = if (proposition.score.toDouble().toInt() > 5)
                    5
                else
                    proposition.score.toDouble().toInt()
                for (i in 0 until count)
                    score[i].setImageResource(R.drawable.ic_icon_star_fill)
            }

            if (proposition.showMastercard == null || proposition.showMastercard.equals("0"))
                mastercard.visibility = View.GONE
            if (proposition.showCash == null || proposition.showCash.equals("0"))
                nal.visibility = View.GONE
            if (proposition.showMir == null || proposition.showMir.equals("0"))
                world.visibility = View.GONE
            if (proposition.showQiwi == null || proposition.showQiwi.equals("0"))
                quiwi.visibility = View.GONE
            if (proposition.showVisa == null || proposition.showVisa.equals("0"))
                visa.visibility = View.GONE
            if (proposition.showYandex == null || proposition.showYandex.equals("0"))
                yandexMoney.visibility = View.GONE

            back.setOnClickListener { onBackPressed() }
            order.setOnClickListener {
                val intent = Intent(this, OrderActivity::class.java)
                intent.putExtra("url", proposition.order)
                intent.putExtra("from", "more_details")
                intent.putExtra("offer", proposition.itemId)
                startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}