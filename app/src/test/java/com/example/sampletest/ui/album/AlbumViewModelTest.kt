package com.example.sampletest.ui.album

import android.app.Application
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.example.sampletest.MockApiService
import com.example.sampletest.data.Photo
import com.example.sampletest.data.User
import com.example.sampletest.ui.user.UserViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class AlbumViewModelTest: Spek({
    val mockApplication = mockk<Application>() {
        every { applicationContext } returns mockk()
    }
    val users = listOf(User(id = 1), User(id = 2), User(id = 3))
    val photos = listOf(Photo(id = 111), Photo(id = 222), Photo(id = 333))
    val mockApiService = MockApiService(users, photos)
    lateinit var albumViewModel: AlbumViewModel
    val mainThreadSurrogate = newSingleThreadContext("UI thread")

    beforeGroup {
        Dispatchers.setMain(mainThreadSurrogate)
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }
        })
        albumViewModel = spyk(AlbumViewModel(apiService = mockApiService, user = users[0], app = mockApplication))
    }

    afterGroup {
        ArchTaskExecutor.getInstance().setDelegate(null)
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    describe("init") {
        it("should init successfully") {
            albumViewModel.albumData.observeForever {
                Assertions.assertThat(albumViewModel.albumData.value).isEqualTo(photos)
            }
        }
    }

    describe("#${AlbumViewModel::displayPhoto.name}") {
        it("should return") {
            val photo = Photo(id = 123)
            albumViewModel.displayPhoto(photo)
            Assertions.assertThat(albumViewModel.selectedPhoto.value).isEqualTo(photo)
        }
    }

    describe("#${AlbumViewModel::displayPhotoComplete.name}") {
        it("should return null") {
            albumViewModel.displayPhotoComplete()
            Assertions.assertThat(albumViewModel.selectedPhoto.value).isNull()
        }
    }
})
