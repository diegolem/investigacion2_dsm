package com.example.inv2_dsm.repository

import com.example.inv2_dsm.services.RetrofitClient
import com.example.inv2_dsm.services.WebService

class PokemonRepository {
    private var apiService: WebService? = null

    init {
        apiService = RetrofitClient.getClient?.create(WebService::class.java)
    }

    suspend fun getPokemon() = apiService?.getPokemons()
}