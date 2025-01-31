package com.geeks.noteapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.geeks.noteapp.R
import com.geeks.noteapp.utils.PreferenceHelper

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        val sharedPreferences = PreferenceHelper()
        sharedPreferences.init(this)

        if (sharedPreferences.onBoard && sharedPreferences.isRegistered){
            navController.navigate(R.id.noteFragment)
        }else{
            navController.navigate(R.id.onBoardFragment)
        }
    }
}