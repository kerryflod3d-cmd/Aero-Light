package com.example.miprimeraaplicacion.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miprimeraaplicacion.R
import com.example.miprimeraaplicacion.models.RegistroLuz

class RegistroLuzAdapter(
    private var listaRegistros: List<RegistroLuz> = listOf(),
    private val onEditarClick: (RegistroLuz) -> Unit,
    private val onEliminarClick: (RegistroLuz) -> Unit
) : RecyclerView.Adapter<RegistroLuzAdapter.RegistroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lectura, parent, false)
        return RegistroViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegistroViewHolder, position: Int) {
        val registro = listaRegistros[position]
        holder.bind(registro, onEditarClick, onEliminarClick)
    }

    override fun getItemCount(): Int {
        return listaRegistros.size
    }

    fun actualizarLista(nuevaLista: List<RegistroLuz>) {
        listaRegistros = nuevaLista
        notifyDataSetChanged()
    }

    class RegistroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtDescripcion: TextView = itemView.findViewById(R.id.txtDescripcion)
        private val txtValor: TextView = itemView.findViewById(R.id.txtValor)
        private val btnEliminar: ImageButton = itemView.findViewById(R.id.btnEliminar)

        fun bind(registro: RegistroLuz, onEditarClick: (RegistroLuz) -> Unit, onEliminarClick: (RegistroLuz) -> Unit) {
            txtDescripcion.text = "Ubicación: ${registro.ubicacion}"
            txtValor.text = "Luz: ${registro.nivelLuz} | Estado: ${registro.estado}"

            itemView.setOnClickListener {
                onEditarClick(registro)
            }

            btnEliminar.setOnClickListener {
                onEliminarClick(registro)
            }
        }
    }
}