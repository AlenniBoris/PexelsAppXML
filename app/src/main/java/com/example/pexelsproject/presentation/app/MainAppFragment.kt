package com.example.pexelsproject.presentation.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pexelsproject.R
import com.example.pexelsproject.databinding.FragmentMainAppBinding
import com.example.pexelsproject.presentation.bookmarks.views.BookmarksScreenFragment
import com.example.pexelsproject.presentation.home.HomeScreenViewModel
import com.example.pexelsproject.presentation.home.views.HomeScreenFragment

class MainAppFragment : Fragment() {

    private var mainAppFragmentBinding: FragmentMainAppBinding? = null
    private val binding get() = mainAppFragmentBinding!!

    private var _prevDestination: String? = null
    private val prevDestination get() = _prevDestination!!

    private val viewModel: HomeScreenViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainAppFragmentBinding = FragmentMainAppBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {

            _prevDestination = arguments?.getString("key_prev_dest")

            when (prevDestination) {
                "home_screen", "from_bookmarks_screen", "liked_screen" -> {
                    parentFragmentManager.beginTransaction()
                        .replace(
                            binding.flMainAppFragmentContainer.id,
                            HomeScreenFragment(),
                            "HomeScreenFragment"
                        )
                        .commit()
                    binding.ibHomeScreenBtn.setImageResource(R.drawable.icon_home_active)
                    binding.ibBookmarksScreenBtn.setImageResource(R.drawable.icon_favourites_not_active)
                }

                else -> {
                    parentFragmentManager.beginTransaction()
                        .replace(
                            binding.flMainAppFragmentContainer.id,
                            BookmarksScreenFragment(),
                            "BookmarksFragment"
                        )
                        .commit()
                    binding.ibHomeScreenBtn.setImageResource(R.drawable.icon_home_not_active)
                    binding.ibBookmarksScreenBtn.setImageResource(R.drawable.icon_favourites_active)
                }
            }
        }

        setListenersOnButtons()

    }

    override fun onDestroy() {
        super.onDestroy()
        mainAppFragmentBinding = null
    }

    private fun setListenersOnButtons() {
        binding.ibHomeScreenBtn.setOnClickListener {
            val currentFragment = parentFragmentManager.findFragmentByTag("HomeScreenFragment")
            if (currentFragment is HomeScreenFragment) {
                viewModel.queryTextChanged("")
                viewModel.forceSearchPhoto("")
            } else {
                parentFragmentManager.beginTransaction()
                    .replace(
                        binding.flMainAppFragmentContainer.id,
                        HomeScreenFragment(),
                        "HomeScreenFragment"
                    )
                    .commit()
            }
            binding.ibHomeScreenBtn.setImageResource(R.drawable.icon_home_active)
            binding.ibBookmarksScreenBtn.setImageResource(R.drawable.icon_favourites_not_active)
        }
        binding.ibBookmarksScreenBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(
                    binding.flMainAppFragmentContainer.id,
                    BookmarksScreenFragment(),
                    "BookmarksFragment"
                )
                .commit()
            binding.ibHomeScreenBtn.setImageResource(R.drawable.icon_home_not_active)
            binding.ibBookmarksScreenBtn.setImageResource(R.drawable.icon_favourites_active)
        }
    }
}