package com.example.pexelsproject.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pexelsproject.R
import com.example.pexelsproject.data.model.Photo
import com.example.pexelsproject.di.PexelsApplication
import com.example.pexelsproject.navigation.Screen

class PhotosRecyclerAdapter (

): RecyclerView.Adapter<PhotosRecyclerAdapter.PhotosRecyclerViewHolder>() {

    private var listOfPhotos: List<Photo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosRecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.photo_item_layout,
            parent, false)
        return PhotosRecyclerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listOfPhotos.size
    }

    override fun getItemId(position: Int): Long {
        return listOfPhotos[position].id.toLong()
    }

    override fun onBindViewHolder(holder: PhotosRecyclerViewHolder, position: Int) {
        val current = listOfPhotos[position]
        holder.tvItemText.text = current.photographer
        Glide.with(holder.itemView.context)
            .load(current.src.medium)
            .error(R.drawable.ic_placeholder_light)
            .placeholder(R.drawable.ic_placeholder_light)
            .into(holder.ivItemImage)

        //Item click
        holder.itemView.setOnClickListener {view ->
            PexelsApplication.router.navigateTo(Screen.DetailsScreen(current.id))
        }

    }

    fun submitList(newItems: List<Photo>) {
        val diffUtil = PhotoDiffUtil(
            oldList = listOfPhotos,
            newList = newItems
        )

        val result: DiffResult = DiffUtil.calculateDiff(diffUtil)

        listOfPhotos = newItems

        result.dispatchUpdatesTo(this)
    }

    class PhotoDiffUtil(
        private val oldList: List<Photo>,
        private val newList: List<Photo>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }

    class PhotosRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ivItemImage: ImageView = itemView.findViewById(R.id.ivItemImage)
        val tvItemText: TextView = itemView.findViewById(R.id.tvItemTitle)
    }

}