package com.example.demo.api.impl

import com.example.demo.api.dto.SearchArtistResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SpotifyApiClientImpl {

    @GET("/v1/search?type=artist")
    fun searchArtist(
        @Query("q") q: String
    ): Call<SearchArtistResponse>
}
