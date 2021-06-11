package com.example.sampletest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sampletest.data.Photo
import com.example.sampletest.data.User
import com.example.sampletest.network.ApiService

class MockApiService(private val users: List<User>, private val photos: List<Photo>): ApiService {

    override suspend fun getUserInfoCoroutine() = users

    override suspend fun getPhotosCoroutine(id: Int) = photos
}
