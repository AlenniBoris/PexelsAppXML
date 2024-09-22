package com.example.pexelsproject.screens.details.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pexelsproject.R
import com.example.pexelsproject.data.model.Photo
import com.example.pexelsproject.databinding.FragmentDetailsScreenBinding
import com.example.pexelsproject.databinding.FragmentMainAppBinding
import com.example.pexelsproject.di.PexelsApplication
import com.example.pexelsproject.navigation.Screen
import com.example.pexelsproject.screens.details.DetailsScreenState
import com.example.pexelsproject.screens.details.DetailsScreenViewModel
import com.example.pexelsproject.utils.PhotosRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsScreenFragment(
    private val id: Int,
) : Fragment() {

    private lateinit var applicationContext: Context
    //Photo

    private val viewModel: DetailsScreenViewModel by viewModels()

    private var detailsScreenFragmentBinding: FragmentDetailsScreenBinding? = null
    private val binding get() = detailsScreenFragmentBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPhotoFromPexelsById(id)
    }

    override fun onDestroy() {
        super.onDestroy()
        detailsScreenFragmentBinding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailsScreenFragmentBinding = FragmentDetailsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applicationContext = requireActivity().applicationContext

        viewModel.screenState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state -> renderState(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun renderState(state: DetailsScreenState){
        val currentPhoto = state.currentPhoto

        Glide.with(applicationContext)
            .load(currentPhoto?.src?.large2x)
            .error(R.drawable.ic_placeholder_light)
            .placeholder(R.drawable.ic_placeholder_light)
            .into(binding.ivDetailsImage)

        binding.tvDetailsName.text = currentPhoto?.photographer

        binding.ibDetailsBookmarksDatabase.setOnClickListener {
            viewModel.actionOnAddToBookmarksButton(currentPhoto!!)
            Toast.makeText(applicationContext, "Favourites clicked", Toast.LENGTH_SHORT).show()
        }

        if (state.photoIsFavourite){
            binding.ibDetailsBookmarksDatabase.setImageResource(R.drawable.icon_favourites_active)
        }
        else{
            binding.ibDetailsBookmarksDatabase.setImageResource(R.drawable.icon_favourites_not_active)
        }

        binding.ibDetailsBack.setOnClickListener {
            PexelsApplication.router.exit()
            Toast.makeText(applicationContext, "Exit to home screen", Toast.LENGTH_SHORT).show()
        }

        binding.ibDetailsDownload.setOnClickListener {
            Toast.makeText(applicationContext, "Download clicked", Toast.LENGTH_SHORT).show()
        }

    }
}