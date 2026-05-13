package com.example.bhuvana.security

object AdminSecurity {

    private const val ADMIN_PIN = "1234"

    fun isValidPin(pin: String): Boolean {
        return pin == ADMIN_PIN
    }
}