package com.example.movie_book_tracker

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.prefs.Preferences

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()
    private var itemList = mutableListOf<movie_book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("movie_book_tracker", MODE_PRIVATE)

        loadData()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        myAdapter = MyAdapter(itemList)
        recyclerView.adapter = myAdapter

        val confirmButton = findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener {
            addItem()
        }
    }

    private fun addItem() {
        val tytul = findViewById<EditText>(R.id.editText_tytul).text.toString()
        val gatunek = findViewById<EditText>(R.id.editText_gatunek).text.toString()
        val opis = findViewById<EditText>(R.id.editText_opis).text.toString()
        val ocena = findViewById<SeekBar>(R.id.seekBar_ocena).progress
        val filmCzyKsiazka = if (findViewById<RadioButton>(R.id.radioButton_ksiazka).isChecked) {
            "Książka"
        } else {
            "Film"
        }
        val czyObejrzane = findViewById<CheckBox>(R.id.checkBox_objechane).isChecked

        if (tytul.isNotEmpty() && gatunek.isNotEmpty() && opis.isNotEmpty()) {
            val newItem = movie_book(tytul, gatunek, opis, ocena, filmCzyKsiazka, czyObejrzane)
            itemList.add(newItem)
            myAdapter.notifyDataSetChanged()
            saveData()
        } else {
            Toast.makeText(this, "Wypełnij wszystkie pola!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveData() {
        val jsonString = gson.toJson(itemList)
        sharedPreferences.edit().putString("itemList", jsonString).apply()
    }

    private fun loadData() {
        val jsonString = sharedPreferences.getString("itemList", null)
        if (!jsonString.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<movie_book>>() {}.type
            itemList = gson.fromJson(jsonString, type)
        }
    }
}