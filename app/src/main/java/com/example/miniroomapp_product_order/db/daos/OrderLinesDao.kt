package com.example.miniroomapp_product_order.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.miniroomapp_product_order.db.entities.OrderLinesEntity



@Dao
interface OrderLinesDao {

    @Insert
    suspend fun insert(order: OrderLinesEntity):Long
    @Query("Select quantity from `order lines` where orderId == :id")
    suspend fun selectAll(id: Int): Int

    @Query("Select productId from `order lines` where orderId==:id")
    suspend fun getProductId(id:Int):Int

    @Query("Select id from `Order Lines` where orderId==:id")
    suspend fun getId(id:Int):Int

    @Query("Select * from `order lines` where orderId==:id")
    suspend fun getAll(id:Int):OrderLinesEntity

    @Delete
    suspend fun delete(order: OrderLinesEntity)
}