package com.example.pexelsproject.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.pexelsproject.R
import com.example.pexelsproject.data.model.TestPhoto
import kotlinx.coroutines.runBlocking

public class PhotosMainRecyclerAdapter(
    private val listOfPhotos: List<TestPhoto>,
    private val context: Context
): RecyclerView.Adapter<PhotosMainRecyclerAdapter.MainRecyclerViewHolder>() {

    class MainRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ivItemImage: ImageView = itemView.findViewById(R.id.ivItemImage)
//        val tvItemText: TextView = itemView.findViewById(R.id.tvItemTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.photo_item_layout,
            parent, false)
        return MainRecyclerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listOfPhotos.size
    }

    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
        val current = listOfPhotos[position]
//        holder.tvItemText.text = current.photographer
//        runBlocking {
            Glide.with(context)
                .load(current.url)
                .error(R.drawable.ic_placeholder_light)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        // Получаем размеры изображения
                        val width = resource.intrinsicWidth
                        val height = resource.intrinsicHeight

                        // Рассчитываем высоту ImageView
                        val imageViewWidth = holder.ivItemImage.width
                        val imageViewHeight = (imageViewWidth.toFloat() / width * height).toInt()

                        // Устанавливаем высоту ImageView
                        holder.ivItemImage.layoutParams.height = imageViewHeight
                        holder.ivItemImage.setImageDrawable(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Очистка ImageView, если изображение не загружено
                    }
                })
//        }

    }


}