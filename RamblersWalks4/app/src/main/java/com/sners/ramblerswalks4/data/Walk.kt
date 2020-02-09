package com.sners.ramblerswalks4.data

import java.util.*

data class Walk (
    val id : Long,
    val status : Dictionary<String, String>,
    val diffiulty: Dictionary<String, String>

)