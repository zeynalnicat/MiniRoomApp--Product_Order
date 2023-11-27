
package com.example.miniroomapp_product_order.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miniroomapp_product_order.databinding.ListOrdersBinding
import com.example.miniroomapp_product_order.db.RoomDB
import com.example.miniroomapp_product_order.db.entities.OrderEntity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrdersListAdapter(private val listOrder: MutableList<OrderEntity>, private val db: RoomDB) :
    RecyclerView.Adapter<OrdersListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listOrder.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = listOrder[position]
        return holder.bind(current)
    }

    inner class ViewHolder(private val binding: ListOrdersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(current: OrderEntity) {
            var orderLines: Int?
            var productId: Int?
            var orderName: String?
            CoroutineScope(Dispatchers.Main).launch {
                    orderLines = db.orderLinesDao().selectAll(current.id)
                    productId = db.orderLinesDao().getProductId(current.id)
                    productId?.let {
                        orderName = db.productDao().getName(it)
                        binding.txtProductName.text = orderName
                    }
                    binding.txtQuantity.text = orderLines.toString()
                    binding.btnDelete.setOnClickListener {
                        delete(current.id, current)

                }
            }
            binding.txtCustomerName.text = current.customerName
            binding.txtDate.text = current.date
        }

        private fun delete(orderId: Int, current: OrderEntity) {
            CoroutineScope(Dispatchers.Main).launch {
                val orderLinesEntity = db.orderLinesDao().getAll(orderId)
                orderLinesEntity?.let {
                    db.orderLinesDao().delete(it)
                    db.orderDao().delete(current)
                    val position = listOrder.indexOf(current)
                    if (position != -1) {
                        listOrder.removeAt(position)
                        notifyDataSetChanged()
                    }
                }
            }

        }


    }
}