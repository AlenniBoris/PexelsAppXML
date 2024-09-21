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
    private lateinit var ivDetailsImage: ImageView
    private lateinit var ibButtonBack: ImageButton
    private lateinit var ibButtonDownload: ImageButton
    private lateinit var ibButtonFavourite: ImageButton
    private lateinit var tvDetailsName: TextView

    private val viewModel: DetailsScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPhotoFromPexelsById(id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applicationContext = requireActivity().applicationContext

        ivDetailsImage = view.findViewById(R.id.ivDetailsImage)

        ibButtonBack = view.findViewById(R.id.ibDetailsBack)
        ibButtonBack.setOnClickListener {
//            view.findViewById<LinearLayout>(R.id.bottomBtnsContainer).visibility = LinearLayout.VISIBLE
            PexelsApplication.router.exit()
            Toast.makeText(applicationContext, "Exit to home screen", Toast.LENGTH_SHORT).show()
        }

        ibButtonDownload = view.findViewById(R.id.ibDetailsDownload)
        ibButtonDownload.setOnClickListener {
            Toast.makeText(applicationContext, "Download clicked", Toast.LENGTH_SHORT).show()
        }

        ibButtonFavourite = view.findViewById(R.id.ibDetailsFavourite)
        ibButtonFavourite.setOnClickListener {
            Toast.makeText(applicationContext, "Favourites clicked", Toast.LENGTH_SHORT).show()
        }

        tvDetailsName = view.findViewById(R.id.tvDetailsName)

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
            .into(ivDetailsImage)

        tvDetailsName.text = currentPhoto?.photographer

    }
}