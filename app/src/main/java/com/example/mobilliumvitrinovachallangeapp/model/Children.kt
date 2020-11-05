package com.example.mobilliumvitrinovachallangeapp.model

data class Children(
    val children: List<Any>,
    val cover: Cover,
    val id: Int,
    val logo: Logo,
    val name: String,
    val order: Int,
    val parent_category: ParentCategory,
    val parent_id: Int
)