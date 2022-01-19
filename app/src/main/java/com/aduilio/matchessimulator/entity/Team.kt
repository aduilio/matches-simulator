package com.aduilio.matchessimulator.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
    var name: String,
    var strength: Int,
    var score: Int?,
    var image: String
) : Parcelable

