package com.example.mobilliumvitrinovachallangeapp.api.repository

import com.example.mobilliumvitrinovachallangeapp.api.apiservice.ApiService

class ContentRepository(
    private val service: ApiService,

    ) {

    fun getContent() = service.getVitrinovaContent()

}