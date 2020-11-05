package com.example.mobilliumvitrinovachallangeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobilliumvitrinovachallangeapp.R
import com.example.mobilliumvitrinovachallangeapp.model.Featured

class GetContentAdapter(private val content: List<Featured>) :
    RecyclerView.Adapter<GetContentAdapter.GetContentViewHolder>() {
    inner class GetContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.contentImage)
        val textUp: TextView = itemView.findViewById(R.id.textUp)
        val textDown: TextView = itemView.findViewById(R.id.textDown)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetContentViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.featured_content, parent, false)
        return GetContentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GetContentViewHolder, position: Int) {
        val currentItem = content[position]
        Glide.with(holder.itemView.context)
            .load(currentItem.cover.url)
            .fitCenter()
            .into(holder.image)

        holder.textUp.text = currentItem.title
        holder.textDown.text = currentItem.sub_title

    }

    override fun getItemCount(): Int {
        return content.size
    }
}