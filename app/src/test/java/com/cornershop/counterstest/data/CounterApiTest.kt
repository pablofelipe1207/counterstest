package com.cornershop.counterstest.data

import com.cornershop.counterstest.data.model.CounterEntity
import com.cornershop.counterstest.data.networking.CounterApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class CounterApiTest {

    private val mockWebServer = MockWebServer()
    private lateinit var mockResponse: MockResponse
    private lateinit var counterModel: CounterEntity

    private val client = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.SECONDS)
        .readTimeout(2, TimeUnit.SECONDS)
        .writeTimeout(2, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CounterApi::class.java)

    @Before
    fun setUp() {

        mockResponse = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody("[{\"id\": \"kn59lxol\", \"title\":\"prueba\",\"count\": 2}]")
            .setResponseCode(200)

        counterModel = CounterEntity(id = "kn59lxol", title = "prueba", count = 2 )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch counters correctly given 200 response`(){
        runBlocking {
            val expected = arrayListOf(counterModel)
            mockWebServer.enqueue(mockResponse)
            assertEquals(api.getAllCounter(), expected)
        }
    }
}