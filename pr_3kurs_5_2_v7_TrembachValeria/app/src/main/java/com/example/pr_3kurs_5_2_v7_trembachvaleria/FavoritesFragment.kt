package com.example.pr_3kurs_5_2_v7_trembachvaleria

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class FavoritesFragment : Fragment() {

    private var selectedMovieId: Int? = null
    private var selectedView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listContainer = view.findViewById<LinearLayout>(R.id.listContainer)
        val btnBack = view.findViewById<Button>(R.id.btnBack)
        val btnDeleteSelected = view.findViewById<Button>(R.id.btnDeleteSelected)
        val repository = MovieRepository(requireContext())

        fun loadMovies() {
            listContainer.removeAllViews()
            selectedMovieId = null
            selectedView = null

            val prefs = PrefsHelper(requireContext())
            val list = repository.getAll(prefs.getLogin())

            if (list.isEmpty()) {
                val tv = TextView(requireContext())
                tv.text = "Список пуст"
                tv.textSize = 18f
                tv.setTextColor(Color.WHITE)
                listContainer.addView(tv)
            } else {
                for (movie in list) {
                    val tv = TextView(requireContext())
                    tv.text = "${movie.title} (${movie.year})\nРежиссёр: ${movie.director}\nАктёры: ${movie.actors}\n"
                    tv.textSize = 16f
                    tv.setPadding(20, 20, 20, 20)
                    tv.setTextColor(Color.WHITE)

                    tv.setOnClickListener {
                        selectedView?.setBackgroundColor(Color.TRANSPARENT)
                        selectedView?.setTextColor(Color.WHITE)

                        selectedMovieId = movie.id
                        selectedView = tv

                        tv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange))
                        tv.setTextColor(Color.WHITE)
                    }

                    listContainer.addView(tv)
                }
            }
        }

        loadMovies()

        btnDeleteSelected.setOnClickListener {
            if (selectedMovieId == null) {
                Toast.makeText(requireContext(), "Сначала выберите фильм", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(requireContext())
                .setTitle("Удаление")
                .setMessage("Вы уверены, что хотите удалить этот фильм из избранного?")
                .setPositiveButton("Да") { dialog, _ ->
                    try {
                        repository.deleteById(selectedMovieId!!)
                        Toast.makeText(requireContext(), "Фильм удален", Toast.LENGTH_SHORT).show()
                        loadMovies()
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Ошибка: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Нет") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }

        btnBack.setOnClickListener {
            (activity as? MainActivity)?.goToSearch()
        }
    }
}