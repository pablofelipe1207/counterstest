package com.cornershop.counterstest.data.common

interface DomainMapper<T : Any> {
    fun mapToDomainModel(): T
}