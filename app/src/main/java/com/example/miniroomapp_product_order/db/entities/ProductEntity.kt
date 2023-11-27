package com.example.miniroomapp_product_order.db.entities


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("Products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val countOnStock: Int
)
