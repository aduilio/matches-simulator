package com.aduilio.matchessimulator.entity

data class Match(
    var description: String,
    var stadium: Stadium,
    var homeTeam: Team,
    var awayTeam: Team
)
