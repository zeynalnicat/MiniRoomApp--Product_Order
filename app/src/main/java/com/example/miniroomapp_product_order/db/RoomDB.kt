package com.example.miniroomapp_product_order.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.miniroomapp_product_order.db.daos.OrderDao
import com.example.miniroomapp_product_order.db.daos.OrderLinesDao
import com.example.miniroomapp_product_order.db.daos.ProductDao
import com.example.miniroomapp_product_order.db.entities.OrderEntity
import com.example.miniroomapp_product_order.db.entities.OrderLinesEntity
import com.example.miniroomapp_product_order.db.entities.ProductEntity


@Database(
    entities = [OrderEntity::class, OrderLinesEntity::class, ProductEntity::class],
    version = 1
)
abstract class RoomDB : RoomDatabase() {
    abstract fun orderDao(): OrderDao
    abstract fun productDao(): ProductDao
    abstract fun orderLinesDao(): OrderLinesDao

    companion object {
        private var INSTANCE: RoomDB? = null
        fun accessDB(context: Context): RoomDB? {
            if (INSTANCE == null) {
                synchronized(RoomDB::class) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context.applicationContext,
                            RoomDB::class.java,
                            "Matrix"
                        )
                            .build()
                }
            }
            return INSTANCE
        }
    }

}