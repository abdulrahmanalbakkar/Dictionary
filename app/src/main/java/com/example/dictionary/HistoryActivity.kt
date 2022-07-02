package com.example.dictionary

import adapters.RecyclerViewHistoryAdapter
import adapters.RecyclerViewMainAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.databinding.ActivityHistoryBinding
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.databinding.ActivitySettingsBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    lateinit var db: DatabaseAccess

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "History"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getList()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.clear_history -> {
                db.clearHistory()
                val list: ArrayList<Word> = db.getHistory()
                val adapter = RecyclerViewMainAdapter(list)
                binding.recycleViewHistory.adapter = adapter
                binding.recycleViewHistory.layoutManager = LinearLayoutManager(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun getList(){
        db = DatabaseAccess(this)
        val list: ArrayList<Word> = db.getHistory()
        val adapter = RecyclerViewHistoryAdapter(list)
        binding.recycleViewHistory.adapter = adapter
        binding.recycleViewHistory.layoutManager = LinearLayoutManager(this)
    }
}