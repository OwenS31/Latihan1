package com.example.latihan1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterRecView(
    private val listTask: ArrayList<task>,
    private val onDelete: (Int) -> Unit,
    private val onEdit: (Int) -> Unit,
    private val onComplete: (Int) -> Unit,
    private val onSharedClicked: () -> Unit // Tambahkan callback untuk klik pada ImageView shared
) : RecyclerView.Adapter<AdapterRecView.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaTask: TextView = itemView.findViewById(R.id.namaTask)
        val tanggal: TextView = itemView.findViewById(R.id.tanggalData)
        val kategori: TextView = itemView.findViewById(R.id.kategori)
        val deskripsi: TextView = itemView.findViewById(R.id.deskripsi)
        val btnHapus: Button = itemView.findViewById(R.id.hapus)
        val btnUbah: Button = itemView.findViewById(R.id.ubah)
        val btnKerjakan: Button = itemView.findViewById(R.id.kerjakan)
        val shared: ImageView = itemView.findViewById(R.id.shared) // Tambahkan akses ke ImageView shared
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listTask.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val task = listTask[position]

        // Set data ke tampilan
        holder.namaTask.text = task.namaTask
        holder.tanggal.text = task.tanggal
        holder.kategori.text = task.kategori
        holder.deskripsi.text = task.deskripsi

        // Ubah teks tombol sesuai status
        holder.btnKerjakan.text = if (task.isCompleted) "Selesai" else "Selesaikan"

        // Set klik listener untuk tombol
        holder.btnHapus.setOnClickListener { onDelete(position) }
        holder.btnUbah.setOnClickListener { onEdit(position) }
        holder.btnKerjakan.setOnClickListener { onComplete(position) }

        // Set klik listener untuk ImageView shared
        holder.shared.setOnClickListener {
            onSharedClicked() // Panggil callback saat ImageView diklik
        }
    }
}
