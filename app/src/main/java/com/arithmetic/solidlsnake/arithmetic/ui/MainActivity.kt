package com.arithmetic.solidlsnake.arithmetic.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arithmetic.solidlsnake.arithmetic.R
import com.arithmetic.solidlsnake.arithmetic.ui.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
                .add(R.id.container, MainFragment())
                .commit()
    }
}
