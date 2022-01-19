package com.aduilio.matchessimulator.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aduilio.matchessimulator.R
import com.aduilio.matchessimulator.api.MatchesApi
import com.aduilio.matchessimulator.databinding.ActivityMainBinding
import com.aduilio.matchessimulator.entity.Match
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var matchesApi: MatchesApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupHttpClient()
    }

    override fun onResume() {
        super.onResume()

        matchesApi.getMatches().enqueue(object : Callback<List<Match>> {
            override fun onResponse(call: Call<List<Match>>, response: Response<List<Match>>) {
                if (response.isSuccessful) {
                    Snackbar.make(
                        binding.root,
                        response?.body()?.get(0)?.description!!,
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    showErrorMessage()
                }
            }

            override fun onFailure(call: Call<List<Match>>, t: Throwable) {
                showErrorMessage()
            }
        })
    }

    private fun setupHttpClient() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://aduilio.github.io/matches-simulator-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        matchesApi = retrofit.create(MatchesApi::class.java)
    }

    private fun showErrorMessage() {
        Snackbar.make(binding.root, R.string.fail_get_matches, Snackbar.LENGTH_SHORT)
            .show()
    }
}