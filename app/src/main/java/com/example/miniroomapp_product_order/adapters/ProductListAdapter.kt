package com.example.miniroomapp_product_order.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miniroomapp_product_order.ProductListFragment
import com.example.miniroomapp_product_order.databinding.ListProductsBinding
import com.example.miniroomapp_product_order.db.RoomDB
import com.example.miniroomapp_product_order.db.entities.OrderEntity
import com.example.miniroomapp_product_order.db.entities.OrderLinesEntity
import com.example.miniroomapp_product_order.db.entities.ProductEntity
import com.google.android.material.snackbar.Snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListAdapter(private val productList: List<ProductEntity>, private val db: RoomDB) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = productList[position]
        return holder.bind(current)
    }


    inner class ViewHolder(private val binding: ListProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(current: ProductEntity) {
            var onStock: Int? = null
            CoroutineScope(Dispatchers.Main).launch {
                onStock = db.productDao().selectCountOnStock(current.name)
            }
            var count = 0
            binding.txtProductName.text = current.name
            binding.txtOnStack.text = current.countOnStock.toString()
            binding.btnOrder.setOnClickListener {
                if (count > 0) {
                    addToDB(current, binding)
                } else {
                    Snackbar.make(binding.root, "Nothing selected", Snackbar.LENGTH_SHORT).show()
                }

            }
            binding.btnIncrease.setOnClickListener {
                if (count < onStock!!) {
                    count++
                    binding.txtProductCount.text = count.toString()
                }

            }
            binding.btnDecrease.setOnClickListener {
                if (count > 0) {
                    count--
                    binding.txtProductCount.text = count.toString()
                }
            }
        }

    }

    fun addToDB(current: ProductEntity, binding: ListProductsBinding) {
        CoroutineScope(Dispatchers.Main).launch {
            db?.let {
                val insertion = it.orderDao().insert(
                    OrderEntity(
                        customerName = ProductListFragment.getName() ?: "",
                        date = ProductListFragment.date ?: ""
                    )
                )
                if (insertion != -1L) {
                    val productId = db.productDao().findId(current.name)

                    val insertionCheck = it.orderLinesDao().insert(
                        OrderLinesEntity(
                            orderId = insertion.toInt(),
                            productId = productId,
                            quantity =
                            binding.txtProductCount.text.toString().toInt()
                        )
                    )

                    if (insertionCheck != -1L) {
                        Snackbar.make(binding.root, "Successfully ordered!", Snackbar.LENGTH_SHORT)
                            .show()
                    }

                }
            }
        }
    }

}