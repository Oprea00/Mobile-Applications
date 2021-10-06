package com.example.accessapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

/**
 * Checks the connectivity to the internet.
 */
class InternetConnection {
    private var connectivityManager: ConnectivityManager? = null
    var connected = false

    val isOnline: Boolean
        get() {
            try {
                connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                val networkInfo = connectivityManager!!.activeNetworkInfo
                connected = networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected

                return connected
            } catch (e: Exception) {
                println("CheckConnectivity Exception: " + e.message)
                Log.e("connectivity", e.toString())
            }
            return connected
        }

    companion object {
        private val instance = InternetConnection()
        var context: Context? = null

        fun getInstance(ctx: Context): InternetConnection {
            context = ctx.applicationContext
            return instance
        }
    }
}