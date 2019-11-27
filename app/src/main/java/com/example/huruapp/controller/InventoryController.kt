package com.example.huruapp.controller

import com.example.huruapp.database.AppDatabase
import com.example.huruapp.model.Inventory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONArray
import java.lang.Exception

class InventoryController(val db: AppDatabase) {

    fun storeJSONLocally(jsonArray: JSONArray) {

        GlobalScope.async {
            for (i in 0 until jsonArray.length()) {
                try {
                    val json = jsonArray.getJSONObject(i)

                    var inventoryTemp = db.InventoryDAO().findByCode(json.getString("code"))

                    if(inventoryTemp == null) {
                        inventoryTemp = Inventory(
                            json.getString("code"),
                            json.getString("status"),
                            json.getString("image_url"),
                            json.getString("date_created")
                        )

                        db.InventoryDAO().insert(inventoryTemp)

                    }else {
                        inventoryTemp.code = json.getString("code")
                        inventoryTemp.status = json.getString("status")
                        inventoryTemp.image = json.getString("image_url")
                        inventoryTemp.date_created = json.getString("date_created")

                        db.InventoryDAO().update(inventoryTemp)

                    }

                }catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

    }

}