package com.example.inv2_dsm.services

import com.example.inv2_dsm.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET

interface WebService {
    @GET("pokedex.json")

    suspend fun getPokemons(): Response<PokemonResponse>
}