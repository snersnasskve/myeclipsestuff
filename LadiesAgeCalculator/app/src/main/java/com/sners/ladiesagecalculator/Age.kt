package com.sners.ladiesagecalculator

import java.util.*

data class Age(
    var birthDate : String,
    var birthMonth : String,
    var birthYear : String,
    var birthday : Date,
    var ageDesc : String = "Teenager"
)
