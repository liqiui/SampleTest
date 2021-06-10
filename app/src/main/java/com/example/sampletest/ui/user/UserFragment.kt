package com.example.sampletest.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sampletest.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUserBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        binding.viewModel = userViewModel

        binding.userList.adapter = UserAdapter(UserAdapter.OnClickListener {
            userViewModel.displayAlbum(it)
        })

        userViewModel.selectedUser.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(
                    UserFragmentDirections.actionShowAlbum(it))
                userViewModel.displayAlbumComplete()
            }
        })

        return binding.root
    }
}