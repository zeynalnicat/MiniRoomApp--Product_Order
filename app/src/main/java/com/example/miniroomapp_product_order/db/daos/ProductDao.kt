package com.example.miniroomapp_product_order.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.miniroomapp_product_order.db.entities.ProductEntity



@Dao
interface ProductDao {

    @Query("Select * from products")
    suspend fun selectAll():     List<ProductEntity>

    @Insert
    suspend fun insert(product: ProductEntity): Long

    @Query("Select id from products where products.name=:name")
    suspend fun findId(name: String): Int


    @Query("Select name from products where products.id=:id")
    suspend fun getName(id:Int):String

    @Query("Select countOnStock from products where name=:name")
    suspend fun selectCountOnStock(name: String): Int
}