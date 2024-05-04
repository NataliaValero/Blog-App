package com.example.blogapp.ui.home


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.blogapp.R
import com.example.blogapp.core.hide
import com.example.blogapp.core.show
import com.example.blogapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





        setUpBottomNavView()
        observeDestinationChange()

    }

    private fun setUpBottomNavView() {

        // Set up bottom nav view

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val bottomBarView = binding.bottomNavigationView
        binding.bottomNavigationView.setupWithNavController(navController)

        bottomBarView.setOnItemSelectedListener {menuItem->
            // limpiar backstack antes de navegar
            navController.popBackStack()

            when(menuItem.itemId) {
                R.id.homeScreenFragment -> {
                    navController.navigate(R.id.homeScreenFragment)
                    true
                }
                R.id.cameraFragment -> {
                    navController.navigate(R.id.cameraFragment)
                    true
                }
                R.id.profileFragment -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                else -> {
                    false
                }

            }

        }



    }



    private fun observeDestinationChange() {
        // Hide or show bottom nav view
        navController.addOnDestinationChangedListener {controller, destination, arguments->

            if(destination.id == R.id.loginFragment || destination.id == R.id.registerFragment || destination.id == R.id.setupProfileFragment) {
                binding.bottomNavigationView.hide()
            } else {
                binding.bottomNavigationView.show()
            }
        }
    }
}


