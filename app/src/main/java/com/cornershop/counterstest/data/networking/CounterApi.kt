package com.cornershop.counterstest.data.networking

import com.cornershop.counterstest.data.model.CounterEntity
import com.cornershop.counterstest.data.networking.entities.IdRequest
import com.cornershop.counterstest.data.networking.entities.TitleRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface CounterApi {
    @GET("/api/v1/counters")
    suspend fun getAllCounter(): List<CounterEntity>

    @POST("/api/v1/counter")
    suspend fun createCounter(@Body title: TitleRequest): List<CounterEntity>

    @POST("/api/v1/counter/inc")
    suspend fun increaseCounter(@Body id: IdRequest): List<CounterEntity>

    @POST("/api/v1/counter/dec")
    suspend fun decrementCounter(@Body id: IdRequest): List<CounterEntity>

    @HTTP(method = "DELETE", path = "/api/v1/counter", hasBody = true)
    suspend fun deleteCounter(@Body id: IdRequest): List<CounterEntity>
}