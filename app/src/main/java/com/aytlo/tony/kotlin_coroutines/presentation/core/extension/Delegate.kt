package com.aytlo.tony.kotlin_coroutines.presentation.core.extension


fun <T> unsafeLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)