package com.example.mobilliumvitrinovachallangeapp.model

data class CategoryX(
    val id: Int,
    val name: String,
    val order: Int,
    val parent_category: ParentCategoryX,
    val parent_id: Int
)