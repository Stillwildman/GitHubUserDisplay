package com.vincent.githubusers.model.items

/**
 * Created by Vincent on 2020/9/29.
 */
data class ItemRateLimit(
    private val limit: String?,
    private val remaining: String?,
    private val reset: String?,
    private val used: String?
) {
    fun getLimit(): Int {
        return limit?.toInt() ?: 0
    }

    fun getRemaining(): Int {
        return remaining?.toInt() ?: 0
    }

    fun getReset(): Long {
        return reset?.toLong() ?: 0
    }

    fun getUsed(): Int {
        return used?.toInt() ?: 0
    }
}
