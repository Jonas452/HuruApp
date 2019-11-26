package com.example.huruapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.huruapp.DAOs.InventoryDAO
import com.example.huruapp.model.Inventory

@Database(
    entities = [Inventory::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun InventoryDAO(): InventoryDAO

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "huru-app.db")
            .build()
    }
}