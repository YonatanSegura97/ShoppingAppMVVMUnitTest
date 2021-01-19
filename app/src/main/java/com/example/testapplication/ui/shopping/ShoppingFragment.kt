package com.example.testapplication.ui.shopping

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.ui.adapters.ShoppingItemAdapter
import com.example.testapplication.databinding.FragmentShoppingBinding
import com.example.testapplication.ui.viewModel.ShoppingViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinApiExtension

// Created by Victor Hernandez on 1/8/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

@KoinApiExtension
class ShoppingFragment : Fragment(R.layout.fragment_shopping) {

    private val viewmodel: ShoppingViewModel by inject()
    private val shoppingAdapter : ShoppingItemAdapter by inject()

    private var _binding:FragmentShoppingBinding? = null
    private val binding:FragmentShoppingBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentShoppingBinding.bind(view)
        subcriberToObservers()
        setupRecyclerView()
        onClick()
    }

    private fun onClick(){
        binding.fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(
                    ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
            )
        }
    }

    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0, LEFT or RIGHT
    ){
        override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
           val pos = viewHolder.layoutPosition
            val item = shoppingAdapter.shoppingItems[pos]
            viewmodel.deleteShoppingItem(item)
            Snackbar.make(
                    requireView(),
                    "Successfully deleted item",
                    Snackbar.LENGTH_LONG
            ).apply {
                setAction("Undo"){
                    viewmodel.insertShoppingItemIntoDb(item)
                }
                show()
            }
        }
    }

    private fun subcriberToObservers(){
        viewmodel.shoppingItem.observe(viewLifecycleOwner,){
            shoppingAdapter.shoppingItems = it
        }

        viewmodel.totalPrice.observe(viewLifecycleOwner){
            val price = it ?: 0f
            binding.tvShoppingItemPrice.text = "Total Price: $$price"
        }
    }

    private fun setupRecyclerView(){
        binding.rvShoppingItems.apply {
            adapter = shoppingAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}