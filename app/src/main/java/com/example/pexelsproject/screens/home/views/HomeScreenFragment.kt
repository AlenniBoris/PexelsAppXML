package com.example.pexelsproject.screens.home.views

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pexelsproject.R
import com.example.pexelsproject.data.model.Photo
import com.example.pexelsproject.databinding.FragmentHomeScreenBinding
import com.example.pexelsproject.di.PexelsApplication
import com.example.pexelsproject.navigation.Screen
import com.example.pexelsproject.screens.home.HomeScreenState
import com.example.pexelsproject.screens.home.HomeScreenViewModel
import com.example.pexelsproject.utils.FeaturedCollectionsRecyclerAdapter
import com.example.pexelsproject.utils.PhotosRecyclerAdapter
import com.example.pexelsproject.utils.SearchBarHistoryRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeScreenFragment() : Fragment() {

    private val viewModel: HomeScreenViewModel by viewModels()

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
        featuredCollectionsAdapter = FeaturedCollectionsRecyclerAdapter{query ->
            viewModel.queryTextChanged(query)
            viewModel.forceSearchPhoto(query)
            binding.searchView.hide()
        }
        binding.rvFeaturedCollections.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        binding.rvFeaturedCollections.adapter = featuredCollectionsAdapter

        //Photos
        photosAdapter = PhotosRecyclerAdapter()
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
        featuredCollectionsAdapter.submitList(state.featuredCollections)
        photosAdapter.submitList(state.photos)
        searchHistoryAdapter.submitList(state.history.toList())
//        searchBar.setQuery(state.queryText, true)
        val isActive = state.isActive

        binding.searchBar.setText(state.queryText)

        binding.searchView.editText.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                val enteredQuery = v?.text.toString()
                viewModel.forceSearchPhoto(enteredQuery)
                viewModel.queryTextChanged(enteredQuery)
                binding.searchView.hide()
                return true
            }
        })

//        searchBar.setOnQueryTextFocusChangeListener{ _, hasFocus ->
//            viewModel.changeIsActive(hasFocus)
//            if (hasFocus){
//                showSearchHistory()
//            }
//        }
//        searchBar.setOnQueryTextListener((object : SearchView.OnQueryTextListener{
//            //End of input and submitted query
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                viewModel.forceSearchPhoto(query ?: "")
//                Log.d("QUERY", state.queryText)
//                searchBar.clearFocus()
//                hideSearchHistory()
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//                //TODO make search after 2.5 seconds
//
//                showSearchHistory()
//                return true
//            }
//        }))

    }

//    private fun showSearchHistory() {
//        searchHistoryContainer.visibility = View.VISIBLE
//        photosRecyclerView.visibility = View.GONE
//        collectionsRecyclerView.visibility = View.GONE
//    }
//
//    private fun hideSearchHistory() {
//        searchHistoryContainer.visibility = View.GONE
//        photosRecyclerView.visibility = View.VISIBLE
//        collectionsRecyclerView.visibility = View.VISIBLE
//    }
}