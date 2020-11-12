package com.example.demo.api

import com.example.demo.api.dto.GetRelatedArtistsResponse
import com.example.demo.api.dto.SearchArtistResponse
import com.example.demo.api.impl.SpotifyApiClientImpl
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Component
class SpotifyApiClient(
    @Autowired val spotifyAuthClient: SpotifyAuthClient,
    @Autowired val spotifyApiParameter: SpotifyApiParameter
) {
    fun getSpotifyApiClientImpl(): SpotifyApiClientImpl {
        val resp = spotifyAuthClient.getToken().execute()

        val authInfo = resp.body()!!
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor(authInfo.tokenType, authInfo.accessToken))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(spotifyApiParameter.apiUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(SpotifyApiClientImpl::class.java)
    }

    private var spotifyApiClientImpl = getSpotifyApiClientImpl()

    fun buildConnection() {
        spotifyApiClientImpl = getSpotifyApiClientImpl()
    }

    fun searchArtist(
        q: String
    ): Call<SearchArtistResponse> = spotifyApiClientImpl.searchArtist(q)

    fun getRelatedArtists(
        id: String
    ): Call<GetRelatedArtistsResponse> = spotifyApiClientImpl.getRelatedArtists(id)
}
