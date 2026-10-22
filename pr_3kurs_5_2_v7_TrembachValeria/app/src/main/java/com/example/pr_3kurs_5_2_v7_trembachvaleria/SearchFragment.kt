package com.example.pr_3kurs_5_2_v7_trembachvaleria

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class SearchFragment : Fragment() {

    private val key = "A7DE5T6-AQX46C4-MQ7MSX3-40WD2QY"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editQuery = view.findViewById<EditText>(R.id.editQuery)
        val btnSearch = view.findViewById<Button>(R.id.btnSearch)
        val btnFavorites = view.findViewById<Button>(R.id.btnFavorites)
        val textResult = view.findViewById<TextView>(R.id.textResult)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        val repository = MovieRepository(requireContext())

        var currentTitle: String = ""
        var currentYear: String = ""
        var currentDirector: String = "неизвестно"
        var currentActors: String = "неизвестно"
        var isMovieFound = false

        btnSearch.setOnClickListener {
            val query = editQuery.text.toString().trim()
            if (query.isEmpty()) {
                Toast.makeText(requireContext(), "Введите название", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            textResult.text = "Поиск..."
            isMovieFound = false

            searchMovieId(query) { movieDto ->
                if (movieDto != null && movieDto.id != null) {
                    currentTitle = movieDto.name ?: "Без названия"
                    currentYear = movieDto.year?.toString() ?: "Год неизвестен"

                    getMovieDetails(movieDto.id) { director, actors ->
                        currentDirector = director
                        currentActors = actors
                        isMovieFound = true

                        textResult.text = "Название: $currentTitle\nГод: $currentYear\nРежиссёр: $currentDirector\nАктёры: $currentActors"
                    }
                } else {
                    textResult.text = "Ничего не найдено"
                }
            }
        }

        btnSave.setOnClickListener {
            if (!isMovieFound) {
                Toast.makeText(requireContext(), "Сначала найдите фильм", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val movie = Movie(
                title = currentTitle,
                year = currentYear,
                director = currentDirector,
                actors = currentActors
            )
            repository.insert(movie)
            Toast.makeText(requireContext(), "Сохранено!", Toast.LENGTH_SHORT).show()
        }

        btnFavorites.setOnClickListener {
            (activity as? MainActivity)?.goToFavorites()
        }
    }

    private fun searchMovieId(query: String, onResult: (MovieDto?) -> Unit) {
        val url = "https://api.kinopoisk.dev/v1.4/movie/search?query=$query"
        val queue = Volley.newRequestQueue(requireContext())

        val request = object : StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("MyLog", "Search Response: $response")
                try {
                    val result = Gson().fromJson(response, KinopoiskResponse::class.java)
                    onResult(result.docs?.firstOrNull())
                } catch (e: Exception) {
                    Log.e("MyLog", "Parse error", e)
                    onResult(null)
                }
            },
            { error ->
                Log.d("MyLog", "Search Error: ${error.message}")
                onResult(null)
            }
        ) {
            override fun getHeaders() = mutableMapOf("X-API-KEY" to key)
        }
        queue.add(request)
    }

    private fun getMovieDetails(movieId: Int, onResult: (String, String) -> Unit) {
        val url = "https://api.kinopoisk.dev/v1.4/movie/$movieId"
        val queue = Volley.newRequestQueue(requireContext())

        val request = object : StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("MyLog", "Details Response: $response")
                try {
                    val details = Gson().fromJson(response, MovieDto::class.java)

                    val persons = details.persons ?: emptyList()

                    val directors = persons.filter {
                        it.profession?.contains("режисс", true) == true || it.profession == "director"
                    }.joinToString(", ") { it.name ?: "" }

                    val actors = persons.filter {
                        it.profession?.contains("актер", true) == true || it.profession == "actor"
                    }.take(3).joinToString(", ") { it.name ?: "" }

                    onResult(
                        if (directors.isEmpty()) "неизвестно" else directors,
                        if (actors.isEmpty()) "неизвестно" else actors
                    )
                } catch (e: Exception) {
                    Log.e("MyLog", "Details Parse error", e)
                    onResult("ошибка парсинга", "ошибка парсинга")
                }
            },
            { error ->
                Log.d("MyLog", "Details Error: ${error.message}")
                onResult("ошибка сети", "ошибка сети")
            }
        ) {
            override fun getHeaders() = mutableMapOf("X-API-KEY" to key)
        }
        queue.add(request)
    }
}