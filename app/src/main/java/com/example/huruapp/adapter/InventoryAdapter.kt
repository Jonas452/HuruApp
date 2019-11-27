package com.example.huruapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.huruapp.model.Inventory

class InventoryAdapter(private val list: List<Inventory>)
    : RecyclerView.Adapter<InventoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return InventoryViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val inventory: Inventory = list[position]
        inventory.code = inventory.code
        holder.bind(inventory)
    }

}