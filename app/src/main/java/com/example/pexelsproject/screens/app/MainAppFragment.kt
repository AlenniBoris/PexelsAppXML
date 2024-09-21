package com.example.pexelsproject.screens.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.example.pexelsproject.R
import com.example.pexelsproject.databinding.FragmentMainAppBinding
import com.example.pexelsproject.screens.bookmarks.views.BookmarksScreenFragment
import com.example.pexelsproject.screens.home.HomeScreenViewModel
import com.example.pexelsproject.screens.home.views.HomeScreenFragment

class MainAppFragment : Fragment() {

    private var mainAppFragmentBinding: FragmentMainAppBinding? = null
    private val binding get() = mainAppFragmentBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mainAppFragmentBinding = FragmentMainAppBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null){
            parentFragmentManager.beginTransaction()
                .replace(binding.flMainAppFragmentContainer.id, HomeScreenFragment(""), "MainAppFragment")
                .commit()
            binding.ibHomeScreenBtn.setImageResource(R.drawable.icon_home_active)
        }

        setListenersOnButtons()

    }

    override fun onDestroy() {
        super.onDestroy()
        mainAppFragmentBinding = null
    }

    private fun setListenersOnButtons(){
        binding.ibHomeScreenBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(binding.flMainAppFragmentContainer.id, HomeScreenFragment(""), "MainAppFragment")
                .commit()
            binding.ibHomeScreenBtn.setImageResource(R.drawable.icon_home_active)
            binding.ibBookmarksScreenBtn.setImageResource(R.drawable.icon_favourites_not_active)
        }
        binding.ibBookmarksScreenBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(binding.flMainAppFragmentContainer.id, BookmarksScreenFragment(), "BookmarksFragment")
                .commit()
            binding.ibHomeScreenBtn.setImageResource(R.drawable.icon_home_not_active)
            binding.ibBookmarksScreenBtn.setImageResource(R.drawable.icon_favourites_active)
        }
    }
}