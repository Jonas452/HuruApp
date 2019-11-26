package com.example.huruapp.DAOs

import android.util.Log
import androidx.room.*
import com.example.huruapp.model.Inventory
import org.json.JSONArray

@Dao

interface InventoryDAO {

    @Query("SELECT * FROM inventory ORDER BY date_created DESC")
    fun getAllOrderByDate(): List<Inventory>

    @Query("SELECT * FROM inventory WHERE code LIKE :code")
    fun findByCode(code: String): Inventory

    @Insert
    fun insert(vararg inventory: Inventory)

    @Update
    fun update(vararg inventory: Inventory)

}