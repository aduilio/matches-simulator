package com.aduilio.matchessimulator.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aduilio.matchessimulator.api.MatchesApi
import com.aduilio.matchessimulator.entity.Match
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MatchesViewModel : ViewModel() {

    object Constants {
        const val SERVER_URL = "https://aduilio.github.io/matches-simulator-api/"
    }

    private lateinit var matchesApi: MatchesApi

    init {
        setupHttpClient()
    }

    val matches: MutableLiveData<List<Match>> by lazy {
        MutableLiveData<List<Match>>()
    }

    val showProgress: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val resultSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun get() {
        showProgress.value = true

        matchesApi.getMatches().enqueue(object : Callback<List<Match>> {
            override fun onResponse(call: Call<List<Match>>, response: Response<List<Match>>) {
                showProgress.value = false
                setResultSuccess(response.isSuccessful)

                handleResponse(response)
            }

            override fun onFailure(call: Call<List<Match>>, t: Throwable) {
                showProgress.value = false
                setResultSuccess(false)
            }
        })
    }

    private fun setupHttpClient() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        matchesApi = retrofit.create(MatchesApi::class.java)
    }

    private fun handleResponse(response: Response<List<Match>>) {
        if (response.isSuccessful) {
            response.body()?.let {
                matches.value = it
            }
        }
    }

    private fun setResultSuccess(success: Boolean) {
        resultSuccess.value = success
    }
}