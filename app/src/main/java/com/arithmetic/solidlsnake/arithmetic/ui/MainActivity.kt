package com.arithmetic.solidlsnake.arithmetic.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arithmetic.solidlsnake.arithmetic.R
import com.arithmetic.solidlsnake.arithmetic.ioc.ActivityModule
import com.arithmetic.solidlsnake.arithmetic.ioc.ActivityModuleImpl
import com.arithmetic.solidlsnake.arithmetic.ioc.AppModule
import com.arithmetic.solidlsnake.arithmetic.ui.fragment.MainFragment

class MainActivity : AppCompatActivity(), Router {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityModule.instance = ActivityModuleImpl(AppModule.instance, this)

        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
                .add(R.id.container, MainFragment())
                .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            ActivityModule.instance = null
        }
    }

    override fun goToMainScreen() = runOnUiThread {
        Toast.makeText(this, "Go to Main screen", Toast.LENGTH_SHORT).show()
    }

    override fun goToGameScreen() = runOnUiThread {
        Toast.makeText(this, "Go to Game screen", Toast.LENGTH_SHORT).show()
    }

    override fun goToSettingsScreen() = runOnUiThread {
        Toast.makeText(this, "Go to Settings screen", Toast.LENGTH_SHORT).show()
    }
}
