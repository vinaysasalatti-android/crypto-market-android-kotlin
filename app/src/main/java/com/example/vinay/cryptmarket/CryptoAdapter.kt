package com.example.vinay.cryptmarket

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.crypto_item.view.*
import java.text.NumberFormat
import java.util.*

/**
 * Created by vinay on 12/03/18.
 */
class CryptoAdapter(private val cryptoList: List<CryptoResult>) : RecyclerView.Adapter<CryptoAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: CryptoAdapter.ViewHolder, position: Int) {
        val crypto = cryptoList[position]
        holder.settext(crypto)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CryptoAdapter.ViewHolder {
        val inflatedView = parent?.inflate(R.layout.crypto_item, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount() = cryptoList.size


    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        private var view: View = itemView!!
        private var crypto: CryptoResult? = null

        fun settext(cryptoResult: CryptoResult) {
            this.crypto = cryptoResult
            view.name.text = cryptoResult.name

            var local : Locale = Locale("en", "IN")
            var format : NumberFormat = NumberFormat.getCurrencyInstance(local)

            view.price.text = format.format(String.format("%.2f",cryptoResult.price_inr!!.toDouble()).toFloat())

            view.price_1.text = cryptoResult.percent_change_1h
            view.price_24.text = cryptoResult.percent_change_24h
            view.price_7.text = cryptoResult.percent_change_7d

            if (cryptoResult.percent_change_1h!!.startsWith("-")) {
                view.price_1.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.holo_red_light))
            } else {
                view.price_1.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.holo_green_light))
            }

            if (cryptoResult.percent_change_24h!!.startsWith("-")) {
                view.price_24.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.holo_red_light))
            } else {
                view.price_24.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.holo_green_light))
            }

            if (cryptoResult.percent_change_7d!!.startsWith("-")) {
                view.price_7.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.holo_red_light))
            } else {
                view.price_7.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.holo_green_light))
            }


            var id: Int = itemView.context.getResources().getIdentifier(cryptoResult.symbol!!.toLowerCase(), "drawable", itemView.context.getPackageName());


            view.crypt_img.setImageResource(id)
        }

    }


}