package com.example.miniroomapp_product_order.db.daos


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.miniroomapp_product_order.db.entities.OrderEntity



@Dao
interface OrderDao {

    @Query("Select * from orders")
    suspend fun selectAll(): MutableList<OrderEntity>


    @Query("Select id from orders where orders.customerName == :name")
    suspend fun getId(name: String): Int

    @Insert
    suspend fun insert(orderEntity: OrderEntity): Long

    @Delete
    suspend fun delete(orderEntity: OrderEntity)
}