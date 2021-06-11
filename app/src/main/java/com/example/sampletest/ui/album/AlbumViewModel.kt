package com.example.sampletest.ui.album

import android.app.Application
import androidx.lifecycle.*
import com.example.sampletest.data.Photo
import com.example.sampletest.data.User
import com.example.sampletest.data.sampleAlbumData
import com.example.sampletest.network.Api
import com.example.sampletest.network.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch

class AlbumViewModel(val apiService: ApiService = Api.retrofitService, val user: User, app: Application): AndroidViewModel(app) {
    // The internal MutableLiveData album Data that stores the most recent data
    private val _albumData = MutableLiveData<List<Photo>>()
    private val _selectedPhoto = MutableLiveData<Photo?>()

    // The external immutable LiveData for the response album Data
    val albumData: LiveData<List<Photo>>
        get() = _albumData
    val selectedPhoto: LiveData<Photo?>
        get() = _selectedPhoto

    init {
//        getAlbumDataFromSample()
        getAlbumDataFromNetworkCoroutine()
    }

    fun displayPhoto(photo: Photo) {
        _selectedPhoto.value = photo
    }

    fun displayPhotoComplete() {
        _selectedPhoto.value = null
    }

    private fun getAlbumDataFromSample() {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val listMyData = Types.newParameterizedType(List::class.java, Photo::class.java)
        val jsonAdapter = moshi.adapter<List<Photo>>(listMyData)
        val sampleData = jsonAdapter.fromJson(sampleAlbumData)?.filter { it.albumId == user.id }
        _albumData.value = sampleData ?: emptyList<Photo>()
    }

    private fun getAlbumDataFromNetworkCoroutine() {
        viewModelScope.launch {
            try {
                _albumData.value = Api.retrofitService.getPhotosCoroutine(id = user.id)
            } catch (e: Exception) {
                _albumData.value = emptyList()
            }
        }
    }

    class Factory(private val user: User,
                  private val app: Application
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                return AlbumViewModel( user = user, app = app) as T
            }
            throw IllegalArgumentException("Unknown View class")
        }

    }
}