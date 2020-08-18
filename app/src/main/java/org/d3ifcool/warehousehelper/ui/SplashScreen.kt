package org.d3ifcool.warehousehelper.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.d3ifcool.warehousehelper.R

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        //fungsi splash screen untuk background halaman pertama
        val background = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(5000)
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}
