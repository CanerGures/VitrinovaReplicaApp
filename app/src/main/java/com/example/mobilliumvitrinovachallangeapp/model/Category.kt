package com.example.mobilliumvitrinovachallangeapp.model

data class Category(
    val children: List<Children>,
    val cover: CoverX,
    val id: Int,
    val logo: LogoX,
    val name: String,
    val order: Int,
    val parent_category: Any,
    val parent_id: Any
)