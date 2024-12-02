package com.example.latihan1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private var arTask = arrayListOf<task>()
    private lateinit var rvData: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var adapter: AdapterRecView
    private lateinit var sharedPreferences: SharedPreferences
    private val SHARED_PREFS_NAME = "task_prefs"
    private val TASK_LIST_KEY = "task_list"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)

        // Muat data dari SharedPreferences
        loadTasks()

        // Inisialisasi RecyclerView
        setupRecyclerView()

        // Tombol tambah data
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = AdapterRecView(arTask,
            onDelete = { position ->
                arTask.removeAt(position)
                adapter.notifyItemRemoved(position)
                saveTasks() // Simpan data setelah dihapus
            },
            onEdit = { position ->
                val task = arTask[position]
                val intent = Intent(this, halamanEdit::class.java)
                intent.putExtra("task", task)
                intent.putExtra("position", position)
                startActivityForResult(intent, REQUEST_EDIT_TASK)
            },
            onComplete = { position ->
                arTask[position].isCompleted = true
                adapter.notifyItemChanged(position)
                saveTasks() // Simpan data setelah diperbarui
            },
            onSharedClicked = {
                showSharedPreferencesDialog() // Fungsi untuk menampilkan isi SharedPreferences
            }

        )
        rvData = findViewById(R.id.rvData)
        rvData.layoutManager = LinearLayoutManager(this)
        rvData.adapter = adapter
    }

    private fun setupListeners() {
        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, halamanEdit::class.java)
            startActivityForResult(intent, REQUEST_ADD_TASK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            val task = data.getParcelableExtra<task>("task")
            val position = data.getIntExtra("position", -1)

            when (requestCode) {
                REQUEST_ADD_TASK -> {
                    if (task != null) {
                        arTask.add(task)
                        adapter.notifyItemInserted(arTask.size - 1)
                        saveTasks() // Simpan data setelah ditambahkan
                    }
                }
                REQUEST_EDIT_TASK -> {
                    if (task != null && position != -1) {
                        arTask[position] = task
                        adapter.notifyItemChanged(position)
                        saveTasks() // Simpan data setelah diperbarui
                    }
                }
            }
        }
    }

    // Fungsi untuk menyimpan data ke SharedPreferences
    private fun saveTasks() {
        val gson = Gson()
        val json = gson.toJson(arTask)
        sharedPreferences.edit()
            .putString(TASK_LIST_KEY, json)
            .apply()
    }

    // Fungsi untuk memuat data dari SharedPreferences
    private fun loadTasks() {
        val gson = Gson()
        val json = sharedPreferences.getString(TASK_LIST_KEY, null)
        val type = object : TypeToken<ArrayList<task>>() {}.type
        if (json != null) {
            arTask = gson.fromJson(json, type)
        }
    }
    private fun showSharedPreferencesDialog() {
        val gson = Gson()
        val json = sharedPreferences.getString(TASK_LIST_KEY, "Tidak ada data")

    }

    companion object {
        const val REQUEST_ADD_TASK = 1
        const val REQUEST_EDIT_TASK = 2
    }
}
