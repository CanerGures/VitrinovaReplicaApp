package com.example.mobilliumvitrinovachallangeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobilliumvitrinovachallangeapp.R
import com.example.mobilliumvitrinovachallangeapp.model.Collection

class GetCollectionAdapterDetail(private val content: List<Collection>) :
    RecyclerView.Adapter<GetCollectionAdapterDetail.GetCollectionDetailViewHolder>() {
    inner class GetCollectionDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.collectionsImage)
        val textUp: TextView = itemView.findViewById(R.id.collectionsHeader)
        val textDown: TextView = itemView.findViewById(R.id.collectionSubHeader)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GetCollectionDetailViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.collections_content_detail, parent, false)
        return GetCollectionDetailViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GetCollectionDetailViewHolder, position: Int) {
        val currentItem = content[position]
        try {
            Glide.with(holder.itemView.context)
                .load(currentItem.cover.url)
                .fitCenter()
                .into(holder.image)

            holder.textUp.text = currentItem.title
            holder.textDown.text = currentItem.definition
        } catch (e: Exception) {
            Glide.with(holder.itemView.context)
                .load(holder.itemView.context.getString(R.string.empty_image_url))
                .fitCenter()
                .into(holder.image)
        }


    }

    override fun getItemCount(): Int {
        return content.size
    }
}