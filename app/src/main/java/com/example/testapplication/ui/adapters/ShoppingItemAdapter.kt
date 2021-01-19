package com.example.testapplication.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.testapplication.R
import com.example.testapplication.data.local.ShoppingItem
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// Created by Victor Hernandez on 1/15/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com


@KoinApiExtension
class ShoppingItemAdapter : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingItemViewHolder>(), KoinComponent {

    private val glide by inject<RequestManager>()

    class ShoppingItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    //callback
    private  val diffCallback = object : DiffUtil.ItemCallback<ShoppingItem>(){
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
           return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
          return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this,diffCallback)

    var shoppingItems : List<ShoppingItem>
    get() = differ.currentList
    set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        return ShoppingItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_shopping,
                        parent,
                        false
                )
        )
    }

    private var onItemClickListener : ((String)-> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit){
        onItemClickListener = listener
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val shoppingItem = shoppingItems[position]
        holder.itemView.apply {
            val ivImage = findViewById<ImageView>(R.id.ivShoppingImage)
            glide
                .load(shoppingItem.imageUrl)
                .circleCrop()
                .into(ivImage)

            val txtName = findViewById<TextView>(R.id.tvName)
            txtName.text = shoppingItem.name

            val txtAmount = findViewById<TextView>(R.id.tvShoppingItemAmount)
            txtAmount.text = "${shoppingItem.amount}x"

            val txtPrice = findViewById<TextView>(R.id.tvShoppingItemPrice)
            txtPrice.text = "Price $ ${shoppingItem.price}"

        }
    }

    override fun getItemCount(): Int {
        return shoppingItems.size
    }
}