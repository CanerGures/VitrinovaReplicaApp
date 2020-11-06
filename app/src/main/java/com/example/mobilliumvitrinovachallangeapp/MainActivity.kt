package com.example.mobilliumvitrinovachallangeapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arlib.floatingsearchview.FloatingSearchView
import com.example.mobilliumvitrinovachallangeapp.adapter.GetContentAdapter
import com.example.mobilliumvitrinovachallangeapp.adapter.GetProductAdapter
import com.example.mobilliumvitrinovachallangeapp.api.apiservice.ApiService
import com.example.mobilliumvitrinovachallangeapp.api.client.WebClient
import com.example.mobilliumvitrinovachallangeapp.api.repository.ContentRepository
import com.example.mobilliumvitrinovachallangeapp.model.Featured
import com.example.mobilliumvitrinovachallangeapp.model.Product
import com.example.mobilliumvitrinovachallangeapp.util.MarginItemDecoration
import com.example.mobilliumvitrinovachallangeapp.viewmodel.ContentViewModel
import com.example.mobilliumvitrinovachallangeapp.viewmodel.ViewModelFactory
import java.util.*

lateinit var recycView: RecyclerView
lateinit var recycViewProducts: RecyclerView
private var homeViewModel: ContentViewModel? = null
lateinit var searchView: FloatingSearchView
lateinit var swipeToRefresh: SwipeRefreshLayout
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


        voiceButton.setOnClickListener {
            speech()
        }

        swipeToRefresh.setOnRefreshListener {
            observeResponse()

        }

        observeResponse()

    }

    private fun observeResponse() {

        val repo = ContentRepository(service)
        homeViewModel = ViewModelFactory(repo).create(ContentViewModel::class.java)
        homeViewModel?.fetchLive?.observe(this) {
            val listContent: List<Featured> = it[0].featured
            val listProduct: List<Product> = it[1].products

            recycView = findViewById(R.id.recyclerView)
            recycView.adapter = GetContentAdapter(listContent)
            recycView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            recycViewProducts = findViewById(R.id.recyclerViewProduct)
            recycViewProducts.adapter = GetProductAdapter(listProduct)
            recycViewProducts.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recycViewProducts.addItemDecoration(
                MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.product_margin))
            )



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