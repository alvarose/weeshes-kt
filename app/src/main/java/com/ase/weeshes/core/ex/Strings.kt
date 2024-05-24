package com.ase.weeshes.core.ex

import android.util.Log
import android.widget.Toast
import com.ase.weeshes.WeeshesApp

fun String.toast(length: Int = Toast.LENGTH_SHORT): Toast = Toast.makeText(WeeshesApp.applicationContext(), this, length).apply { show() }

private const val TAG = "WEE-LOG"
fun String.toErrorLog(tag: String = TAG) = Log.e(tag, "Error: $this")
fun String.toLog(tag: String = TAG) = Log.i(tag, this)