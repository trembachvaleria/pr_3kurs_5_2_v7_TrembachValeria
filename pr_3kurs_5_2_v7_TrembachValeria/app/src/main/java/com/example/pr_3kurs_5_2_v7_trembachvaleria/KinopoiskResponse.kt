package com.example.pr_3kurs_5_2_v7_trembachvaleria

import com.google.gson.annotations.SerializedName

data class KinopoiskResponse(
    @SerializedName("docs") val docs: List<MovieDto>? = null
)

data class MovieDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("year") val year: Int? = null,
    @SerializedName("persons") val persons: List<Person>? = null
)

data class Person(
    @SerializedName("name") val name: String? = null,
    @SerializedName("profession") val profession: String? = null
)