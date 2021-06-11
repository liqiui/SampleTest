package com.example.sampletest.ui.user

import android.app.Application
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.example.sampletest.MockApiService
import com.example.sampletest.data.Photo
import com.example.sampletest.data.User
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
class UserViewModelTest: Spek({
    val users = listOf(User(id = 1), User(id = 2), User(id = 3))
    val photos = listOf(Photo(id = 111), Photo(id = 222), Photo(id = 333))
    val mockApiService = MockApiService(users, photos)
    lateinit var userViewModel: UserViewModel
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
        userViewModel = spyk(UserViewModel(apiService = mockApiService))
    }

    afterGroup {
        ArchTaskExecutor.getInstance().setDelegate(null)
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    describe("init") {
        it("should init successfully") {
            userViewModel.userData.observeForever {
                Assertions.assertThat(userViewModel.userData.value).isEqualTo(users)
            }
        }
    }

    describe("#${UserViewModel::displayAlbum.name}") {
        it("should return") {
            val user = User(id = 123)
            userViewModel.displayAlbum(user)
            Assertions.assertThat(userViewModel.selectedUser.value).isEqualTo(user)
        }
    }

    describe("#${UserViewModel::displayAlbumComplete.name}") {
        it("should return null") {
            userViewModel.displayAlbumComplete()
            Assertions.assertThat(userViewModel.selectedUser.value).isNull()
        }
    }
})
