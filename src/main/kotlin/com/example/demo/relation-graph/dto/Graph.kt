package com.example.demo.relationgraph.dto

data class Edge(
    val from: String,
    val to: String
)

class Graph {

    val g = HashMap<String, MutableList<Edge>>()

    fun addEdge(from: String, to: String) {
        g[from]!!.add(Edge(from, to))
        g[to]!!.add(Edge(to, from))
    }

    fun addVertex(id: String) {
        g[id] = mutableListOf()
    }

    fun getOutgoingEdgesList(id: String) = g[id]!!.toList()
    fun containsVertexOf(id: String) = g.containsKey(id)
}
