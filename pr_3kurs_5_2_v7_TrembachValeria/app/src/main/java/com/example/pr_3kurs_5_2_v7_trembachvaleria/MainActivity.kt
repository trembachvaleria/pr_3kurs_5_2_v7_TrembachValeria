package com.example.pr_3kurs_5_2_v7_trembachvaleria

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = PrefsHelper(this)

        if (savedInstanceState == null) {
            if (prefs.isLoggedIn()) {
                goToSearch()
            } else {
                goToWelcome()
            }
        }
    }

    fun goToWelcome() {
        replaceFragment(WelcomeFragment())
    }

    fun goToSearch() {
        replaceFragment(SearchFragment())
    }

    fun goToFavorites() {
        replaceFragment(FavoritesFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}