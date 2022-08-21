package com.example.nlapp.utils

sealed class ResponseHandler<T>(val data: T? = null, val errorMessage: String? = null) {

    class Success<T>(data: T? = null) : ResponseHandler<T>(data)
    class Failure<T>(errorMessage: String,data: T? = null) : ResponseHandler<T>(data, errorMessage)
    class Loading<T> : ResponseHandler<T>()

}