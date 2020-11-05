package com.example.mobilliumvitrinovachallangeapp.model

data class Shop(
    val comment_count: Int,
    val cover: CoverXXXX,
    val created_at: String,
    val definition: String,
    val follower_count: Int,
    val id: Int,
    val is_editor_choice: Boolean,
    val is_following: Boolean,
    val logo: LogoXXX,
    val name: String,
    val name_updateable: Boolean,
    val product_count: Int,
    val share_url: String,
    val shop_payment_id: Int,
    val shop_rate: Int,
    val slug: String,
    val vacation_mode: Int
)