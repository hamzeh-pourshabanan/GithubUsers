package com.example.myapplication.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.myapplication.Injection
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentUserSearchBinding
import com.example.myapplication.databinding.LayoutDetailsBinding
import com.example.myapplication.ui.SearchUsersActivity
import com.example.myapplication.ui.SearchUsersViewModel
import kotlinx.coroutines.launch

class DetailsFragment: Fragment() {

    private var _binding: LayoutDetailsBinding? = null
    private val binding: LayoutDetailsBinding
        get() = _binding!!

    private val args: DetailsFragmentArgs? by navArgs()
    private var url: String? = null

    private val viewModel: DetailsViewModel by viewModels{
        Injection.provideViewModelFactory(requireContext(), this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args?.url?.let {
            url = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as SearchUsersActivity).showBackButton(true)
        (activity as SearchUsersActivity).setToolbarTitle(requireActivity().getString(R.string.details_toolbar_title))

        url?.let {
            viewModel.getDetails(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailsState.collect { detailsState ->
                    when(detailsState) {
                        is DetailsStates.DetailsErrorState -> {
                            Toast.makeText(requireContext(), detailsState.error, Toast.LENGTH_SHORT).show()
                        }
                        is DetailsStates.DetailsSuccessState -> {
                            Log.d("avatar", "avatar: ${detailsState.userDetailsResponse.avatar_url}")
                            Glide.with(this@DetailsFragment)
                                .load(detailsState.userDetailsResponse.avatar_url)
                                .into(binding.imageView)

                            binding.name.text = detailsState.userDetailsResponse.name ?: ""
                            binding.login.text = detailsState.userDetailsResponse.login ?: ""
                            binding.followers.text = context?.getString(R.string.followers, detailsState.userDetailsResponse.followers.toString()) ?: ""
                            binding.repos.text = context?.getString(R.string.repos, detailsState.userDetailsResponse.public_repos?.toString()) ?: ""

                            binding.login.setOnClickListener {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(detailsState.userDetailsResponse.html_url))
                                view.context.startActivity(intent)
                            }

                        }
                        DetailsStates.Loading -> {

                        }
                    }
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}