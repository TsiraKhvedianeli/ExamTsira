package com.example.examtsira


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Request {
    @GET("artists")
    fun getSerial(@Query("page") page: Int): Call<ReqresObj<List<Artist>>>
}
