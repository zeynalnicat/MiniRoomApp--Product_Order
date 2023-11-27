package com.example.miniroomapp_product_order


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miniroomapp_product_order.adapters.ProductListAdapter
import com.example.miniroomapp_product_order.databinding.DialogUserBinding
import com.example.miniroomapp_product_order.databinding.FragmentProductListBinding
import com.example.miniroomapp_product_order.db.RoomDB

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductListFragment : Fragment() {
    private lateinit var binding: FragmentProductListBinding
    private var db: RoomDB? = null
    private  var adapter: ProductListAdapter? = null

    companion object {
        private var name: String? = null
        var date : String? =null

        fun setName(name: String) {
            this.name = name
        }

        fun getName(): String? {
            return this.name
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductListBinding.inflate(inflater)
        db = RoomDB.accessDB(requireContext())
        setAdapter()
        setDialog()
        return binding.root
    }


    private fun setAdapter() {
        CoroutineScope(Dispatchers.IO).launch {
            val productLists = db?.productDao()?.selectAll() ?: emptyList()
            if(productLists.isNotEmpty()){
                binding.txtNothing2.visibility = View.GONE
            }else{
                binding.txtNothing2.visibility = View.VISIBLE
            }
            withContext(Dispatchers.Main) {
                adapter = ProductListAdapter(productLists, db!!)
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.adapter = adapter
            }
        }
    }

    private fun setDialog() {
        if (getName() == null) {
            val dialogBinding = DialogUserBinding.inflate(layoutInflater)
            val dialogView = dialogBinding.root
            val builder = AlertDialog.Builder(requireContext()).setView(dialogView)
            val dialog = builder.create()
            dialogBinding.button2.setOnClickListener {
                setName(dialogBinding.edtEnterUsername.text.toString())
                dialog.dismiss()
            }
            dialog.show()

            dialogBinding.edt.setOnClickListener {
                val c = Calendar.getInstance()
                val mYear = c.get(Calendar.YEAR)
                val mMonth = c.get(Calendar.MONTH)
                val mDay = c.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { view, year, monthOfYear, dayOfMonth ->
                        date = "$dayOfMonth/${monthOfYear + 1}/$year"
                        dialogBinding.edt.setText(date)
                    },
                    mYear, mMonth, mDay
                )
                date = dialogBinding.edt.text.toString()
                datePickerDialog.show()
            }
            }
        }
    }



