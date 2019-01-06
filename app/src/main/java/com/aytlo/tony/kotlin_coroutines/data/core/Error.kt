package com.aytlo.tony.kotlin_coroutines.data.core


open class Error(message: String? = null) : Throwable(message)

object NetworkConnection : Error()

class ServerError(message: String?) : Error(message)