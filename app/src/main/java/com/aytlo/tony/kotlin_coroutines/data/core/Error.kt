package com.aytlo.tony.kotlin_coroutines.data.core


open class Error(message: String? = null) : Throwable(message)

class NetworkConnection : Error("No internet connection")

class ServerError : Error("Server error")