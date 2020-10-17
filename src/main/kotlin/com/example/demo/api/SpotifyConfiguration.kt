package com.example.demo.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@ConstructorBinding
@ConfigurationProperties("spotify")
data class SpotifyApiParameter(
    val authUrl: String,
    val authType: String,
    val authToken: String,
    val authGrantType: String
)

@Configuration
class SpotifyApiConfiguration(
    @Autowired val spotifyApiParameter: SpotifyApiParameter
) {

    @Bean
    fun spotifyAuthOkHttphClient() = OkHttpClient.Builder()
        .addInterceptor(AuthorizationInterceptor(spotifyApiParameter.authType, spotifyApiParameter.authToken))
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    @Bean
    fun spotifyAuthClientImpl(spotifyAuthOkHttphClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(spotifyApiParameter.authUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(spotifyAuthOkHttphClient)
            .build()
            .create(SpotifyAuthClientImpl::class.java)
}
