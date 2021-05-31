package com.cornershop.counterstest.domain.model

data class Counter(val id: String, val title: String, var count: Int, var isSelected: Boolean = false){
    fun increment() = count++

    fun decrement() = count++
}