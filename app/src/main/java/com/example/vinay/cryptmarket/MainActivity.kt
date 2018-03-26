package com.example.vinay.cryptmarket

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback


class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {


    private lateinit var adapter: CryptoAdapter
    val cryptoList = mutableListOf<CryptoResult>()
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()

        crypto_list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        crypto_list.addItemDecoration(LinearLayoutSpaceItemDecoration(16))
        adapter = CryptoAdapter(cryptoList)
        crypto_list.adapter = adapter
        prepareSwipeRefreshLayout()

        progress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.color_values), PorterDuff.Mode.MULTIPLY);

        progress.visibility = View.VISIBLE
        callApi()

        doTheAutoRefresh()

    }

    override fun onRefresh() {
        callApi()
    }

    private fun prepareSwipeRefreshLayout() {
        swipe_layout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
        swipe_layout.setOnRefreshListener(this)
    }


    fun callApi() {


        val apiService = ApiService.create()
        val call = apiService.getCrypto()
        Log.d("REQUEST", call.toString() + "")
        call.enqueue(object : Callback<MutableList<CryptoResult>> {
            override fun onResponse(call: Call<MutableList<CryptoResult>>, response: retrofit2.Response<MutableList<CryptoResult>>?) {
                if (response != null) {
                    progress.visibility = View.GONE

                    Log.d("RESPONCE", response.toString() + "")
                    runOnUiThread {
                        cryptoList.clear()
                        cryptoList.addAll(response.body())
                        adapter.notifyItemInserted(cryptoList.size)
                    }

                    swipe_layout.isRefreshing = false

                }

            }

            override fun onFailure(call: Call<MutableList<CryptoResult>>, t: Throwable) {
                progress.visibility = View.GONE
                Log.e("error", t.toString());
                swipe_layout.isRefreshing = false
            }
        })

    }

    private fun doTheAutoRefresh() {
        handler.postDelayed(Runnable {
// auto refresh
            progress.visibility = View.VISIBLE
            callApi()
            doTheAutoRefresh()
        }, 60000)
    }


}
