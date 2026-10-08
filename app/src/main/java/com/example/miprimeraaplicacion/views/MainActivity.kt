package com.example.miprimeraaplicacion.views

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.miprimeraaplicacion.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var mostrandoPassword: Boolean = false
    var intentosFallidos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth
    }

    fun onMostrarPasswordClick(view: View) {
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        if (!mostrandoPassword) {
            edtPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            mostrandoPassword = true
        } else {
            edtPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            mostrandoPassword = false
        }
        edtPassword.setSelection(edtPassword.text.length)
    }

    fun onIngresarClick(view: View) {
        val edtUsuario = findViewById<EditText>(R.id.edtUsuario)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val progressBarLogin = findViewById<ProgressBar>(R.id.progressBarLogin)

        val usuario = edtUsuario.text.toString().trim()
        val password = edtPassword.text.toString()

        var esValido = true

        if (usuario.isEmpty()) {
            edtUsuario.error = "Ingresa tu email de usuario"
            esValido = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(usuario).matches()) {
            edtUsuario.error = "Ingresa un email válido"
            esValido = false
        } else {
            edtUsuario.error = null
        }

        if (password.isEmpty()) {
            edtPassword.error = "Ingresa tu contraseña"
            esValido = false
        } else if (password.length < 6) {
            edtPassword.error = "Mínimo 6 caracteres"
            esValido = false
        } else {
            edtPassword.error = null
        }

        if (!esValido) {
            intentosFallidos++
        } else {
            // Activar rueda de carga y deshabilitar botón para evitar dobles clics
            progressBarLogin.visibility = View.VISIBLE
            btnIngresar.isEnabled = false

            auth.signInWithEmailAndPassword(usuario, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = auth.currentUser
                        val emailUsuario = firebaseUser?.email ?: usuario

                        // Ocultar la rueda y habilitar botón de nuevo (por si el usuario vuelve)
                        progressBarLogin.visibility = View.GONE
                        btnIngresar.isEnabled = true

                        val intent = Intent(this, BienvenidaActivity::class.java)
                        intent.putExtra("usuario", emailUsuario)
                        startActivity(intent)
                    } else {
                        // Error: Ocultar rueda, reactivar botón y sumar intento
                        progressBarLogin.visibility = View.GONE
                        btnIngresar.isEnabled = true
                        intentosFallidos++
                        val mensajeError = task.exception?.localizedMessage ?: "Error al autenticar usuario"
                        Toast.makeText(this, "Error de autenticación: $mensajeError", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    fun onLimpiarClick(view: View) {
        val edtUsuario = findViewById<EditText>(R.id.edtUsuario)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val chkRecordarme = findViewById<CheckBox>(R.id.chkRecordarme)

        edtUsuario.setText("")
        edtPassword.setText("")
        edtUsuario.error = null
        edtPassword.error = null
        chkRecordarme.isChecked = false
    }

    fun onIrARegistroClick(view: View) {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
}