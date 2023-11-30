package com.example.turfbooking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.turfbooking.databinding.ActivityOwnerDashboardBinding

class OwnerDashboard : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerDashboardBinding
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Update the toolbar assignment using the generated binding class
        toolbar = findViewById(R.id.toolbar)

        // Set up the toolbar
        setSupportActionBar(toolbar)

        val homeFragment = OwnerHomeFragment()
        val profileFragment = OwnerProfileFragment()
        val settingFragment = OwnerSettingFragment()

        supportActionBar?.title = "Home"
        replaceFragment(homeFragment)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    supportActionBar?.title = "Home"
                    replaceFragment(homeFragment)
                }
                R.id.profile -> {
                    supportActionBar?.title = "Profile"
                    replaceFragment(profileFragment)
                }
                R.id.setting -> {
                    supportActionBar?.title = "Setting"
                    replaceFragment(settingFragment)
                }

            }
            true
        }
    }
    private  fun replaceFragment(fragment : Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, fragment)
            transaction.commit()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val onActionExpandListener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                Toast.makeText(this@OwnerDashboard, "Search is Expanded", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                Toast.makeText(this@OwnerDashboard, "Search is Collapsed", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        val searchItem = menu.findItem(R.id.search)
        searchItem.setOnActionExpandListener(onActionExpandListener)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search Data here..."
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.person -> {
                Toast.makeText(this, "Person clicked", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.search -> {
                Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
