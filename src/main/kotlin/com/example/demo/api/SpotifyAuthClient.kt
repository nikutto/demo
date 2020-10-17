package com.example.demo.api

import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Field
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import org.springframework.beans.factory.annotation.Autowired
import retrofit2.Call

import org.springframework.stereotype.Component

@JsonIgnoreProperties(ignoreUnknown = true)
data class SpotifyAuthResponse(

    @JsonProperty(value = "access_token")
    val accessToken: String,
    @JsonProperty(value = "token_type")
    val tokenType: String,
    @JsonProperty(value = "expires_in")
    val expiresIn: Int
)

interface SpotifyAuthClientImpl {

    @FormUrlEncoded
    @POST("/api/token")
    fun getTokenImpl(
        @Field("grant_type") grantType: String
    ): Call<SpotifyAuthResponse>

}

@Component
class SpotifyAuthClient(
    @Autowired val spotifyAuthClientImpl: SpotifyAuthClientImpl,
    @Autowired val spotifyApiParameter: SpotifyApiParameter
) {

    fun getToken(): Call<SpotifyAuthResponse> {
        return spotifyAuthClientImpl.getTokenImpl(spotifyApiParameter.authGrantType)
    }

}
