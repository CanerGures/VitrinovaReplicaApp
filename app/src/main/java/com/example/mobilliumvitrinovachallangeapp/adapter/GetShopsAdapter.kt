package com.example.mobilliumvitrinovachallangeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobilliumvitrinovachallangeapp.R
import com.example.mobilliumvitrinovachallangeapp.model.ShopX

class GetShopsAdapter(private val shop: List<ShopX>) :
    RecyclerView.Adapter<GetShopsAdapter.GetShopViewHolder>() {
    inner class GetShopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val imageBackGround: ImageView = itemView.findViewById(R.id.editorsChoiceField)
        val imageCircle: ImageView = itemView.findViewById(R.id.ivImage)
        val ivProduct1: ImageView = itemView.findViewById(R.id.ivProduct1Image)
        val ivProduct2: ImageView = itemView.findViewById(R.id.ivProduct2Image)
        val ivProduct3: ImageView = itemView.findViewById(R.id.ivProduct3Image)
        val tvShopName: TextView = itemView.findViewById(R.id.tvShopName)
        val tvDefinition: TextView = itemView.findViewById(R.id.tvDefinition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetShopViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.shops_content, parent, false)
        return GetShopViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GetShopViewHolder, position: Int) {
        val currentItem = shop[position]
        try {

            holder.tvShopName.text = currentItem.name
            holder.tvDefinition.text = currentItem.definition

            Glide.with(holder.itemView.context)
                .load(currentItem.popular_products[0].images[0].url)
                .fitCenter()
                .into(holder.ivProduct1)
            Glide.with(holder.itemView.context)
                .load(currentItem.popular_products[1].images[0].url)
                .fitCenter()
                .into(holder.ivProduct2)
            Glide.with(holder.itemView.context)
                .load(currentItem.popular_products[2].images[0].url)
                .fitCenter()
                .into(holder.ivProduct3)

            Glide.with(holder.itemView.context)
                .load(currentItem.logo.url)
                .fitCenter()
                .circleCrop()
                .into(holder.imageCircle)
            /*Glide.with(holder.itemView.context)
                .load(currentItem.cover.url)
                .fitCenter()
                .into(holder.imageBackGround)*/
        }catch (e: Exception){
            Glide.with(holder.itemView.context)
                .load(holder.itemView.context.getString(R.string.empty_image_url))
                .fitCenter()
                .into(holder.ivProduct1)
            Glide.with(holder.itemView.context)
                .load(holder.itemView.context.getString(R.string.empty_image_url))
                .fitCenter()
                .into(holder.ivProduct2)
            Glide.with(holder.itemView.context)
                .load(holder.itemView.context.getString(R.string.empty_image_url))
                .fitCenter()
                .into(holder.ivProduct3)

            Glide.with(holder.itemView.context)
                .load(holder.itemView.context.getString(R.string.empty_image_url))
                .fitCenter()
                .circleCrop()
                .into(holder.imageCircle)
        }

    }

    override fun getItemCount(): Int {
        return shop.size
    }
}