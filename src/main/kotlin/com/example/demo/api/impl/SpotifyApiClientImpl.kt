package com.example.demo.api.impl

import com.example.demo.api.dto.GetRelatedArtistsResponse
import com.example.demo.api.dto.SearchArtistResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyApiClientImpl {

    @GET("/v1/search?type=artist")
    fun searchArtist(
        @Query("q") q: String
    ): Call<SearchArtistResponse>

    @GET("/v1/artists/{id}/related-artists")
    fun getRelatedArtists(
        @Path("id") id: String
    ): Call<GetRelatedArtistsResponse>
}
