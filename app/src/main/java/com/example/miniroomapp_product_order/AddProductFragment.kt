package com.example.miniroomapp_product_order


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.miniroomapp_product_order.databinding.FragmentAddProductBinding
import com.example.miniroomapp_product_order.db.RoomDB
import com.example.miniroomapp_product_order.db.entities.ProductEntity
import com.google.android.material.snackbar.Snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddProductFragment : Fragment() {
    private var db: RoomDB? = null
    private lateinit var binding : FragmentAddProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductBinding.inflate(inflater)
        db = RoomDB.accessDB(requireContext())
        insert()
        return binding.root
    }

    private fun insert(){
        binding.btnSave.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
               val chck =  db?.productDao()!!.insert(ProductEntity(name = binding.edtProductName.text.toString() , countOnStock = binding.edtCountOnStack.text.toString().toInt() ))
                if(chck!=-1L){
                    Snackbar.make(requireView(),"Successfully added",Snackbar.LENGTH_SHORT).show()
                }else{
                    Snackbar.make(requireView(),"There was an error in adding",Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

}