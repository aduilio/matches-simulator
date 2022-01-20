package com.aduilio.matchessimulator.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aduilio.matchessimulator.R
import com.aduilio.matchessimulator.adapter.MatchesAdapter
import com.aduilio.matchessimulator.databinding.ActivityMainBinding
import com.aduilio.matchessimulator.entity.Match
import com.aduilio.matchessimulator.entity.Team
import com.aduilio.matchessimulator.viewmodel.MatchesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var matchesAdapter: MatchesAdapter

    private val matchesViewModel: MatchesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupComponents()
        setupViewModel()
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

    private fun setupViewModel() {
        matchesViewModel.matches.observe(this, {
            matchesAdapter.setMatches(it)
        })

        matchesViewModel.showProgress.observe(this, {
            binding.srlMatches.isRefreshing = it
        })

        matchesViewModel.resultSuccess.observe(this, {
            showErrorMessage(it)
        })
    }

    private fun getMatches() {
        matchesViewModel.get()
    }

    private fun showErrorMessage(success: Boolean) {
        if (!success) {
            Snackbar.make(binding.root, R.string.fail_get_matches, Snackbar.LENGTH_SHORT)
                .show()
        }
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