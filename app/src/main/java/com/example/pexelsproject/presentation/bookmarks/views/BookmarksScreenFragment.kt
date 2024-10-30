package com.example.pexelsproject.presentation.bookmarks.views

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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pexelsproject.databinding.FragmentBookmarksScreenBinding
import com.example.pexelsproject.di.PexelsApplication
import com.example.pexelsproject.navigation.Screen
import com.example.pexelsproject.presentation.bookmarks.BookmarksScreenState
import com.example.pexelsproject.presentation.bookmarks.BookmarksScreenViewModel
import com.example.pexelsproject.utils.PhotosRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BookmarksScreenFragment() : Fragment() {

    private val viewModel: BookmarksScreenViewModel by viewModels()

    private lateinit var applicationContext: Context
    //Photos
    private lateinit var photosAdapter: PhotosRecyclerAdapter

    private var bookmarksScreenBinding: FragmentBookmarksScreenBinding? = null
    private val binding get() = bookmarksScreenBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookmarksScreenBinding = FragmentBookmarksScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applicationContext = requireActivity().applicationContext

        //Photos
        photosAdapter = PhotosRecyclerAdapter(applicationContext){ id ->
            PexelsApplication.router.navigateTo(
                Screen.DetailsScreen(id, "bookmarks_screen")
            )
        }
        binding.rvPhotosBookmarks.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvPhotosBookmarks.adapter = photosAdapter

        binding.tvExploreButton.setOnClickListener {
            PexelsApplication.router.navigateTo(
                Screen.MainAppScreens("from_bookmarks_screen")
            )
        }

        viewModel.screenState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state -> renderState(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun renderState(state: BookmarksScreenState){
        photosAdapter.submitList(state.favouritePhotos)

        if (state.favouritePhotos.isEmpty()){
            binding.bookmarksNotEmptyLayout.visibility = View.GONE
            binding.tvBookmarksEmptyPageTitle.visibility = View.VISIBLE
            binding.bookmarksEmptyLayout.visibility = View.VISIBLE
        }
        else{
            binding.bookmarksNotEmptyLayout.visibility = View.VISIBLE
            binding.tvBookmarksEmptyPageTitle.visibility = View.GONE
            binding.bookmarksEmptyLayout.visibility = View.GONE
        }
    }
}