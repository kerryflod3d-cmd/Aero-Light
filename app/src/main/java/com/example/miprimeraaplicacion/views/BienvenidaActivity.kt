package com.example.miprimeraaplicacion.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.miprimeraaplicacion.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BienvenidaActivity : AppCompatActivity() {

    private var nombreUsuario: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bienvenida)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recibimos el correo desde el Login
        nombreUsuario = intent.getStringExtra("usuario")
        val txtBienvenida = findViewById<TextView>(R.id.txtBienvenida)

        // Cortamos el correo antes del "@" y capitalizamos la primera letra
        val nombreCorto = nombreUsuario?.substringBefore("@")?.replaceFirstChar { it.uppercase() } ?: "Usuario"

        // Asignamos el nuevo formato de bienvenida
        txtBienvenida.text = "Sistema de Vigilancia Activo\n\nBienvenido, $nombreCorto"
    }

    fun onPreferenciasClick(view: View) {
        val intent = Intent(this, PreferenciasActivity::class.java)
        intent.putExtra("usuario", nombreUsuario)
        startActivity(intent)
    }

    fun onVerLecturasClick(view: View) {
        val intent = Intent(this, ListaActivity::class.java)
        startActivity(intent)
    }

    fun onCerrarSesionClick(view: View) {
        Firebase.auth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}