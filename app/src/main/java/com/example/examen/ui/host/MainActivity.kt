package com.example.examen.ui.host

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.examen.R
import com.example.examen.databinding.ActivityMainBinding
import com.example.examen.ui.gallery.GalleryFragment
import com.example.examen.ui.location.LocationFragment
import com.example.examen.ui.movies.MoviesFragment
import com.example.examen.ui.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var activityFragment: Fragment
    private lateinit var fragmentManager: FragmentManager

    private val profileFragment = ProfileFragment()
    private val moviesFragment = MoviesFragment()
    private val locationFragment = LocationFragment()
    private val galleryFragment = GalleryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initElements()
    }

    private fun initElements() {
        fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction().replace(R.id.hostFragment, profileFragment).commit()

        binding.nav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.action_profile -> {
                    replaceFragment(profileFragment)
                }
                R.id.action_movies -> {
                    replaceFragment(moviesFragment)
                }
                R.id.action_location -> {
                    replaceFragment(locationFragment)
                }
                R.id.action_gallery -> {
                    replaceFragment(galleryFragment)
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.hostFragment, fragment)
        fragmentTransaction.commit()
    }
}