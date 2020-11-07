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
import com.example.mobilliumvitrinovachallangeapp.model.Category
import java.lang.Exception


class GetCategoryAdapter(private val category: List<Category>) :
    RecyclerView.Adapter<GetCategoryAdapter.GetCategoryViewHolder>() {
    private var onAttach = true
    private var duration = 400L

    inner class GetCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val parentCard: CardView = itemView.findViewById(R.id.parentCard)
        val image: ImageView = itemView.findViewById(R.id.categoriesImage)
        val categoryHeader: TextView = itemView.findViewById(R.id.categoriesItemHeader)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetCategoryViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.categories_content, parent, false)
        return GetCategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GetCategoryViewHolder, position: Int) {

        val currentItem = category[position]
        holder.parentCard.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rv_animations)
        try {
        Glide.with(holder.itemView.context)
            .load(currentItem.logo.url)
            .fitCenter()
            .into(holder.image)

        holder.categoryHeader.text = currentItem.name
        }catch (e: Exception){
            Glide.with(holder.itemView.context)
                .load(holder.itemView.context.getString(R.string.empty_image_url))
                .fitCenter()
                .into(holder.image)
        }

    }

    override fun getItemCount(): Int {
        return category.size
    }


}