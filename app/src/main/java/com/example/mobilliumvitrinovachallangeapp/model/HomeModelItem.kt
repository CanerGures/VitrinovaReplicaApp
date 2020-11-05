package com.example.mobilliumvitrinovachallangeapp.model

data class HomeModelItem(
    val categories: List<Category>,
    val collections: List<Collection>,
    val featured: List<Featured>,
    val products: List<Product>,
    val shops: List<ShopX>,
    val title: String,
    val type: String
)