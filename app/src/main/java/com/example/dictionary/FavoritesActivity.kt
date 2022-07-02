package com.example.dictionary

import adapters.RecyclerViewFavoritesAdapter
import adapters.RecyclerViewMainAdapter
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.databinding.ActivityFavoritesBinding
import com.example.dictionary.databinding.ActivityHistoryBinding

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var builder: AlertDialog.Builder
    lateinit var db: DatabaseAccess
    lateinit var list: ArrayList<Word>
    lateinit var adapter: RecyclerViewMainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Favorites"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getList()
        builder = AlertDialog.Builder(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.clear_history -> {
                showDialog()
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
        list = db.getFavorites()
        adapter = RecyclerViewMainAdapter(list)
        binding.recycleViewFavorites.adapter = adapter
        binding.recycleViewFavorites.layoutManager = LinearLayoutManager(this)
    }

    private fun showDialog(){
        builder.setTitle("Delete Favorites")
        builder.setMessage("Are you sure you want to delete favorites?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){DialogInterface, it ->
            db.clearFavorites()
            getList()
        }
        builder.setNegativeButton("No"){DialogInterface, it ->
            DialogInterface.cancel()
        }
        builder.show()
    }
}