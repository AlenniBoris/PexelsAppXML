package com.example.pexelsproject.screens.bookmarks.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pexelsproject.R
import com.example.pexelsproject.screens.home.HomeScreenState
import com.example.pexelsproject.screens.home.HomeScreenViewModel
import com.example.pexelsproject.utils.PhotosRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BookmarksScreenFragment() : Fragment() {

    private val viewModel: HomeScreenViewModel by viewModels()

    private lateinit var applicationContext: Context
    //Photos
    private lateinit var photosRecyclerView: RecyclerView
    private lateinit var photosAdapter: PhotosRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmarks_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applicationContext = requireActivity().applicationContext

        //Photos
        photosRecyclerView = view.findViewById(R.id.rvPhotosBookmarks)
        photosAdapter = PhotosRecyclerAdapter()
        photosRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        photosRecyclerView.adapter = photosAdapter

        viewModel.screenState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state -> renderState(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun renderState(state: HomeScreenState){
        photosAdapter.submitList(state.photos)
    }
}