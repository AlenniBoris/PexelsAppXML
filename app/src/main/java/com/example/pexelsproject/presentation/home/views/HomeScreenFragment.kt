package com.example.pexelsproject.presentation.home.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pexelsproject.databinding.FragmentHomeScreenBinding
import com.example.pexelsproject.di.PexelsApplication
import com.example.pexelsproject.navigation.Screen
import com.example.pexelsproject.presentation.home.HomeScreenState
import com.example.pexelsproject.presentation.home.HomeScreenViewModel
import com.example.pexelsproject.utils.ExtraFunctions
import com.example.pexelsproject.utils.FeaturedCollectionsRecyclerAdapter
import com.example.pexelsproject.utils.PhotosRecyclerAdapter
import com.example.pexelsproject.utils.SearchBarHistoryRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeScreenFragment() : Fragment() {

    private val viewModel: HomeScreenViewModel by activityViewModels()

    private lateinit var applicationContext: Context
    //Photos
    private lateinit var photosAdapter: PhotosRecyclerAdapter
    //Featured collections
    private lateinit var featuredCollectionsAdapter: FeaturedCollectionsRecyclerAdapter
    //History
    private lateinit var searchHistoryAdapter: SearchBarHistoryRecyclerAdapter

    private var homeScreenBinding: FragmentHomeScreenBinding? = null
    private val binding get() = homeScreenBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeScreenBinding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        homeScreenBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applicationContext = requireActivity().applicationContext

        //Collections

        viewModel.scrollEvent
            .onEach {
                binding.rvFeaturedCollections.scrollToPosition(0)
            }
            .launchIn(lifecycleScope)

        featuredCollectionsAdapter = FeaturedCollectionsRecyclerAdapter{id, query ->
            ExtraFunctions.changeSearch(
                scope = lifecycleScope,
                mainScreenViewModel = viewModel,
                title = query,
                id = id
            )
            binding.searchView.hide()
        }
        binding.rvFeaturedCollections.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        binding.rvFeaturedCollections.adapter = featuredCollectionsAdapter

        //Photos
        photosAdapter = PhotosRecyclerAdapter(){ id ->
            PexelsApplication.router.navigateTo(
                Screen.DetailsScreen(id, "home_screen")
            )
        }
        binding.rvPhotosMain.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvPhotosMain.adapter = photosAdapter

        //History
        searchHistoryAdapter = SearchBarHistoryRecyclerAdapter{query ->
            viewModel.queryTextChanged(query)
            viewModel.forceSearchPhoto(query)
            binding.searchView.hide()
        }
        binding.rvHistory.layoutManager = GridLayoutManager(applicationContext, 2)
        binding.rvHistory.adapter = searchHistoryAdapter

        binding.searchBar.setOnClickListener {
            binding.searchView.show()
        }



        viewModel.screenState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state -> renderState(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun renderState(state: HomeScreenState) {

        featuredCollectionsAdapter.submitQueryAndSelectedId(state.queryText, state.selectedFeaturedCollectionId)
        featuredCollectionsAdapter.submitList(state.featuredCollections)

        photosAdapter.submitList(state.photos)
        searchHistoryAdapter.submitList(state.history.toList())

        binding.tvCheckInternetConnectionAgain.setOnClickListener {
            viewModel.internetRetryEventHandler(state.queryText)
            Log.d("interet", state.queryText)
        }

        binding.tvGetQueryResultsAgain.setOnClickListener {
            viewModel.queryTextChanged("")
            viewModel.forceSearchPhoto("")
            Log.d("explore", "tapped")
        }

        val isActive = state.isActive

        binding.searchBar.setText(state.queryText)

        binding.searchView.editText.setOnEditorActionListener { v, _, _ ->
            val enteredQuery = v?.text.toString()
            viewModel.queryTextChanged(enteredQuery)
            viewModel.forceSearchPhoto(enteredQuery)
            binding.searchView.hide()
            true
        }

        binding.fab.setOnClickListener{
            PexelsApplication.router.navigateTo(Screen.LikedScreen())
        }

        if (ExtraFunctions.checkHasInternetConnection(applicationContext)){

            if (state.photos.isEmpty()){
                binding.nsvPhotos.visibility = View.GONE
                binding.nsvNoInternetConnectionResultLayout.visibility = View.GONE
                binding.progressBar.visibility = View.GONE

                binding.rvFeaturedCollections.visibility = View.VISIBLE
                binding.nsvNoResultsFoundLayout.visibility = View.VISIBLE
            }else{
                binding.nsvNoResultsFoundLayout.visibility = View.GONE
                binding.nsvNoInternetConnectionResultLayout.visibility = View.GONE
                binding.progressBar.visibility = View.GONE

                binding.rvFeaturedCollections.visibility = View.VISIBLE
                binding.nsvPhotos.visibility = View.VISIBLE
            }

        }else{

            if (state.photos.isNotEmpty()){
                binding.nsvNoInternetConnectionResultLayout.visibility = View.GONE
                binding.nsvNoResultsFoundLayout.visibility = View.GONE
                binding.progressBar.visibility = View.GONE

                binding.rvFeaturedCollections.visibility = View.VISIBLE
                binding.nsvPhotos.visibility = View.VISIBLE
            }
            else{
                binding.nsvNoResultsFoundLayout.visibility = View.GONE
                binding.nsvPhotos.visibility = View.GONE
                binding.rvFeaturedCollections.visibility = View.GONE

                binding.progressBar.visibility = View.VISIBLE
                binding.nsvNoInternetConnectionResultLayout.visibility = View.VISIBLE
            }

        }

    }
}