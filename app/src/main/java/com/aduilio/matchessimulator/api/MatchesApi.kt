package com.aduilio.matchessimulator.api

import com.aduilio.matchessimulator.entity.Match
import retrofit2.Call
import retrofit2.http.GET

interface MatchesApi {

    @GET("matches.json")
    fun getMatches(): Call<List<Match>>
}