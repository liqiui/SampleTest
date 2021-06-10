package com.example.sampletest.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sampletest.R
import com.example.sampletest.data.User
import com.example.sampletest.databinding.FragmentAlbumBinding
import com.example.sampletest.databinding.FragmentUserBinding
import com.example.sampletest.ui.user.UserAdapter
import com.example.sampletest.ui.user.UserViewModel

class AlbumFragment : Fragment() {

    private val albumViewModel: AlbumViewModel by lazy {
        ViewModelProvider(this).get(AlbumViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentAlbumBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        val data = User(id = 1)
        val viewModelFactory = AlbumViewModel.Factory( data, application)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(AlbumViewModel::class.java)

        binding.photoList.adapter = AlbumAdapter()
        return binding.root
    }
}