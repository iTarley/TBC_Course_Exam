package com.example.nlapp.utils


object Validator {

    fun isEmailValid(email:String): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()

    fun isEmailEmpty(email:String): Boolean =
        email.isEmpty()

    fun isPasswordEmpty(password:String): Boolean =
        password.isEmpty()

    fun isPasswordMatch(password: String,repeatPassword:String):Boolean =
        password == repeatPassword

    fun isNameEmpty(name:String,lastName:String):Boolean =
        name.isEmpty() && lastName.isEmpty()
}