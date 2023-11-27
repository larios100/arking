package com.example.arking.feature_otis.util

class TotalOtiHelper(private val amount: Double) {
    fun getTotalAmount(): Double{
        return amount + (amount * 0.15)
    }
}