package com.example.miniroomapp_product_order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miniroomapp_product_order.adapters.OrdersListAdapter
import com.example.miniroomapp_product_order.databinding.FragmentOrderListsBinding
import com.example.miniroomapp_product_order.db.RoomDB

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class OrderListsFragment : Fragment() {
    private lateinit var binding: FragmentOrderListsBinding
    private var adapter: OrdersListAdapter? = null
    private var db: RoomDB? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderListsBinding.inflate(inflater)
        db = RoomDB.accessDB(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setNavigation()

    }

    private fun setAdapter() {
        CoroutineScope(Dispatchers.IO).launch {
            val orderLists = db?.orderDao()?.selectAll() ?: emptyList()

            withContext(Dispatchers.Main) {
                if (orderLists.isNotEmpty()) {
                    binding.txtNothing.visibility = View.GONE
                } else {
                    binding.txtNothing.visibility = View.VISIBLE

                }
                adapter = OrdersListAdapter(orderLists.toMutableList(), db!!)
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.adapter = adapter
            }
        }

    }

    private fun setNavigation() {
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_orderListsFragment_to_productListFragment)
        }
    }

}