package com.aduilio.matchessimulator.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stadium(
    var name: String,
    var image: String
) : Parcelable
