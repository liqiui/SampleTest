package com.example.sampletest.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampletest.data.User
import com.example.sampletest.data.sampleData
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class UserViewModel : ViewModel() {

    // The internal MutableLiveData User Data that stores the most recent data
    private val _userData = MutableLiveData<List<User>>()

    // The external immutable LiveData for the response User Data
    val userData: LiveData<List<User>>
        get() = _userData

    init {
        getDataFromSample()
    }

    private fun getDataFromSample() {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val listMyData = Types.newParameterizedType(List::class.java, User::class.java)
        val jsonAdapter = moshi.adapter<List<User>>(listMyData)
        val sampleData = jsonAdapter.fromJson(sampleData)
        _userData.value = sampleData ?: emptyList<User>()
    }
}