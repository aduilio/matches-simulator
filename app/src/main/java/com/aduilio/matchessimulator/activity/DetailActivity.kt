package com.aduilio.matchessimulator.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.aduilio.matchessimulator.databinding.ActivityDetailBinding
import com.aduilio.matchessimulator.entity.Match
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    object Constants {
        const val MATCH_PARAM = "match-param"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadMatch()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadMatch() {
        intent?.extras?.getParcelable<Match>(Constants.MATCH_PARAM)?.let {
            setDetails(it)
        }
    }

    private fun setDetails(match: Match) {
        match.apply {
            supportActionBar?.title = stadium.name
            setImage(stadium.image, binding.ivStadium)

            binding.tvDescription.text = description

            binding.tvHomeTeamName.text = homeTeam.name
            binding.tvHomeTeamScore.text = homeTeam.score?.toString()
            binding.rbHomeTeamStars.rating = homeTeam.strength.toFloat()
            setImage(homeTeam.image, binding.ivHomeTeam)

            binding.tvAwayTeamName.text = awayTeam.name
            binding.tvAwayTeamScore.text = awayTeam.score?.toString()
            binding.rbAwayTeamStars.rating = awayTeam.strength.toFloat()
            setImage(awayTeam.image, binding.ivAwayTeam)
        }
    }

    private fun setImage(url: String, imageView: ImageView) {
        Glide.with(this@DetailActivity).load(url).into(imageView)
    }
}