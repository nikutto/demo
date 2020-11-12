package com.example.demo.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.net.HttpURLConnection

@SpringBootTest
class SpotifyApiClientTests(@Autowired val spotifyApiClient: SpotifyApiClient) {

    @Test
    fun `Response for spotify API search is valid`() {
        val name = "Queen"
        val response = spotifyApiClient.searchArtist(name).execute()
        assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_OK)
        assertThat(response.body()?.artists?.artists?.elementAt(0)?.name).isEqualTo(name)
    }

    @Test
    fun `Response for spotify API getRelatedArtists is valid`() {
        val id = "1dfeR4HaWDbWqFHLkxsg1d"
        val response = spotifyApiClient.getRelatedArtists(id).execute()
        assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_OK)
    }
}
