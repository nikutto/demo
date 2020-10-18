package com.example.demo.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

@JsonIgnoreProperties(ignoreUnknown = true)
data class Artist(

    @JsonProperty("id")
    val id: String,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("uri")
    val uri: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Artists(

    @JsonProperty("items")
    val artists: List<Artist>

)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SearchArtistResponse(

    @JsonProperty("artists")
    val artists: Artists
)

interface SpotifyApiClientImpl {

    @GET("/v1/search?type=artist")
    fun searchArtist(
        @Query("q") q: String
    ): Call<SearchArtistResponse>
}

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
}
