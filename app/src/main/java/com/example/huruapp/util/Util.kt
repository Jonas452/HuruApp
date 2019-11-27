package com.example.huruapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun isOnline(context: Context): Boolean {
    val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo=connectivityManager.activeNetworkInfo
    return  networkInfo!=null && networkInfo.isConnected
}

fun formatDate(date: String): String {
    val outputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    val inputFormat: DateFormat =
        SimpleDateFormat("yyyy-MM-dd", Locale.US)

    val inputText = date
    val dateOutput: Date = inputFormat.parse(inputText)
    val outputText: String = outputFormat.format(dateOutput)

    return outputText
}

fun loadImage(url: String): Bitmap? = runBlocking {
    val bmp: Bitmap?
    val funAsync = GlobalScope.async {
        try {
            val url = URL(url)
            val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            return@async bmp
        }catch (e: Exception) {
            return@async null
        }
    }

    bmp = funAsync.await()

    return@runBlocking bmp
}