package com.cornershop.counterstest.domain.repository

import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.model.*

interface ICounterRepository {
    suspend fun getCounters(): Result<List<Counter>>
    suspend fun createCounter(counter : Counter): Result<List<Counter>>
    suspend fun increaseCounter(counter : Counter): Result<List<Counter>>
    suspend fun decreaseCounter(counter : Counter): Result<List<Counter>>
    suspend fun deleteCounter(counter : List<Counter>): Result<List<Counter>>
    suspend fun searchCounterByTitle(title : String): Result<List<Counter>>
    suspend fun selectCounter(counter : Counter): Result<List<Counter>>
    suspend fun unselectCounter(counter : Counter): Result<List<Counter>>
}