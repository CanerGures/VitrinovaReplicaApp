package com.example.mobilliumvitrinovachallangeapp.adapter

import android.graphics.Paint
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
import com.example.mobilliumvitrinovachallangeapp.model.Product
import java.lang.Exception

class GetProductAdapter(private val product: List<Product>) :
    RecyclerView.Adapter<GetProductAdapter.GetProductViewHolder>() {
    inner class GetProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parentCard: CardView = itemView.findViewById(R.id.parentProductCard)
        val image: ImageView = itemView.findViewById(R.id.productImage)
        val textHeader: TextView = itemView.findViewById(R.id.productHeader)
        val textSubHeader: TextView = itemView.findViewById(R.id.productSubHeader)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val itemPriceDiscount: TextView = itemView.findViewById(R.id.itemPriceDiscount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetProductViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.products_content, parent, false)

        return GetProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GetProductViewHolder, position: Int) {
        val currentItem = product[position]
        holder.parentCard.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rv_animations)
        try {
            Glide.with(holder.itemView.context)
                .load(currentItem.images[0].url)
                .fitCenter()
                .into(holder.image)

            holder.textHeader.text = currentItem.title
            holder.textSubHeader.text = currentItem.shop.name
            holder.itemPrice.text = holder.itemView.context.getString(R.string.get_product, currentItem.old_price.toString())

            if (currentItem.old_price != 0) {
                holder.itemPriceDiscount.text = holder.itemView.context.getString(R.string.get_product, currentItem.old_price.toString())
                holder.itemPriceDiscount.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.itemPriceDiscount.visibility = View.GONE
            }

        }catch (e: Exception){
            Glide.with(holder.itemView.context)
                .load(holder.itemView.context.getString(R.string.empty_image_url))
                .fitCenter()
                .into(holder.image)
        }



    }

    override fun getItemCount(): Int {
        return product.size
    }
}