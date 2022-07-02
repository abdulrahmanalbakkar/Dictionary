package adapters

import android.content.Context
import android.provider.UserDictionary
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.DatabaseAccess
import com.example.dictionary.MainActivity
import com.example.dictionary.R
import com.example.dictionary.Word

class RecyclerViewHistoryAdapter(private val WordsList: ArrayList<Word>): RecyclerView.Adapter<RecyclerViewHistoryAdapter.RecyclerViewHistoryViewHolder>() {

    companion object{
        var positionInList = 0
        var staticWord: Word = Word(null, null, null, null, null, null, null, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_item, parent, false)
        return RecyclerViewHistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewHistoryViewHolder, position: Int) {
        val currentItem = WordsList[position]
        holder.tv1.text = currentItem.word
        holder.tv2.text = currentItem.type
        holder.tv3.text = currentItem.meaning
        holder.tv4.text = currentItem.example_sentence
        if (WordsList[position].favorites.equals("0")){
            staticWord.favorites = "1"
            holder.favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
        }
        else{
            staticWord.favorites = "0"
            holder.favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite)
        }
        val isExpandable: Boolean = WordsList[position].expandable
        holder.tv2.visibility = if(isExpandable) View.VISIBLE else View.VISIBLE
        holder.tv3.visibility = if(isExpandable) View.VISIBLE else View.VISIBLE
        holder.tv4.visibility = if(isExpandable) View.VISIBLE else View.VISIBLE
        holder.favBtn.visibility = if(isExpandable) View.VISIBLE else View.VISIBLE
        holder.cardView.setOnClickListener {
            WordsList[position].expandable = !WordsList[position].expandable
            staticWord.id = WordsList[position].id
            staticWord.word = WordsList[position].word
            staticWord.type = WordsList[position].type
            staticWord.meaning = WordsList[position].meaning
            staticWord.example_sentence = WordsList[position].example_sentence
            staticWord.history = WordsList[position].history
            staticWord.favorites = WordsList[position].favorites
            staticWord.expandable = WordsList[position].expandable
            positionInList = position
            staticWord.id?.let { it1 -> MainActivity.db.addToHistory(it1) }
            notifyItemChanged(position)
        }
        holder.favBtn.setOnClickListener {
            if (WordsList[position].favorites.equals("0")){
                staticWord.favorites = "1"
                WordsList[position].favorites = "1"
                staticWord.id = WordsList[position].id
                staticWord.id?.let { it1 -> MainActivity.db.addToFavorites(it1) }
                holder.favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite)
            }
            else{
                staticWord.favorites = "0"
                WordsList[position].favorites = "0"
                staticWord.id = WordsList[position].id
                staticWord.id?.let { it1 -> MainActivity.db.removeFromFavorites(it1) }
                holder.favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }
    }

    override fun getItemCount(): Int {
        return WordsList.size
    }

    class RecyclerViewHistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)/*, View.OnCreateContextMenuListener*/{
        val tv1: TextView = itemView.findViewById(R.id.tv_word1)
        val tv2: TextView = itemView.findViewById(R.id.tv_word2)
        val tv3: TextView = itemView.findViewById(R.id.tv_word3)
        val tv4: TextView = itemView.findViewById(R.id.tv_word4)
        val cardView: CardView = itemView.findViewById(R.id.card_view_item)
        val favBtn: Button = itemView.findViewById(R.id.favorites_btn)

/*        init {
            cardView.setOnCreateContextMenuListener(this)
            itemView.setOnLongClickListener {
                listener.onItemLongPressed(absoluteAdapterPosition)
                true
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.add(this.absoluteAdapterPosition, 121, 0, "add to favorites")
        }*/
    }
}