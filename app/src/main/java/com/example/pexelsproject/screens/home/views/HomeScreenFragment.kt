package com.example.pexelsproject.screens.home.views

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
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
import com.example.pexelsproject.utils.ExtraFunctions
import com.example.pexelsproject.utils.FeaturedCollectionsRecyclerAdapter
import com.example.pexelsproject.utils.PhotosRecyclerAdapter
import com.example.pexelsproject.utils.SearchBarHistoryRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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

        binding.tvCheckInternetConnectionAgain.setOnClickListener {
            viewModel.checkInternetConnection()
            Log.d("interet", "tapped")
        }

        binding.tvGetQueryResultsAgain.setOnClickListener {
            viewModel.queryTextChanged("")
            viewModel.forceSearchPhoto("")
            Log.d("explore", "tapped")
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

        val isActive = state.isActive

        binding.searchBar.setText(state.queryText)

        binding.searchView.editText.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                val enteredQuery = v?.text.toString()
                viewModel.queryTextChanged(enteredQuery)
                viewModel.forceSearchPhoto(enteredQuery)
                binding.searchView.hide()
                return true
            }
        })

        binding.fab.setOnClickListener{
            PexelsApplication.router.navigateTo(Screen.LikedScreen())
        }

//        viewModel.isOnline.observe(viewLifecycleOwner){ isOnline ->
//            if (isOnline){
//                Log.d("ONLINE--->", "true")
//                binding.rvFeaturedCollections.visibility = View.VISIBLE
//                if (state.photos.isEmpty()){
//                    binding.nsvNoResultsFoundLayout.visibility = View.VISIBLE
//                    binding.nsvPhotos.visibility = View.GONE
//                } else{
//                    binding.nsvNoResultsFoundLayout.visibility = View.GONE
//                    binding.nsvPhotos.visibility = View.VISIBLE
//                }
//
//                binding.progressBar.visibility = View.GONE
//                binding.nsvNoInternetConnectionResultLayout.visibility = View.GONE
//            }else{
//                Log.d("ONLINE--->", "false")
//                binding.rvFeaturedCollections.visibility = View.GONE
//                binding.rvPhotosMain.visibility = View.GONE
//
//                binding.progressBar.visibility = View.VISIBLE
//                binding.nsvNoInternetConnectionResultLayout.visibility = View.VISIBLE
//            }
//        }



    }
}