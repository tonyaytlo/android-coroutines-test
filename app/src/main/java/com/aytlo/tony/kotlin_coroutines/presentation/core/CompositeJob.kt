package com.aytlo.tony.kotlin_coroutines.presentation.core

import kotlinx.coroutines.Job

class CompositeJob {
    private val jobs = hashMapOf<String, Job>()

    fun add(job: Job, key: String = job.hashCode().toString()) = jobs.put(key, job)?.cancel()

    fun cancel(key: String) = jobs[key]?.cancel()

    fun cancel() = jobs.forEach { (_, job) -> job.cancel() }
}