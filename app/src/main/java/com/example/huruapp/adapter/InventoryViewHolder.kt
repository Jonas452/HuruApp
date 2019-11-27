package com.example.huruapp.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.huruapp.R
import com.example.huruapp.model.Inventory
import com.example.huruapp.util.formatDate
import com.example.huruapp.util.loadImage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class InventoryViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.inventory_item, parent, false)) {
    private var mCodeView: TextView? = null
    private var mStatusView: TextView? = null
    private var mDateView: TextView? = null
    private var mImageView: ImageView? = null


    init {
        mCodeView = itemView.findViewById(R.id.code)
        mStatusView = itemView.findViewById(R.id.status)
        mDateView = itemView.findViewById(R.id.date)
        mImageView = itemView.findViewById(R.id.image)
    }

    fun bind(inventory: Inventory) {
        val text = mCodeView?.text
        val code = inventory.code

        mCodeView?.text =  "$text $code"
        mStatusView?.text = inventory.status
        mDateView?.text = formatDate(inventory.date_created)
        mImageView?.setImageBitmap(loadImage(inventory.image))
    }

}