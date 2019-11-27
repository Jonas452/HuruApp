package com.example.huruapp.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request.Method
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.huruapp.controller.InventoryController
import com.example.huruapp.R
import com.example.huruapp.adapter.InventoryAdapter
import com.example.huruapp.database.AppDatabase
import com.example.huruapp.model.Inventory
import com.example.huruapp.util.isOnline
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: InventoryAdapter

    private lateinit var db: AppDatabase
    val URL =
        "https://gist.githubusercontent.com/kevinmcampos/b0f8c18fe478e11a06a1e9cb96fd3d7b/raw/53fc49f38578597eb7708da8aa5a19ca96a963bc/items"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase(this)

    }

    override fun onStart() {
        super.onStart()

        loadInventoryList(this)
    }

    private fun loadInventoryList(context: Context) = runBlocking {
        if (isOnline(context)) {
            retriveFromAPI()
        } else {
            val t = Toast.makeText(
                this@MainActivity,
                getString(R.string.data_loading_msg),
                Toast.LENGTH_LONG
            )
            t.show()
        }

        val data = loadFromDatabase()

        recyclerViewInventory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = InventoryAdapter(data)
        }

    }

    private suspend fun retriveFromAPI() {
        Log.e("APP", "Searching API STARTS")
        val queue = Volley.newRequestQueue(this.applicationContext)

        val apiCall = GlobalScope.async {
            val jsonRequest = StringRequest(
                Method.GET, URL,
                Response.Listener<String> { response ->
                    val json = JSONObject(response)

                    val inventoryController = InventoryController(db)
                    inventoryController.storeJSONLocally(json.getJSONArray("results"))

                },
                Response.ErrorListener { Log.e("Response", "Error") })

            queue.add(jsonRequest)
        }

        apiCall.await()
        Log.e("APP", "Searching API ENDS")
    }

    private suspend fun loadFromDatabase(): List<Inventory> {
        val data = GlobalScope.async{ db.InventoryDAO().getAllOrderByDate() }
        return data.await()
    }

}
