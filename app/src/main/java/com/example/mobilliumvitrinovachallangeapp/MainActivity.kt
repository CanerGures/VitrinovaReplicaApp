package com.example.mobilliumvitrinovachallangeapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arlib.floatingsearchview.FloatingSearchView
import com.example.mobilliumvitrinovachallangeapp.adapter.*
import com.example.mobilliumvitrinovachallangeapp.api.apiservice.ApiService
import com.example.mobilliumvitrinovachallangeapp.api.client.WebClient
import com.example.mobilliumvitrinovachallangeapp.api.repository.ContentRepository
import com.example.mobilliumvitrinovachallangeapp.model.*
import com.example.mobilliumvitrinovachallangeapp.model.Collection
import com.example.mobilliumvitrinovachallangeapp.util.MarginItemDecoration
import com.example.mobilliumvitrinovachallangeapp.viewmodel.ContentViewModel
import com.example.mobilliumvitrinovachallangeapp.viewmodel.ViewModelFactory
import java.util.*


lateinit var recycView: RecyclerView
lateinit var recycViewProducts: RecyclerView
lateinit var recycViewCategories: RecyclerView
lateinit var recycViewCollections: RecyclerView
lateinit var recycViewShops: RecyclerView
lateinit var recycViewShopNew: RecyclerView
private var homeViewModel: ContentViewModel? = null
lateinit var searchView: FloatingSearchView
lateinit var swipeToRefresh: SwipeRefreshLayout
lateinit var titlesProducts: TextView
lateinit var titlesCategories: TextView
lateinit var titlesCollections: TextView
lateinit var titlesShops: TextView
lateinit var titlesShopsNew: TextView
private val service: ApiService by lazy { WebClient.buildService(ApiService::class.java) }
private var REQUEST_CODE_SPEECH_INPUT = 100

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val voiceButton: ImageView = findViewById(R.id.voiceSearchImage)
        searchView = findViewById(R.id.floating_search_view)
        swipeToRefresh = findViewById(R.id.itemSwipeToRefresh)
        titlesProducts = findViewById(R.id.productFieldTitle)
        titlesCategories = findViewById(R.id.categoriesFieldTitle)
        titlesCollections = findViewById(R.id.collectionsFieldTitle)
        titlesShops = findViewById(R.id.shopsFieldTitle)
        titlesShopsNew = findViewById(R.id.shopsNewTitle)

        voiceButton.setOnClickListener {
            speech()
        }

        swipeToRefresh.setOnRefreshListener {
            observeResponse()

        }
        observeResponse()

    }

    private fun observeResponse() {

        swipeToRefresh.isRefreshing = true
        val repo = ContentRepository(service)
        homeViewModel = ViewModelFactory(repo).create(ContentViewModel::class.java)
        homeViewModel?.fetchLive?.observe(this) {
            val snapHelper: SnapHelper = PagerSnapHelper()
            val listContent: List<Featured> = it[0].featured
            val listProduct: List<Product> = it[1].products
            val listCategories: List<Category> = it[2].categories
            val listCollections: List<Collection> = it[3].collections
            val listShops: List<ShopX> = it[4].shops
            val listShopNew : List<ShopX> = it[5].shops

            titlesProducts.text = it[1].title
            titlesCategories.text = it[2].title
            titlesCollections.text = it[3].title
            titlesShops.text = it[4].title
            titlesShopsNew.text = it[5].title

            recycView = findViewById(R.id.rvFeatured)
            recycView.adapter = GetContentAdapter(listContent)
            recycView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            recycViewProducts = findViewById(R.id.rvProduct)
            recycViewProducts.adapter = GetProductAdapter(listProduct)
            recycViewProducts.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recycViewProducts.addItemDecoration(
                MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.product_margin))
            )

            recycViewCategories = findViewById(R.id.rvCategories)
            recycViewCategories.adapter = GetCategoryAdapter(listCategories)
            recycViewCategories.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recycViewCategories.addItemDecoration(
                MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.product_margin))
            )


            recycViewCollections = findViewById(R.id.rvCollections)
            recycViewCollections.adapter = GetCollectionAdapter(listCollections)
            recycViewCollections.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recycViewCollections.addItemDecoration(
                MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.product_margin))
            )
            recycViewShops = findViewById(R.id.rvShops)
            recycViewShops.adapter = GetEditorsChoiceAdapter(listShops)
            recycViewShops.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recycViewShops.addItemDecoration(
                MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.shop_margin))
            )

            recycViewShopNew = findViewById(R.id.rvShopNew)
            recycViewShopNew.adapter = GetShopNewAdapter(listShopNew)
            recycViewShopNew.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recycViewShopNew.addItemDecoration(
                MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.shop_margin))
            )



            if (recycViewShops.onFlingListener == null)
                snapHelper.attachToRecyclerView(recycViewShops)

            if (swipeToRefresh.isRefreshing) {
                swipeToRefresh.isRefreshing = false
            }
        }

    }

    private fun speech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi, please speak")

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (e: Exception) {
            Toast.makeText(this, "Error:${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    searchView.setSearchText(result!![0])
                }
            }
        }
    }


}