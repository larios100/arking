package com.example.arking.utils


sealed class Resource<T>(val data: T? = null, val message: UiText? = null, val field: String? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: UiText, data: T? = null, field: String? = null): Resource<T>(data, message, field)
}
