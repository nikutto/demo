package com.example.demo.relationgraph

import com.example.demo.api.SpotifyApiClient
import com.example.demo.api.dto.Artist
import com.example.demo.api.dto.GetRelatedArtistsResponse
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GraphCreatorTests {

    fun addMockkBehavior(mockedSpotifyApiClient: SpotifyApiClient, id: String, relatedArtists: List<Artist>) {
        every {
            mockedSpotifyApiClient
                .getRelatedArtists(id)
                .execute()
                .body()
        } returns GetRelatedArtistsResponse(
            relatedArtists
        )
    }

    @Test
    fun `test graph creator works correctly`() {

        val n = 5
        val correctIds = (0 until n).map { "c_" + it.toString() }.toList()
        val badIds = (0 until n).map { "b_" + it.toString() }.toList()
        val correctArtists = correctIds.map { Artist(it, "nameFor_" + it) }.toList()
        val badArtists = badIds.map { Artist(it, "nameFor_" + it) }.toList()

        val mockedSpotifyApiClient = mockk<SpotifyApiClient>()

        val relations = listOf(
            listOf(
                correctArtists[1],
                correctArtists[2],
                correctArtists[3],
            ),
            listOf(
                badArtists[0],
                correctArtists[0],
                correctArtists[2],
                correctArtists[3],
                correctArtists[4],
                badArtists[1],
            ),
            listOf(
                correctArtists[0],
                correctArtists[1],
                correctArtists[3],
            ),
            listOf(
                badArtists[2],
                correctArtists[0],
                correctArtists[1],
                correctArtists[2],
                correctArtists[4],
            ),
            listOf(
                badArtists[0],
                correctArtists[1],
                correctArtists[3],
                badArtists[1]
            )
        )
        for ((id, relation) in correctIds.zip(relations)) {
            addMockkBehavior(mockedSpotifyApiClient, id, relation)
        }

        val graphCreator = GraphCreator(mockedSpotifyApiClient)
        val graph = graphCreator.create(correctIds[0], n)
        assertThat(graph.g.size).isEqualTo(n)
        for (id in correctIds) {
            assertThat(graph.containsVertexOf(id)).isTrue()
        }

        for ((id, relation) in correctIds.zip(relations)) {
            val relationOfGraph = graph.getOutgoingEdgesList(id).map { it.to }.toSet<String>()
            val relationToBe = relation.map { it.id }.filter { correctIds.contains(it) }.toSet<String>()
            assertThat(relationOfGraph).isEqualTo(relationToBe)
        }
    }
}
