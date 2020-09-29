package ch.iagentur.unity.testdogsproject.misc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun Context.inflate(resource: Int, container: ViewGroup?): View {
    return LayoutInflater.from(this).inflate(resource, container, false)
}