package com.example.examen.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen.data.model.MoviesModel
import com.example.examen.databinding.GalleryFragmentBinding
import com.example.examen.databinding.MoviesFragmentBinding
import com.example.examen.ui.movies.adapter.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment: Fragment() {
    private lateinit var mBinding: MoviesFragmentBinding

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = MoviesFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUiState()
        initElements()
        viewModel.getMovies()
    }

    private fun initElements(){
        mBinding.rvMovies.layoutManager = LinearLayoutManager(mBinding.root.context)
    }

    private fun initUiState(){
        viewModel.uiMoviesState.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { state ->
            when(state){
                is MoviesViewState.Movies -> {
                    mBinding.rvMovies.adapter = MoviesAdapter(state.movies)
                }
                else -> {
                    //
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}

