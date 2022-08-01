package com.example.gituserhub

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gituserhub.adapter.UserAdapter
import com.example.gituserhub.databinding.ActivityMainBinding
import com.example.gituserhub.helper.ViewModelFactory
import com.example.gituserhub.model.UserProfiles
import com.example.gituserhub.settings.SettingPreferences
import com.example.gituserhub.viewmodel.MainViewModel
import com.example.gituserhub.viewmodel.ThemeViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var bind: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.userRecycleView.setHasFixedSize(true)
        userAdapter = UserAdapter()
        displayUserList()
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        mainViewModel.getProfile().observe(this) { items ->
            if (items !== null) {
                userAdapter.setUserData(items)
                showLoadingProcess(false)

                if(items.size == 0){
                    Toast.makeText(this, "Pengguna tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
        }
        showLoadingProcess(false)
        supportActionBar?.setTitle(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflaterMenu = menuInflater
        inflaterMenu.inflate(R.menu.menus, menu)
        val searchAction = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchScreen = menu.findItem(R.id.app_bar_search).actionView as SearchView

        searchScreen.setSearchableInfo(searchAction.getSearchableInfo(componentName))
        searchScreen.queryHint = resources.getString(R.string.example)
        searchScreen.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.getUserList(query)
                showLoadingProcess(true)
                Log.d("SearchAPI", query)
                searchScreen.clearFocus()
                Toast.makeText(this@MainActivity, "Mencari ${query}", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        val switchTheme = menu.findItem(R.id.app_bar_theme)
        switchTheme.setActionView(R.layout.switch_theme_mode)

        val switch = switchTheme.actionView.findViewById<SwitchMaterial>(R.id.switch_to_dark)
        val pref = SettingPreferences.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ThemeViewModel::class.java
        )
        themeViewModel.getThemePreferences().observe(
            this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switch.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switch.isChecked = false
            }
        }
        switch.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemePreferences(isChecked)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_favorite -> {
                startActivity(Intent(this@MainActivity, FavoriteUsers::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoadingProcess(isLoading: Boolean) {
        if (isLoading) {
            bind.loading.visibility = View.VISIBLE
        } else {
            bind.loading.visibility = View.GONE
        }
    }

    private fun displayUserList() {
        bind.userRecycleView.layoutManager = LinearLayoutManager(this)
        bind.userRecycleView.adapter = userAdapter
        userAdapter.setOnProfileCallback(object : UserAdapter.OnProfileCallback {
            override fun onProfileClicked(data: UserProfiles) {
                chosenProfile(data)
            }
        })
    }

    private fun chosenProfile(profiles: UserProfiles) {
        val sendData = Intent(this, UserDetail::class.java)
        sendData.putExtra(UserDetail.EXTRA_PROFILE, profiles)
        startActivity(sendData)
        Toast.makeText(this, "Memuat profile " + profiles.names, Toast.LENGTH_SHORT).show()
    }
}

