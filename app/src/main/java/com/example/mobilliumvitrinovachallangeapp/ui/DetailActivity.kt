package com.example.mobilliumvitrinovachallangeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumvitrinovachallangeapp.R
import com.example.mobilliumvitrinovachallangeapp.adapter.GetCollectionAdapterDetail
import com.example.mobilliumvitrinovachallangeapp.adapter.GetProductAdapterDetail
import com.example.mobilliumvitrinovachallangeapp.adapter.GetShopNewAdapterDetail
import com.example.mobilliumvitrinovachallangeapp.adapter.GetShopsAdapterDetail
import com.example.mobilliumvitrinovachallangeapp.model.Collection
import com.example.mobilliumvitrinovachallangeapp.model.Product
import com.example.mobilliumvitrinovachallangeapp.model.ShopX
import com.example.mobilliumvitrinovachallangeapp.util.MarginItemDecoration
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

private var currentObject: String? = ""
lateinit var rvDetail: RecyclerView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val getShopNewObject: String? = intent.getStringExtra("listShopNew")
        rvDetail = findViewById(R.id.rvDetail)
        if (getShopNewObject != null) {
            currentObject = getShopNewObject
            val gson = Gson()
            val type: Type = object : TypeToken<List<ShopX?>?>() {}.type
            val data: List<ShopX> = gson.fromJson(currentObject, type)
            rvDetail.adapter = GetShopNewAdapterDetail(data)
            rvDetail.layoutManager =
                LinearLayoutManager(this)
            rvDetail.addItemDecoration(
                MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.shop_margin))
            )
        }
        val getShopsObject: String? = intent.getStringExtra("listShops")
        if (getShopsObject != null) {
            currentObject = getShopsObject
            val gson = Gson()
            val type: Type = object : TypeToken<List<ShopX?>?>() {}.type
            val data: List<ShopX> = gson.fromJson(currentObject, type)
            rvDetail.adapter = GetShopsAdapterDetail(data)
            rvDetail.layoutManager =
                LinearLayoutManager(this)
            rvDetail.addItemDecoration(
                MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.shop_margin))
            )
        }
        val getCollectionsObject: String? = intent.getStringExtra("listCollections")
        if (getCollectionsObject != null) {
            currentObject = getCollectionsObject
            val gson = Gson()
            val type: Type = object : TypeToken<List<Collection?>?>() {}.type
            val data: List<Collection> = gson.fromJson(currentObject, type)
            rvDetail.adapter = GetCollectionAdapterDetail(data)
            rvDetail.layoutManager =
                LinearLayoutManager(this)

        }
        val getProductObject: String? = intent.getStringExtra("listProduct")
        if (getProductObject != null) {
            currentObject = getProductObject
            val gson = Gson()
            val type: Type = object : TypeToken<List<Product?>?>() {}.type
            val data: List<Product> = gson.fromJson(currentObject, type)
            rvDetail.adapter = GetProductAdapterDetail(data)
            rvDetail.layoutManager =
                GridLayoutManager(this, 2)

        }

    }

}