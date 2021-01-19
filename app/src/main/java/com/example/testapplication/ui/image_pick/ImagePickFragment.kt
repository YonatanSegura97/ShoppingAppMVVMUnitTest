package com.example.testapplication.ui.image_pick

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testapplication.R
import com.example.testapplication.ui.adapters.ImageAdapter
import com.example.testapplication.databinding.FragmentImagePickBinding
import com.example.testapplication.ui.viewModel.ShoppingViewModel
import com.example.testapplication.utils.Constants.GRID_SPAN_COUNT
import com.example.testapplication.utils.Constants.SEARCH_TIME_DELAY
import com.example.testapplication.utils.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinApiExtension

// Created by Victor Hernandez on 1/8/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

@KoinApiExtension
class ImagePickFragment : Fragment(R.layout.fragment_image_pick) {

    val viewModel: ShoppingViewModel by inject()
    val imageAdapter by inject<ImageAdapter>()

    private var _binding: FragmentImagePickBinding? = null
    private val binding:FragmentImagePickBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentImagePickBinding.bind(view)

        subcribeToObservers()
        setupRecyclerView()
        onClick()
        var job: Job? = null
        binding.etSearch.addTextChangedListener{ editable ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(SEARCH_TIME_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchForImage(editable.toString())
                    }
                }
            }
        }

    }

    @SuppressLint("LogNotTimber")
    private fun onClick(){
        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.data = "modificada"
            viewModel.setCurlImageUrl(it)
        }
    }

    private fun setupRecyclerView(){
        binding.rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
        }
    }

    private fun subcribeToObservers(){
        viewModel.images.observe(viewLifecycleOwner){
            it?.getContentIfNotHandled()?.let { result ->
                when(result.status){
                    Status.SUCCESS->{
                        val urls = result.data?.hits?.map { imageResult -> imageResult.previewURL }
                        imageAdapter.images = urls ?: listOf()
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.ERROR->{
                        Snackbar.make(
                                requireView(),
                                result.message ?: "An unknown error occured.",
                                Snackbar.LENGTH_LONG
                        ).show()
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.LOADING->{
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}