package org.vse.zaimy.online.ui.propositions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.vse.zaimy.online.R
import org.vse.zaimy.online.pojo.db.Proposition
import org.vse.zaimy.online.ui.proposition.OnPropositionClickListener

class PropositionsAdapter() :
        RecyclerView.Adapter<PropositionsAdapter.ViewHolder>() {

    lateinit var listener: OnPropositionClickListener
    var list: MutableList<Proposition> = arrayListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_proposalt, viewGroup, false)

        return ViewHolder(view)
    }

    fun setData(data: MutableList<Proposition>?) {
        list.clear()
        if (data != null)
            list.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val proposition = list[position]

        if (proposition.screen !== null)
            Picasso.get().load(proposition.screen).into(viewHolder.image)

        if (proposition.name !== null)
            viewHolder.name.text = proposition.name


        if ((proposition.summMid == null || proposition.summMid.equals("")) ||
                (proposition.summMax == null || proposition.summMax.equals("")) ||
                (proposition.summPostfix == null || proposition.summPostfix.equals(""))) {
            viewHolder.summText.visibility = View.INVISIBLE
            viewHolder.summTextTitle.visibility = View.INVISIBLE
        } else
            viewHolder.summText.text =
                    "${proposition.summMid} ${proposition.summMax} ${proposition.summPostfix}"


        if (proposition.hidePercentFields != null && proposition.hidePercentFields.equals("1") ||
                (proposition.percentPrefix == null || proposition.percentPrefix.equals("")) ||
                (proposition.percent == null || proposition.percent.equals("")) ||
                (proposition.percentPostfix == null || proposition.percentPostfix.equals(""))) {
            viewHolder.percentText.visibility = View.INVISIBLE
            viewHolder.percentTextTitle.visibility = View.INVISIBLE
        } else
            viewHolder.percentText.text =
                    "${proposition.percentPrefix} ${proposition.percent} ${proposition.percentPostfix}"

        if (proposition.orderButtonText !== null)
            viewHolder.orderText.text = proposition.orderButtonText

        if (proposition.score !== null) {
            if (proposition.score.equals(""))
                proposition.score = "0"

            val count = if (proposition.score.toDouble().toInt() > 5)
                5
            else
                proposition.score.toDouble().toInt()
            for (i in 0 until count)
                viewHolder.score[i].setImageResource(R.drawable.ic_icon_star_fill)
        }

        if (proposition.showMastercard == null || proposition.showMastercard.equals("0"))
            viewHolder.mastercard.visibility = View.GONE
        if (proposition.showCash == null || proposition.showCash.equals("0"))
            viewHolder.nal.visibility = View.GONE
        if (proposition.showMir == null || proposition.showMir.equals("0"))
            viewHolder.world.visibility = View.GONE
        if (proposition.showQiwi == null || proposition.showQiwi.equals("0"))
            viewHolder.quiwi.visibility = View.GONE
        if (proposition.showVisa == null || proposition.showVisa.equals("0"))
            viewHolder.visa.visibility = View.GONE
        if (proposition.showYandex == null || proposition.showYandex.equals("0"))
            viewHolder.yandexMoney.visibility = View.GONE

        viewHolder.order.setOnClickListener { listener.onOrderClick(proposition) }
        viewHolder.detail.setOnClickListener { listener.onDetailClick(proposition) }
        viewHolder.image.setOnClickListener { listener.onDetailClick(proposition) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.proposal_name)
        var summTextTitle: TextView = view.findViewById(R.id.summ_title)
        var summText: TextView = view.findViewById(R.id.summ_text)
        var percentText: TextView = view.findViewById(R.id.percent_text)
        var percentTextTitle: TextView = view.findViewById(R.id.percent_text_title)
        var image: ImageView = view.findViewById(R.id.proposal_image)
        var quiwi: ImageView = view.findViewById(R.id.quiwi)
        var world: ImageView = view.findViewById(R.id.world)
        var yandexMoney: ImageView = view.findViewById(R.id.yandex_money)
        var nal: ImageView = view.findViewById(R.id.nal)
        var visa: ImageView = view.findViewById(R.id.visa)
        var mastercard: ImageView = view.findViewById(R.id.mastercard)
        var orderText: TextView = view.findViewById(R.id.order_text)
        var order: LinearLayout = view.findViewById(R.id.order)
        var detail: LinearLayout = view.findViewById(R.id.detail)
        var score: Array<ImageView> = arrayOf(
                view.findViewById(R.id.star_1),
                view.findViewById(R.id.star_2),
                view.findViewById(R.id.star_3),
                view.findViewById(R.id.star_4),
                view.findViewById(R.id.star_5),
        )
    }

    override fun getItemCount() = list.size

}