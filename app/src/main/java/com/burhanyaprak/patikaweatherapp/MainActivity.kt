package com.burhanyaprak.patikaweatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.burhanyaprak.patikaweatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = setupNavController().navController
        setupActionBarWithNavController(navController)
    }

    private fun setupNavController(): NavHostFragment {
        return supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
    }

    //Support a back button to navigate fragments
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() or super.onSupportNavigateUp()
    }
}