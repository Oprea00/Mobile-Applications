package com.example.accessapp

import android.util.Log

fun Any.logd(message: Any? = "no message"){
    Log.d(this.javaClass.simpleName, message.toString())
}