package com.aduilio.matchessimulator.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Match(
    var description: String,
    var stadium: Stadium,
    var homeTeam: Team,
    var awayTeam: Team
) : Parcelable
