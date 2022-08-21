package com.myapp.simplegallery.extentions


import android.content.Context

import android.database.Cursor
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build

import android.provider.MediaStore
import android.util.Log

import androidx.appcompat.app.AlertDialog

import com.myapp.simplegallery.R
import java.io.File



fun Context.getFileInFiles(fileName: String, folderName: String): File {
    val file: File
    val filePath = File(this.filesDir, folderName)
    file = File(filePath, "$fileName")
    file.parentFile.mkdirs()
    return file
}

fun Context.getFileFromFiles(fileName: String): File {
    val file: File
    val filePath = File(this.filesDir.path)
    file = File(filePath, "$fileName")
    file.parentFile.mkdirs()
    return file
}

fun Context.isOnline(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}


fun Context.noInternetDialog(mCallback: () -> Unit){
    AlertDialog.Builder(this)
        .setTitle(R.string.simple_gallery)
        .setMessage(R.string.no_internet)
        .setPositiveButton("OK") { dialogInterface, which ->

        }.setOnDismissListener {
            mCallback.invoke()
        }
        .create().show()
}
