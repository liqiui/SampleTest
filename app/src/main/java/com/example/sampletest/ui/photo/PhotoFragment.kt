package com.example.sampletest.ui.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sampletest.R
import com.example.sampletest.databinding.FragmentAlbumBinding
import com.example.sampletest.databinding.FragmentPhotoBinding
import com.example.sampletest.ui.album.AlbumAdapter
import com.example.sampletest.ui.album.AlbumFragmentArgs
import com.example.sampletest.ui.album.AlbumViewModel

class PhotoFragment : Fragment() {

    private val photoViewModel: PhotoViewModel by lazy {
        ViewModelProvider(this).get(PhotoViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentPhotoBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        val data = PhotoFragmentArgs.fromBundle(requireArguments()).photo
        val viewModelFactory = PhotoViewModel.Factory( data, application)
        binding.viewModel = ViewModelProvider(
                this, viewModelFactory).get(PhotoViewModel::class.java)

        return binding.root
    }
}