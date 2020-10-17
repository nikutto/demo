package com.example.demo.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.net.HttpURLConnection

@SpringBootTest
class SpotifyAuthClientTest(@Autowired val spotifyAuthClient: SpotifyAuthClient) {

    @Test
    fun `Response for authorization is valid`() {
        val response = spotifyAuthClient.getToken().execute()
        assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_OK)
        assertThat(response?.body()?.accessToken).isNotNull()
    }
}
