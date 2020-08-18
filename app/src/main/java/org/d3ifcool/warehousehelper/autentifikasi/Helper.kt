package org.d3ifcool.warehousehelper.autentifikasi

import android.content.Context
import android.content.Intent
import android.os.Message
import android.widget.Toast
import org.d3ifcool.warehousehelper.ui.DashboardActivity

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.login(){
    val intent = Intent(this,DashboardActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}
fun Context.signUp(){
    val intent = Intent(this, DashboardActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}
fun Context.logout(){
    val intent = Intent(this, LoginActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}