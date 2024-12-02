package com.example.latihan1

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class halamanEdit : AppCompatActivity() {

    private lateinit var etNamaTask: EditText
    private lateinit var etTanggal: EditText
    private lateinit var etKategori: EditText
    private lateinit var etDeskripsi: EditText
    private lateinit var btnSimpan: Button
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_halaman_edit)

        etNamaTask = findViewById(R.id.namaTask)
        etTanggal = findViewById(R.id.tanggal)
        etKategori = findViewById(R.id.kategori)
        etDeskripsi = findViewById(R.id.deskripsi)
        btnSimpan = findViewById(R.id.simpan)

        // Mengambil data jika dalam mode edit
        val task = intent.getParcelableExtra<task>("task")
        position = intent.getIntExtra("position", -1)
        task?.let {
            etNamaTask.setText(it.namaTask)
            etTanggal.setText(it.tanggal)
            etKategori.setText(it.kategori)
            etDeskripsi.setText(it.deskripsi)
        }

        btnSimpan.setOnClickListener {
            val resultTask = task(
                namaTask = etNamaTask.text.toString(),
                tanggal = etTanggal.text.toString(),
                kategori = etKategori.text.toString(),
                deskripsi = etDeskripsi.text.toString(),
                isCompleted = task?.isCompleted ?: false
            )
            val resultIntent = intent
            resultIntent.putExtra("task", resultTask)
            resultIntent.putExtra("position", position)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
