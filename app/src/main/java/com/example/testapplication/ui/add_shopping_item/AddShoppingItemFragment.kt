package com.example.testapplication.ui.add_shopping_item

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.testapplication.R
import com.example.testapplication.databinding.FragmentAddShoppingItemBinding
import com.example.testapplication.ui.viewModel.ShoppingViewModel
import com.example.testapplication.utils.Status
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinApiExtension

// Created by Victor Hernandez on 1/8/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

@KoinApiExtension
class AddShoppingItemFragment : Fragment(R.layout.fragment_add_shopping_item) {

    private val viewmodel: ShoppingViewModel by inject()
    private val glide by inject<RequestManager>()


    private var _binding: FragmentAddShoppingItemBinding? = null
    private val binding:FragmentAddShoppingItemBinding
    get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddShoppingItemBinding.bind(view)
        subscribeToObservers()
        onClick()
    }

    private fun onClick(){
        binding.ivShoppingImage.setOnClickListener {
            findNavController().navigate(
                    AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
            )
        }

        binding.btnAddShoppingItem.setOnClickListener {
            binding.apply {
                viewmodel.insertShoppingItem(
                        etShoppingItemName.text.toString(),
                        etShoppingItemAmount.text.toString(),
                        etShoppingItemPrice.text.toString()
                )
            }

        }
        //back callback
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                viewmodel.setCurlImageUrl("")
                findNavController().popBackStack()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    @SuppressLint("LogNotTimber")
    private fun subscribeToObservers() {
     observerImageUrl()
     observerInsertStatus()
    }

    private fun observerImageUrl(){
        viewmodel.curlImageUrl.observe(viewLifecycleOwner,{
            it?.let {
                glide
                    .load(it)
                    .circleCrop()
                    .into(binding.ivShoppingImage)
            }
        })
    }

    private fun observerInsertStatus(){
        viewmodel.insertShoppingItemStatus.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { result ->
                when (result.status){
                    Status.SUCCESS -> {
                        messageSnackbar("Added Shopping Item")
                        findNavController().popBackStack()
                    }
                    Status.ERROR -> {
                        messageSnackbar(result.message ?: "An unknown error occured")
                    }
                    Status.LOADING -> {
                        // NO.OP
                    }
                }

            }
        })
    }

    private fun messageSnackbar(message:String){
        Snackbar.make(
            requireView().rootView,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}