package com.example.dictionary

import adapters.RecyclerViewFavoritesAdapter
import adapters.RecyclerViewMainAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.UserDictionary
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    companion object{
        lateinit var db: DatabaseAccess
    }
    private lateinit var list: ArrayList<Word>
    lateinit var adapter: RecyclerViewMainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseAccess(this)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        list = db.getWords(binding.etSearch.text.toString())
        adapter = RecyclerViewMainAdapter(list)
        binding.recycleViewMain.adapter = adapter
        binding.recycleViewMain.layoutManager = LinearLayoutManager(this)
        binding.etSearch.addTextChangedListener {
            list = db.getWords(binding.etSearch.text.toString())
            adapter = RecyclerViewMainAdapter(list)
            binding.recycleViewMain.adapter = adapter
            binding.recycleViewMain.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.about -> {
                startActivity(Intent(this, AboutActivity::class.java))
            }
            R.id.favorites ->{
                startActivity(Intent(this, FavoritesActivity::class.java))
            }
            R.id.history_lv ->{
                startActivity(Intent(this, HistoryActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

/*    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            121 -> {
                RecyclerViewMainAdapter.staticWord.id?.let { db.addToFavorites(it) }
                //db.addToFavorites(list[binding.recycleViewMain.adapter.get])
                showToast("${list[RecyclerViewMainAdapter.positionInList].word} added to favorites")
                //showToast("${RecyclerViewMainAdapter.staticWord.word} added to favorites")
            }
        }
        return super.onContextItemSelected(item)
    }*/
}

/*
try {
    helper = Db(this)
    helper.getWords("a")
} catch (e: Exception) {
    println("Abdo <> " + e.message)
}*/
