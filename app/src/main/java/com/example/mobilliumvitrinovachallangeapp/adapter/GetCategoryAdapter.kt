package com.example.mobilliumvitrinovachallangeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobilliumvitrinovachallangeapp.R
import com.example.mobilliumvitrinovachallangeapp.model.Category

class GetCategoryAdapter(private val category: List<Category>) :
    RecyclerView.Adapter<GetCategoryAdapter.GetCategoryViewHolder>() {

    inner class GetCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        Glide.with(holder.itemView.context)
            .load(currentItem.logo.url)
            .fitCenter()
            .into(holder.image)
        holder.categoryHeader.text = currentItem.name
    }

    override fun getItemCount(): Int {
        return category.size
    }
}