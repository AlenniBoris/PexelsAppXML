package com.example.pexelsproject.presentation.details.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.pexelsproject.R
import com.example.pexelsproject.databinding.FragmentDetailsScreenBinding
import com.example.pexelsproject.di.PexelsApplication
import com.example.pexelsproject.navigation.Screen
import com.example.pexelsproject.presentation.details.DetailsScreenState
import com.example.pexelsproject.presentation.details.DetailsScreenViewModel
import com.example.pexelsproject.utils.ExtraFunctions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsScreenFragment : Fragment() {

    private lateinit var applicationContext: Context
    //Photo

    private val viewModel: DetailsScreenViewModel by viewModels()

    private var detailsScreenFragmentBinding: FragmentDetailsScreenBinding? = null
    private val binding get() = detailsScreenFragmentBinding!!

    private var _prevDestination: String? = null
    private val prevDestination get() = _prevDestination!!

    private var _pictureId: Int? = null
    private val pictureId get() = _pictureId!!

    override fun onDestroy() {
        super.onDestroy()
        detailsScreenFragmentBinding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailsScreenFragmentBinding =
            FragmentDetailsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applicationContext = requireActivity().applicationContext

        _pictureId = arguments?.getInt("key_pic_id")
        _prevDestination = arguments?.getString("key_prev_dest")


        viewModel.loadImageAction(prevDestination, pictureId)


        viewModel.screenState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state -> renderState(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun renderState(state: DetailsScreenState) {
        val currentPhoto = state.currentPhoto

        Glide.with(applicationContext)
            .load(currentPhoto?.src?.large2x)
            .error(R.drawable.ic_placeholder)
            .placeholder(R.drawable.ic_placeholder)
            .into(binding.ivDetailsImage)

        binding.tvDetailsName.text = currentPhoto?.photographer

        binding.ibDetailsBookmarksDatabase.setOnClickListener {
            viewModel.actionOnAddToBookmarksButton(currentPhoto!!)
            Toast.makeText(applicationContext, "Favourites clicked", Toast.LENGTH_SHORT).show()
        }

        binding.ibLikedImageButton.setOnClickListener {
            viewModel.actionOnAddToLikedButton(currentPhoto!!)
            Toast.makeText(applicationContext, "Liked clicked", Toast.LENGTH_SHORT).show()
        }

        if (state.photoIsFavourite) {
            binding.ibDetailsBookmarksDatabase.setImageResource(R.drawable.icon_favourites_active)
        } else {
            binding.ibDetailsBookmarksDatabase.setImageResource(R.drawable.icon_favourites_not_active)
        }

        if (state.photoIsLiked) {
            binding.ibLikedImageButton.setImageResource(R.drawable.heart_active)
        } else {
            binding.ibLikedImageButton.setImageResource(R.drawable.heart_not_active)
        }

        binding.ibDetailsBack.setOnClickListener {
            PexelsApplication.router.navigateTo(
                Screen.MainAppScreens(prevDestination)
            )
        }

        binding.ibDetailsDownload.setOnClickListener {
            Toast.makeText(applicationContext, "Download clicked", Toast.LENGTH_SHORT).show()
            viewModel.downloadImagesToGallery(applicationContext)
        }

        binding.ibDetailsNoImageFoundBack.setOnClickListener {
            PexelsApplication.router.navigateTo(
                Screen.MainAppScreens(prevDestination)
            )
        }

        binding.tvExploreAgainButton.setOnClickListener {
            PexelsApplication.router.navigateTo(
                Screen.MainAppScreens("home_screen")
            )
        }


        if (ExtraFunctions.checkHasInternetConnection(applicationContext)) {

            if (state.someErrorOccurred) {
                binding.svDetailsNotErrorScreen.visibility = View.GONE
                binding.flDetailsImageNotFoundErrorLayout.visibility = View.VISIBLE
            } else {
                binding.svDetailsNotErrorScreen.visibility = View.VISIBLE
                binding.flDetailsImageNotFoundErrorLayout.visibility = View.GONE
            }

        } else {
            binding.svDetailsNotErrorScreen.visibility = View.GONE
            binding.flDetailsImageNotFoundErrorLayout.visibility = View.VISIBLE
        }

    }
}