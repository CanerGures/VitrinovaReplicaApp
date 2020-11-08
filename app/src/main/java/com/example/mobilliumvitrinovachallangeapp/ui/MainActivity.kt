package com.example.mobilliumvitrinovachallangeapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arlib.floatingsearchview.FloatingSearchView
import com.bumptech.glide.Glide
import com.example.mobilliumvitrinovachallangeapp.R
import com.example.mobilliumvitrinovachallangeapp.adapter.*
import com.example.mobilliumvitrinovachallangeapp.api.apiservice.ApiService
import com.example.mobilliumvitrinovachallangeapp.api.client.WebClient
import com.example.mobilliumvitrinovachallangeapp.api.repository.ContentRepository
import com.example.mobilliumvitrinovachallangeapp.model.*
import com.example.mobilliumvitrinovachallangeapp.model.Collection
import com.example.mobilliumvitrinovachallangeapp.util.MarginItemDecoration
import com.example.mobilliumvitrinovachallangeapp.viewmodel.ContentViewModel
import com.example.mobilliumvitrinovachallangeapp.viewmodel.ViewModelFactory
import com.google.gson.Gson
import java.util.*


lateinit var recycViewContent: RecyclerView
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
lateinit var buttonProducts: TextView
lateinit var buttonCollections: TextView
lateinit var buttonShops: TextView
lateinit var buttonShopsNew: TextView
lateinit var listContent: List<Featured>
lateinit var listProduct: List<Product>
lateinit var listCollections: List<Collection>
lateinit var listShops: List<ShopX>
lateinit var listShopNew: List<ShopX>
lateinit var listCategories: List<Category>
lateinit var indicatorsContainer: LinearLayout
lateinit var editorsChoiceField: ImageView


private val service: ApiService by lazy { WebClient.buildService(ApiService::class.java) }
private var REQUEST_CODE_SPEECH_INPUT = 100

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        this.overridePendingTransition(
            R.anim.detail_page_animation_enter,
            R.anim.detail_page_animation_leave
        )
        val voiceButton: ImageView = findViewById(R.id.voiceSearchImage)
        searchView = findViewById(R.id.floating_search_view)
        swipeToRefresh = findViewById(R.id.itemSwipeToRefresh)
        titlesProducts = findViewById(R.id.productFieldTitle)
        titlesCategories = findViewById(R.id.categoriesFieldTitle)
        titlesCollections = findViewById(R.id.collectionsFieldTitle)
        titlesShops = findViewById(R.id.shopsFieldTitle)
        titlesShopsNew = findViewById(R.id.shopsNewTitle)
        buttonProducts = findViewById(R.id.wholeProductText)
        buttonCollections = findViewById(R.id.wholeCollectionText)
        buttonShops = findViewById(R.id.wholeeditorsChoiceText)
        buttonShopsNew = findViewById(R.id.wholeShopNewText)
        indicatorsContainer = findViewById(R.id.indicatorsContainer)
        editorsChoiceField = findViewById(R.id.editorsChoiceField)



        voiceButton.setOnClickListener {
            speech()
        }

        swipeToRefresh.setOnRefreshListener {
            observeResponse()

        }
        buttonShopsNew.setOnClickListener {
            val currentItem = listShopNew
            val gson = Gson()
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra("listShopNew", gson.toJson(currentItem))
            it.context.startActivity(intent)
        }

        buttonShops.setOnClickListener {
            val currentItem = listShops
            val gson = Gson()
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra("listShops", gson.toJson(currentItem))
            it.context.startActivity(intent)
        }

        buttonCollections.setOnClickListener {
            val currentItem = listCollections
            val gson = Gson()
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra("listCollections", gson.toJson(currentItem))
            it.context.startActivity(intent)
        }

        buttonProducts.setOnClickListener {
            val currentItem = listProduct
            val gson = Gson()
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra("listProduct", gson.toJson(currentItem))
            it.context.startActivity(intent)
        }

        observeResponse()

    }

    private fun observeResponse() {

        swipeToRefresh.isRefreshing = true
        val repo = ContentRepository(service)
        homeViewModel = ViewModelFactory(repo).create(ContentViewModel::class.java)
        homeViewModel?.fetchLive?.observe(this) {
            val snapHelper: SnapHelper = PagerSnapHelper()
            val helper: SnapHelper = LinearSnapHelper()
            listContent = it[0].featured
            listProduct = it[1].products
            listCategories = it[2].categories
            listCollections = it[3].collections
            listShops = it[4].shops
            listShopNew = it[5].shops

            titlesProducts.text = it[1].title
            titlesCategories.text = it[2].title
            titlesCollections.text = it[3].title
            titlesShops.text = it[4].title
            titlesShopsNew.text = it[5].title

            recycViewContent = findViewById(R.id.rvFeatured)
            recycViewContent.adapter = GetContentAdapter(listContent)
            recycViewContent.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recycViewContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager
                    val snapView = snapHelper.findSnapView(layoutManager)
                    val snapPosition = snapView?.let { it1 -> layoutManager?.getPosition(it1) }
                    if (snapPosition != null) {
                        currentIndicator(snapPosition)
                    }
                }
            })
            setUpIndicators()
            currentIndicator(0)


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
            recycViewShops.adapter = GetShopsAdapter(listShops)
            recycViewShops.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recycViewShops.addItemDecoration(
                MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.shop_margin))
            )
            recycViewShops.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager
                    val snapView = snapHelper.findSnapView(layoutManager)
                    val snapPosition = snapView?.let { it1 -> layoutManager?.getPosition(it1) }
                    val backgroundImage = listShops[snapPosition!!].cover.url
                    Glide.with(this@MainActivity.applicationContext)
                        .load(backgroundImage)
                        .fitCenter()
                        .into(editorsChoiceField)
                }
            })

            recycViewShopNew = findViewById(R.id.rvShopNew)
            recycViewShopNew.adapter = GetShopNewAdapter(listShopNew)
            recycViewShopNew.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recycViewShopNew.addItemDecoration(
                MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.shop_margin))
            )



            if (recycViewShops.onFlingListener == null)
                helper.attachToRecyclerView(recycViewContent)
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

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun setUpIndicators() {
        val indicators = arrayOfNulls<ImageView>(listContent.size)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams

            }
            indicatorsContainer.addView(indicators[i])
        }
    }

    private fun currentIndicator(index: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_inactive
                    )
                )
            }
        }
    }


}