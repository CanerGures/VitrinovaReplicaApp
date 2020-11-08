package com.example.mobilliumvitrinovachallangeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobilliumvitrinovachallangeapp.R
import com.example.mobilliumvitrinovachallangeapp.model.ShopX

class GetShopsAdapterDetail(private val shop: List<ShopX>) :
    RecyclerView.Adapter<GetShopsAdapterDetail.GetShopDetailViewHolder>() {
    inner class GetShopDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val topImage: ImageView = itemView.findViewById(R.id.topImageDetail)
        val title: TextView = itemView.findViewById(R.id.tvShopDetailName)
        val definition: TextView = itemView.findViewById(R.id.tvShopDetailDefinition)
        val productCount: TextView = itemView.findViewById(R.id.tvShopDetailProductCount)
        val logoPhotoNull: TextView = itemView.findViewById(R.id.shopDetailLogoPhotoNull)
        val logoPhoto: ImageView = itemView.findViewById(R.id.shopDetailLogoPhoto)
        val shopDetailParent: CardView = itemView.findViewById(R.id.shopDetailParent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetShopDetailViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.shops_content_detail, parent, false)
        return GetShopDetailViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GetShopDetailViewHolder, position: Int) {
        val currentItem = shop[position]
        holder.shopDetailParent.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rv_linear_animations)
        try {
            holder.title.text = currentItem.name
            holder.definition.text = currentItem.definition
            holder.productCount.text = holder.itemView.context.getString(
                R.string.detail_product_count,
                currentItem.product_count.toString()
            )

            Glide.with(holder.itemView.context)
                .load(currentItem.cover.url)
                .fitCenter()
                .into(holder.topImage)
            if (currentItem.logo != null) {
                Glide.with(holder.itemView.context)
                    .load(currentItem.logo.url)
                    .fitCenter()
                    .circleCrop()
                    .into(holder.logoPhoto)
            } else {
                val firstLetter = currentItem.name.take(1)
                holder.logoPhoto.visibility = View.GONE
                holder.logoPhotoNull.visibility = View.VISIBLE
                holder.logoPhotoNull.text = firstLetter
            }

        } catch (e: Exception) {
            if (currentItem.logo != null) {
                Glide.with(holder.itemView.context)
                    .load(currentItem.logo.url)
                    .fitCenter()
                    .circleCrop()
                    .into(holder.logoPhoto)
            } else {
                val firstLetter = currentItem.name.take(1)
                holder.logoPhoto.visibility = View.GONE
                holder.logoPhotoNull.visibility = View.VISIBLE
                holder.logoPhotoNull.text = firstLetter


            }
            holder.title.text = currentItem.name
            holder.definition.text = currentItem.definition
            holder.productCount.text = currentItem.product_count.toString()
        }

    }

    override fun getItemCount(): Int {
        return shop.size
    }
}