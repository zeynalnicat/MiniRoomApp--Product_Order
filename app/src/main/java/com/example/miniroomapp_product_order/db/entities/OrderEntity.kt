package com.example.miniroomapp_product_order.db.entities


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("Orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val customerName: String,
    val date: String


)
