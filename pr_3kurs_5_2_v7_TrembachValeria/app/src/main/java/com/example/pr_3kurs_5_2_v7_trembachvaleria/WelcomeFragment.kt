package com.example.pr_3kurs_5_2_v7_trembachvaleria

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editLogin = view.findViewById<EditText>(R.id.editLogin)
        val editPassword = view.findViewById<EditText>(R.id.editPassword)
        val editConfirm = view.findViewById<EditText>(R.id.editConfirmPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)
        val textError = view.findViewById<TextView>(R.id.textError)

        val prefs = PrefsHelper(requireContext())

        btnLogin.setOnClickListener {
            val login = editLogin.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if (login.isEmpty() || password.isEmpty()) {
                textError.text = "Введите логин и пароль!"
                return@setOnClickListener
            }

            if (login == prefs.getLogin() && password == prefs.getPassword()) {
                textError.text = ""
                (activity as? MainActivity)?.goToSearch()
            } else {
                textError.text = "Неверный логин или пароль!"
            }
        }

        btnRegister.setOnClickListener {
            val login = editLogin.text.toString().trim()
            val password = editPassword.text.toString().trim()
            val confirm = editConfirm.text.toString().trim()

            if (login.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                textError.text = "Заполните все поля!"
                return@setOnClickListener
            }

            if (password != confirm) {
                textError.text = "Пароли не совпадают!"
                return@setOnClickListener
            }

            prefs.saveUser(login, password)
            textError.text = ""
            Toast.makeText(requireContext(), "Регистрация успешна!", Toast.LENGTH_SHORT).show()
            editPassword.text.clear()
            editConfirm.text.clear()
        }
    }
}