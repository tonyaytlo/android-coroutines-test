package com.aytlo.tony.kotlin_coroutines.presentation.core.extension

import android.os.Handler
import android.os.Looper


fun <T> unsafeLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)

fun postUi(action: () -> Unit) = Handler(Looper.getMainLooper()).post { action() }