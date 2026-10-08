package com.example.miprimeraaplicacion.views

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.miprimeraaplicacion.R

class PreferenciasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preferencias)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Buscamos el Spinner y le inyectamos nuestras propias opciones para el proyecto IoT
        val spinnerZonas = findViewById<Spinner>(R.id.spZonaMonitoreo)
        val opciones = arrayOf("Puerta Principal", "Cortina Metálica", "Vitrina 1", "Acceso Trasero")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opciones)
        spinnerZonas.adapter = adapter

        // Buscamos el botón volver que agregaste en el XML y le damos la acción de cerrar la pantalla
        val btnVolver = findViewById<Button>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            finish() // Esto destruye la pantalla actual y te regresa a la anterior
        }
    }
}