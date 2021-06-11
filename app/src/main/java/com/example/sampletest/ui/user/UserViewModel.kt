package com.example.sampletest.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampletest.data.User
import com.example.sampletest.data.sampleUserData
import com.example.sampletest.network.Api
import com.example.sampletest.network.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch

class UserViewModel(val apiService: ApiService = Api.retrofitService) : ViewModel() {
    // The internal MutableLiveData User Data that stores the most recent data
    private val _userData = MutableLiveData<List<User>>()
    private val _selectedUser = MutableLiveData<User?>()

    // The external immutable LiveData for the response User Data
    val userData: LiveData<List<User>>
        get() = _userData
    val selectedUser: LiveData<User?>
        get() = _selectedUser


    init {
//        getUserDataFromSample()
        getUserDataFromNetworkCoroutine()
    }

    fun displayAlbum(user: User) {
        _selectedUser.value = user
    }

    fun displayAlbumComplete() {
        _selectedUser.value = null
    }

    private fun getUserDataFromSample() {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val listMyData = Types.newParameterizedType(List::class.java, User::class.java)
        val jsonAdapter = moshi.adapter<List<User>>(listMyData)
        val sampleData = jsonAdapter.fromJson(sampleUserData)
        _userData.value = sampleData ?: emptyList<User>()
    }

    private fun getUserDataFromNetworkCoroutine() {
        viewModelScope.launch {
            try {
                _userData.value = apiService.getUserInfoCoroutine()
            } catch (e: Exception) {
                _userData.value = emptyList()
            }
        }
    }
}