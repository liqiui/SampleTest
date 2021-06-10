package com.example.sampletest.ui.photo

import android.app.Application
import androidx.lifecycle.*
import com.example.sampletest.data.Photo
import com.example.sampletest.data.User
import com.example.sampletest.data.sampleAlbumData
import com.example.sampletest.network.Api
import com.example.sampletest.ui.album.AlbumViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch

class PhotoViewModel(val photo: Photo, app: Application): AndroidViewModel(app) {

    private val _photoData = MutableLiveData<Photo>()

    // The external immutable LiveData for the response photo Data
    val photoData: LiveData<Photo>
        get() = _photoData

    init {
        _photoData.value = photo
    }

    class Factory(private val photo: Photo,
                  private val app: Application
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
                return PhotoViewModel( photo, app) as T
            }
            throw IllegalArgumentException("Unknown View class")
        }

    }
}
