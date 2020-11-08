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

class GetShopNewAdapter(private val shopNew: List<ShopX>) :
    RecyclerView.Adapter<GetShopNewAdapter.GetShopNewViewHolder>() {
    inner class GetShopNewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.topImage)
        val shopNewLogoImage: ImageView = itemView.findViewById(R.id.shopNewLogoPhoto)
        val textTitle: TextView = itemView.findViewById(R.id.tvShopNewName)
        val textDefinition: TextView = itemView.findViewById(R.id.tvShopNewDefinition)
        val textProductCount: TextView = itemView.findViewById(R.id.tvShopNewProductCount)
        val shopNewLogoPhotoNull: TextView = itemView.findViewById(R.id.shopNewLogoPhotoNull)
        val shopNewParent: CardView = itemView.findViewById(R.id.shopNewParent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetShopNewViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.shops_new_content, parent, false)
        return GetShopNewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GetShopNewViewHolder, position: Int) {
        val currentItem = shopNew[position]
        holder.shopNewParent.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rv_animations)

        if (currentItem.logo != null){
            Glide.with(holder.itemView.context)
                .load(currentItem.logo.url)
                .fitCenter()
                .circleCrop()
                .into(holder.shopNewLogoImage)
        }
        else{
            val firstLetter = currentItem.name.take(1)
            holder.shopNewLogoImage.visibility= View.GONE
            holder.shopNewLogoPhotoNull.visibility= View.VISIBLE
            holder.shopNewLogoPhotoNull.text = firstLetter
        }
       try {
        Glide.with(holder.itemView.context)
            .load(currentItem.cover.url)
            .fitCenter()
            .into(holder.image)

           holder.textTitle.text = currentItem.name
           holder.textDefinition.text = currentItem.definition
           holder.textProductCount.text = holder.itemView.context.getString(R.string.shop_new_product_count, currentItem.product_count.toString())

       }catch (e:Exception){
           Glide.with(holder.itemView.context)
               .load(holder.itemView.context.getString(R.string.empty_image_url))
               .fitCenter()
               .into(holder.image)
           holder.textTitle.text = currentItem.name
           holder.textDefinition.text = currentItem.definition
           holder.textProductCount.text = holder.itemView.context.getString(R.string.shop_new_product_count, currentItem.product_count.toString())
       }
    }

    override fun getItemCount(): Int {
        return shopNew.size
    }

}