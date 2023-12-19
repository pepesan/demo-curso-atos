package com.example.demogradlekotlin.dto


import java.util.*



data class ErrorMessage (
    val statusCode: Int = 0,
    val timestamp: Date? = null,
    val message: String? = null,
    val description: String? = null
){

}