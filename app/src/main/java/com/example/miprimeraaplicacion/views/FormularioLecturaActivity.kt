package com.example.miprimeraaplicacion.views

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.miprimeraaplicacion.R
import com.example.miprimeraaplicacion.models.RegistroLuz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FormularioLecturaActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var txtTituloFormulario: TextView
    private lateinit var edtUbicacion: EditText
    private lateinit var edtNivelLuz: EditText
    private lateinit var edtEstado: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var btnGuardar: Button

    private var registroIdEditar: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_lectura)

        db = Firebase.firestore

        txtTituloFormulario = findViewById(R.id.txtTituloFormulario)
        edtUbicacion = findViewById(R.id.edtUbicacion)
        edtNivelLuz = findViewById(R.id.edtNivelLuz)
        edtEstado = findViewById(R.id.edtEstado)
        progressBar = findViewById(R.id.progressBar)
        btnGuardar = findViewById(R.id.btnGuardar)

        // Acción del nuevo botón Volver
        val btnVolverForm = findViewById<Button>(R.id.btnVolverForm)
        btnVolverForm.setOnClickListener {
            finish()
        }

        registroIdEditar = intent.getStringExtra("registroId")

        if (registroIdEditar != null) {
            txtTituloFormulario.text = "Editar Registro de Luz"
            edtUbicacion.setText(intent.getStringExtra("registroUbicacion"))
            val nivel = intent.getIntExtra("registroNivel", 0)
            edtNivelLuz.setText(nivel.toString())
            edtEstado.setText(intent.getStringExtra("registroEstado"))
        } else {
            txtTituloFormulario.text = "Nuevo Registro de Luz"
        }
    }

    fun onGuardarLecturaClick(view: View) {
        val ubicacion = edtUbicacion.text.toString().trim()
        val nivelString = edtNivelLuz.text.toString().trim()
        val estado = edtEstado.text.toString().trim()

        var hayErrores = false

        if (ubicacion.isEmpty()) {
            edtUbicacion.error = "La ubicación es obligatoria"
            hayErrores = true
        }
        if (nivelString.isEmpty()) {
            edtNivelLuz.error = "El nivel de luz es obligatorio"
            hayErrores = true
        }
        if (estado.isEmpty()) {
            edtEstado.error = "El estado es obligatorio"
            hayErrores = true
        }

        if (hayErrores) return

        val nivelInt = nivelString.toIntOrNull()
        if (nivelInt == null) {
            edtNivelLuz.error = "Ingrese un número válido"
            return
        }

        // Mostrar rueda de carga y bloquear el botón para evitar duplicados
        progressBar.visibility = View.VISIBLE
        btnGuardar.isEnabled = false

        if (registroIdEditar != null) {
            val registroActualizado = RegistroLuz(id = registroIdEditar!!, ubicacion = ubicacion, nivelLuz = nivelInt, estado = estado)

            db.collection("registrosLuz").document(registroIdEditar!!)
                .set(registroActualizado)
                .addOnSuccessListener {
                    Toast.makeText(this, "Registro actualizado correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    // Si falla, ocultar rueda y reactivar botón
                    progressBar.visibility = View.GONE
                    btnGuardar.isEnabled = true
                    Toast.makeText(this, "Error al actualizar: ${e.message}", Toast.LENGTH_LONG).show()
                }
        } else {
            val nuevoRegistro = RegistroLuz(ubicacion = ubicacion, nivelLuz = nivelInt, estado = estado)

            db.collection("registrosLuz")
                .add(nuevoRegistro)
                .addOnSuccessListener {
                    Toast.makeText(this, "Registro guardado correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    // Si falla, ocultar rueda y reactivar botón
                    progressBar.visibility = View.GONE
                    btnGuardar.isEnabled = true
                    Toast.makeText(this, "Error al guardar: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}