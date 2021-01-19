package com.example.testapplication.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.testapplication.R
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// Created by Victor Hernandez on 1/15/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com


@KoinApiExtension
class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(), KoinComponent {

    private val glide by inject<RequestManager>()

    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    //callback
    private  val diffCallback = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
           return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
          return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    var images : List<String>
    get() = differ.currentList
    set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_image,
                        parent,
                        false
                )
        )
    }

    private var onItemClickListener : ((String)-> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = images[position]
        holder.itemView.apply {
            val ivImage = findViewById<ImageView>(R.id.ivShoppingImage)
            glide
                .load(url)
                .circleCrop()
                .into(ivImage)

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(url)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}