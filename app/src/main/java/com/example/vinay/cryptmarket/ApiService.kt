package com.example.vinay.cryptmarket

import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


/**
 * Created by vinay on 13/03/18.
 */
public interface ApiService {

    @GET("ticker/?convert=INR&limit=50")
    abstract fun getCrypto(): Call<MutableList<CryptoResult>>



    /**
     * Companion object to create the GithubApiService
     */
    companion object Factory {
        fun create():ApiService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.coinmarketcap.com/v1/")
                    .build()

            return retrofit.create(ApiService::class.java);
        }
    }
}
