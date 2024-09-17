package com.example.pexelsproject.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pexelsproject.R

class SearchBarHistoryRecyclerAdapter : RecyclerView.Adapter<SearchBarHistoryRecyclerAdapter.SearchBarHistoryViewHolder>() {

    private var listOfSearches: List<String> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchBarHistoryRecyclerAdapter.SearchBarHistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.featured_collections_item_layout, parent, false)
        return SearchBarHistoryRecyclerAdapter.SearchBarHistoryViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listOfSearches.size
    }

    override fun onBindViewHolder(holder: SearchBarHistoryRecyclerAdapter.SearchBarHistoryViewHolder, position: Int) {
        val current = listOfSearches[position]
        holder.tvItemText.text = current
    }

    fun submitList(newItems: List<String>){
        val difUtil = SearchBarDiffUtil(
            oldList = listOfSearches,
            newList = newItems
        )

        val result: DiffUtil.DiffResult = DiffUtil.calculateDiff(difUtil)

        listOfSearches = newItems

        result.dispatchUpdatesTo(this)
    }

    class SearchBarDiffUtil(
        private val oldList: List<String>,
        private val newList: List<String>
    ): DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].length == newList[newItemPosition].length
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }

    class SearchBarHistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvItemText: TextView = itemView.findViewById(R.id.tvFeaturedCollectionTitle)
    }
}