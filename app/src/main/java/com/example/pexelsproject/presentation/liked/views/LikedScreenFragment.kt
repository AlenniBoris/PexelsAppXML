package com.example.pexelsproject.presentation.liked.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.example.pexelsproject.databinding.FragmentLikedScreenBinding
import com.example.pexelsproject.presentation.uikit.theme.PexelsApplicationTheme
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
                PexelsApplicationTheme {
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