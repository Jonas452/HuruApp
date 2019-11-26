package com.example.huruapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Inventory(
    @PrimaryKey var code: String,
    var status: String,
    var image: String,
    var date_created: String
)
