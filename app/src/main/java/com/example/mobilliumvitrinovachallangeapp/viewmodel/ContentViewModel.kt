package com.example.mobilliumvitrinovachallangeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobilliumvitrinovachallangeapp.api.repository.ContentRepository
import com.example.mobilliumvitrinovachallangeapp.model.HomeModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ContentViewModel(private val contentRepository: ContentRepository) :
    BaseViewModel<HomeModel>() {

    val fetch = MutableLiveData<HomeModel>()
    val fetchLive: LiveData<HomeModel> = fetch

    init {
        createJobApi()
    }

    private fun createJobApi() {
        dataLoading.value = true

        contentRepository.getContent().enqueue(object : Callback<HomeModel> {
            override fun onResponse(
                call: Call<HomeModel>,
                response: Response<HomeModel>
            ) {
                if (!response.isSuccessful) {
                    empty.value = true
                    return
                }
                fetch.value = response.body()
                empty.value = false
            }

            override fun onFailure(call: Call<HomeModel>?, t: Throwable?) {

            }
        })


    }

}
