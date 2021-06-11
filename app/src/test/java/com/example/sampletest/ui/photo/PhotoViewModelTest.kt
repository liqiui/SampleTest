package com.example.sampletest.ui.photo

import android.app.Application
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.example.sampletest.data.Photo
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

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class PhotoViewModelTest: Spek({
    val mockApplication = mockk<Application>() {
        every { applicationContext } returns mockk()
    }
    val photo = Photo(id = 111)
    lateinit var photoViewModel: PhotoViewModel

    beforeGroup {
        photoViewModel = spyk(PhotoViewModel(photo = photo, app = mockApplication))
    }

    afterGroup {
    }

    describe("init") {
        it("should init successfully") {
            photoViewModel.photoData.observeForever {
                Assertions.assertThat(photoViewModel.photoData.value).isEqualTo(photo)
            }
        }
    }
})
