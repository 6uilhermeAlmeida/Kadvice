package com.gaa.data.api.advice.dto

import kotlinx.serialization.Serializable

@Serializable
data class AdviceResponse(val slip: Slip)

@Serializable
data class Slip(val id: String, val advice: String)