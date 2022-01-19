package com.aduilio.matchessimulator.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aduilio.matchessimulator.R
import com.aduilio.matchessimulator.adapter.MatchesAdapter
import com.aduilio.matchessimulator.api.MatchesApi
import com.aduilio.matchessimulator.databinding.ActivityMainBinding
import com.aduilio.matchessimulator.entity.Match
import com.aduilio.matchessimulator.entity.Team
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var matchesApi: MatchesApi
    private lateinit var matchesAdapter: MatchesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupComponents()
        setupHttpClient()
        getMatches()
    }

    private fun setupComponents() {
        matchesAdapter = MatchesAdapter()
        binding.rvMatches.setHasFixedSize(true)
        binding.rvMatches.adapter = matchesAdapter

        binding.srlMatches.setOnRefreshListener { getMatches() }

        binding.fabSimulate.setOnClickListener { view ->
            view.animate().rotationBy(360F).duration = 500
            simulate()
        }
    }

    private fun setupHttpClient() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://aduilio.github.io/matches-simulator-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        matchesApi = retrofit.create(MatchesApi::class.java)
    }

    private fun getMatches() {
        binding.srlMatches.isRefreshing = true
        matchesApi.getMatches().enqueue(object : Callback<List<Match>> {
            override fun onResponse(call: Call<List<Match>>, response: Response<List<Match>>) {
                binding.srlMatches.isRefreshing = false

                if (response.isSuccessful) {
                    response.body()?.let {
                        matchesAdapter.setMatches(it)
                    }
                } else {
                    showErrorMessage()
                }
            }

            override fun onFailure(call: Call<List<Match>>, t: Throwable) {
                binding.srlMatches.isRefreshing = false
                showErrorMessage()
            }
        })
    }

    private fun showErrorMessage() {
        Snackbar.make(binding.root, R.string.fail_get_matches, Snackbar.LENGTH_SHORT)
            .show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun simulate() {
        matchesAdapter.getMatches().forEach(this::simulateScore)
        matchesAdapter.notifyDataSetChanged()
    }

    private fun simulateScore(match: Match) {
        simulateScore(match.homeTeam)
        simulateScore(match.awayTeam)
    }

    private fun simulateScore(team: Team) {
        team.score = Random.nextInt(0, team.strength + 1)
    }
}