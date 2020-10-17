package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {

    @Test
    fun `Assert hello world works correctly`() {
        val entitty = restTemplate.getForEntity<String>("/")
        assertThat(entitty.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entitty.body).contains("<h1>Hello world!</h1>")
    }
}
