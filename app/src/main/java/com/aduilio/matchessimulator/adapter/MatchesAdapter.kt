package com.aduilio.matchessimulator.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aduilio.matchessimulator.activity.DetailActivity
import com.aduilio.matchessimulator.databinding.MatchItemBinding
import com.aduilio.matchessimulator.entity.Match
import com.bumptech.glide.Glide


class MatchesAdapter : RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder>() {

    private val matches: MutableList<Match> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        val binding = MatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        matches[position].apply {
            val context = holder.itemView.context

            holder.binding.tvHomeTeamName.text = homeTeam.name
            holder.binding.tvHomeTeamScore.text = homeTeam.score?.toString()
            Glide.with(context).load(homeTeam.image).into(holder.binding.ivHomeTeam)

            holder.binding.tvAwayTeamName.text = awayTeam.name
            holder.binding.tvAwayTeamScore.text = awayTeam.score?.toString()
            Glide.with(context).load(awayTeam.image).into(holder.binding.ivAwayTeam)

            holder.itemView.setOnClickListener {
                val intent = Intent(
                    context,
                    DetailActivity::class.java
                ).putExtra(DetailActivity.Constants.MATCH_PARAM, this)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return matches.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMatches(matches: List<Match>) {
        this.matches.clear()
        this.matches.addAll(matches)

        notifyDataSetChanged()
    }

    fun getMatches(): List<Match> {
        return matches
    }

    class MatchesViewHolder(var binding: MatchItemBinding) : RecyclerView.ViewHolder(binding.root)
}