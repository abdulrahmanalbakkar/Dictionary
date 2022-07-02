package adapters

import android.location.GnssAntennaInfo
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.FavoritesActivity
import com.example.dictionary.MainActivity
import com.example.dictionary.R
import com.example.dictionary.Word


class RecyclerViewFavoritesAdapter(private val WordsList: ArrayList<Word>): RecyclerView.Adapter<RecyclerViewFavoritesAdapter.RecyclerViewFavoritesViewHolder>() {

    companion object{
        var positionInList = 0
        var staticWord: Word = Word(null, null, null, null, null, null, null, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewFavoritesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_item_delete, parent, false)

        return RecyclerViewFavoritesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewFavoritesViewHolder, position: Int) {
        val currentItem = WordsList[position]
        holder.tv1.text = currentItem.word
        holder.tv2.text = currentItem.type
        holder.tv3.text = currentItem.meaning
        holder.tv4.text = currentItem.example_sentence
        holder.delFavBtn.setOnClickListener {
            staticWord.id = WordsList[position].id
            staticWord.id?.let { it1 -> MainActivity.db.removeFromFavorites(it1) }
        }
    }

    override fun getItemCount(): Int {
        return WordsList.size
    }

    inner class RecyclerViewFavoritesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tv1: TextView = itemView.findViewById(R.id.tv_word1)
        val tv2: TextView = itemView.findViewById(R.id.tv_word2)
        val tv3: TextView = itemView.findViewById(R.id.tv_word3)
        val tv4: TextView = itemView.findViewById(R.id.tv_word4)
        val delFavBtn: Button = itemView.findViewById(R.id.favorites_btn_delete)
    }
}