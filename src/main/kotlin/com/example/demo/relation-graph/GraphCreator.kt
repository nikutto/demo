package com.example.demo.relationgraph

import com.example.demo.api.SpotifyApiClient
import com.example.demo.api.dto.Artist
import com.example.demo.relationgraph.dto.Graph
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class GraphCreator(
    @Autowired val spotifyApiClient: SpotifyApiClient
) {

    fun create(id: String, n: Int): Graph {
        val uncheckedSet = mutableMapOf<String, Int>()
        uncheckedSet[id] = 0
        val checkedSet = mutableSetOf<String>()
        val relatedArtists = mutableMapOf<String, List<Artist>>()

        val graph = Graph()

        for (_i in 0 until n) {
            if (uncheckedSet.isEmpty()) break
            val maxDegree: Int = uncheckedSet.maxByOrNull { it.value }!!.value
            val candidates = uncheckedSet.filter {
                it.value == maxDegree
            }
            val importantId = candidates.toList().random().first

            uncheckedSet.remove(importantId)
            checkedSet.add(importantId)

            val response = spotifyApiClient.getRelatedArtists(importantId)
            val artists = response.execute().body()!!.artists
            relatedArtists[importantId] = artists

            for (artist in artists) {
                if (checkedSet.contains(artist.id)) continue
                val preDeg = uncheckedSet[artist.id] ?: 0
                uncheckedSet[artist.id] = preDeg + 1
            }
        }

        for ((targetId, _) in relatedArtists) {
            graph.addVertex(targetId)
        }
        for ((targetId, artists) in relatedArtists) {
            for (artist in artists) {
                if (graph.containsVertexOf(artist.id)) {
                    graph.addEdge(targetId, artist.id)
                }
            }
        }
        return graph
    }
}
