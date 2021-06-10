package com.example.sampletest.ui.album

import android.app.Application
import androidx.lifecycle.*
import com.example.sampletest.data.Photo
import com.example.sampletest.data.User
import com.example.sampletest.data.sampleAlbumData
import com.example.sampletest.network.Api
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch

class AlbumViewModel(val user: User, app: Application): AndroidViewModel(app) {
    // The internal MutableLiveData Album Data that stores the most recent data
    private val _photoData = MutableLiveData<List<Photo>>()

    // The external immutable LiveData for the response User Data
    val photoData: LiveData<List<Photo>>
        get() = _photoData

    init {
//        getPhotoDataFromSample()
        getPhotoDataFromNetworkCoroutine()
    }

    private fun getPhotoDataFromSample() {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val listMyData = Types.newParameterizedType(List::class.java, Photo::class.java)
        val jsonAdapter = moshi.adapter<List<Photo>>(listMyData)
        val sampleData = jsonAdapter.fromJson(sampleAlbumData)?.filter { it.albumId == user.id }
        _photoData.value = sampleData ?: emptyList<Photo>()
    }

    private fun getPhotoDataFromNetworkCoroutine() {
        viewModelScope.launch {
            try {
                _photoData.value = Api.retrofitService.getPhotosCoroutine(id = user.id)
            } catch (e: Exception) {
                _photoData.value = emptyList()
            }
        }
    }

    class Factory(private val user: User,
                  private val app: Application
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                return AlbumViewModel( user, app) as T
            }
            throw IllegalArgumentException("Unknown View class")
        }

    }
}