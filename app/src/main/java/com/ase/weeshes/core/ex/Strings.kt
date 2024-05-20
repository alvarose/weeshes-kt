package com.ase.weeshes.core.ex

import android.widget.Toast
import com.ase.weeshes.WeeshesApp

fun String.toast(length: Int = Toast.LENGTH_SHORT): Toast = Toast.makeText(WeeshesApp.applicationContext(), this, length).apply { show() }