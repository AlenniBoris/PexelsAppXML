package com.example.pexelsproject.presentation.liked.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.example.pexelsproject.databinding.FragmentLikedScreenBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LikedScreenFragment : Fragment() {

    private var likedScreenBinding: FragmentLikedScreenBinding? = null
    private val binding get() = likedScreenBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        likedScreenBinding = FragmentLikedScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    LikedScreen()
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        likedScreenBinding = null
    }

}