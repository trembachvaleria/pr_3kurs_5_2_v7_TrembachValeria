package com.example.pr_3kurs_5_2_v7_trembachvaleria

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class FavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textList = view.findViewById<TextView>(R.id.textList)
        val btnBack = view.findViewById<Button>(R.id.btnBack)

        val repository = MovieRepository(requireContext())
        val list = repository.getAll()

        if (list.isEmpty()) {
            textList.text = "Список пуст"
        } else {
            val sb = StringBuilder()
            list.forEachIndexed { i, movie ->
                sb.append("${i + 1}) ${movie.title} (${movie.year})\n")
                sb.append("Режиссёр: ${movie.director}\n")
                sb.append("Актёры: ${movie.actors}\n\n")
            }
            textList.text = sb.toString()
        }

        btnBack.setOnClickListener {
            (activity as? MainActivity)?.goToSearch()
        }
    }
}