package com.example.pexelsproject.presentation.uikit.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import com.example.pexelsproject.R
import com.example.pexelsproject.domain.model.Collection

class FeaturedCollectionsRecyclerAdapter(
    private val onItemClick: (String, String) -> Unit,
) : RecyclerView.Adapter<FeaturedCollectionsRecyclerAdapter.CollectionsRecyclerViewHolder>() {

    private var listOfFeaturedCollections: MutableList<Collection> = mutableListOf()
    private var queryText: String = ""
    private var selectedId: String = ""
    private var lastSelectedId: String = ""

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

        Log.d("binding view holder","binded = ${current.title}")
        Log.d("selected id", "query = $queryText , selected id = $selectedId , current id = ${current.id}")

        if ((current.id == selectedId) || (current.title == queryText)){
            holder.tvItemText.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.selected_color)
            )
            holder.tvItemText.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.on_selected_color)
            )
            lastSelectedId = selectedId
        }else{
            holder.tvItemText.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.main_color)
            )
            holder.tvItemText.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.on_main_color)
            )
        }

        holder.itemView.setOnClickListener {
            moveItemToTop(position)
            selectedId = current.id
            onItemClick(current.id, current.title)
            notifyDataSetChanged()
        }

    }

    private fun moveItemToTop(position: Int) {
        if (position == 0) return
        val selectedItem = listOfFeaturedCollections.removeAt(position)
        listOfFeaturedCollections.add(0, selectedItem)
        notifyItemMoved(position, 0)
    }


    fun submitList(newItems: MutableList<Collection>){
        val difUtil = FeaturedCollectionsDiffUtil(
            oldList = listOfFeaturedCollections,
            newList = newItems
        )

        val result: DiffResult = DiffUtil.calculateDiff(difUtil)

        listOfFeaturedCollections = newItems

        result.dispatchUpdatesTo(this)
    }

    fun submitQueryAndSelectedId(query: String, selectedFeaturedCollectionId: String){
        queryText = query
        selectedId = selectedFeaturedCollectionId


        val index = listOfFeaturedCollections.indexOfFirst { it.title == queryText }
        if (index != -1) {
            moveItemToTop(index)
        }

        notifyDataSetChanged()
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