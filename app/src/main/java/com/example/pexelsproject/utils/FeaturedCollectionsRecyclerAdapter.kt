package com.example.pexelsproject.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import com.example.pexelsproject.R
import com.example.pexelsproject.data.model.Collection

class FeaturedCollectionsRecyclerAdapter(
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<FeaturedCollectionsRecyclerAdapter.CollectionsRecyclerViewHolder>() {

    private var listOfFeaturedCollections: List<Collection> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectionsRecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.featured_collections_item_layout, parent, false)
        return CollectionsRecyclerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listOfFeaturedCollections.size
    }

    override fun getItemId(position: Int): Long {
        return listOfFeaturedCollections[position].id.toLong()
    }

    override fun onBindViewHolder(holder: CollectionsRecyclerViewHolder, position: Int) {
        val current = listOfFeaturedCollections[position]
        holder.tvItemText.text = current.title

        holder.itemView.setOnClickListener {
            onItemClick(current.title)
        }
    }

    fun submitList(newItems: List<Collection>){
        val difUtil = FeaturedCollectionsDiffUtil(
            oldList = listOfFeaturedCollections,
            newList = newItems
        )

        val result: DiffResult = DiffUtil.calculateDiff(difUtil)

        listOfFeaturedCollections = newItems

        result.dispatchUpdatesTo(this)
    }

    class FeaturedCollectionsDiffUtil(
        private val oldList: List<Collection>,
        private val newList: List<Collection>
    ): DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }

    class CollectionsRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvItemText: TextView = itemView.findViewById(R.id.tvFeaturedCollectionTitle)
    }

}