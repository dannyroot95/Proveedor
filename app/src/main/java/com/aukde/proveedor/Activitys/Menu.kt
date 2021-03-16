package com.aukde.proveedor.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aukde.proveedor.R

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppThemePink)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }
}