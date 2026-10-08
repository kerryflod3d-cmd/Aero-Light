package com.example.miprimeraaplicacion.views

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miprimeraaplicacion.R
import com.example.miprimeraaplicacion.adapters.RegistroLuzAdapter
import com.example.miprimeraaplicacion.models.RegistroLuz
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListaActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: RegistroLuzAdapter
    private lateinit var rvLecturas: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        db = Firebase.firestore

        // Configuramos el adaptador con nuestra nueva clase RegistroLuz
        adapter = RegistroLuzAdapter(
            onEditarClick = { registroAEditar ->
                val intent = Intent(this, FormularioLecturaActivity::class.java)
                // Estas llaves deben ser exactas a las que recibe el FormularioLecturaActivity
                intent.putExtra("registroId", registroAEditar.id)
                intent.putExtra("registroUbicacion", registroAEditar.ubicacion)
                intent.putExtra("registroNivel", registroAEditar.nivelLuz)
                intent.putExtra("registroEstado", registroAEditar.estado)
                startActivity(intent)
            },
            onEliminarClick = { registroAEliminar ->
                mostrarDialogoConfirmacion(registroAEliminar)
            }
        )

        rvLecturas = findViewById(R.id.rvLecturas)
        rvLecturas.layoutManager = LinearLayoutManager(this)
        rvLecturas.adapter = adapter

        escucharRegistros()

        val fabAgregar = findViewById<FloatingActionButton>(R.id.fabAgregarLectura)
        fabAgregar.setOnClickListener {
            val intent = Intent(this, FormularioLecturaActivity::class.java)
            startActivity(intent)
        }

        // Botón volver
        val btnVolver = findViewById<Button>(R.id.btnVolverLista)
        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun escucharRegistros() {
        // Apuntamos a tu colección personalizada exigida por la rúbrica
        db.collection("registrosLuz").addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.w("ListaActivity", "Error al escuchar los registros.", error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val listaRegistros = snapshot.toObjects(RegistroLuz::class.java)
                Log.d("ListaActivity", "Registros recuperados: ${listaRegistros.size}")
                adapter.actualizarLista(listaRegistros)
            }
        }
    }

    private fun mostrarDialogoConfirmacion(registro: RegistroLuz) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar Registro")
        // Personalizamos el mensaje con el nombre de la ubicación
        builder.setMessage("¿Estás seguro de que deseas eliminar la vigilancia de '${registro.ubicacion}'?")

        builder.setPositiveButton("Sí, eliminar") { dialog, _ ->
            eliminarRegistro(registro)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    private fun eliminarRegistro(registro: RegistroLuz) {
        // Aseguramos que se elimine desde la misma colección
        db.collection("registrosLuz").document(registro.id)
            .delete()
            .addOnSuccessListener {
                Log.d("ListaActivity", "Documento eliminado con éxito!")
            }
            .addOnFailureListener { e ->
                Log.w("ListaActivity", "Error al eliminar documento", e)
            }
    }
}