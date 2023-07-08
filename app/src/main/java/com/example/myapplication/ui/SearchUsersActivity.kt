package com.example.myapplication.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Injection
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySearchUsersBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchUsersActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySearchUsersBinding
    protected var navController: NavController? = null
    val viewModel: SearchUsersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchUsersBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        initializeToolbar()

    }

    fun showToolbar(): Boolean = false

    private fun initializeToolbar() {
        binding.appBarLayout.visibility = View.VISIBLE
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
            setDisplayShowTitleEnabled(false)
        }

        binding.toolbar.navigationIcon = null
        binding.appBarLayout.visibility = if (showToolbar()) View.VISIBLE else View.GONE

        binding.toolbarActionArea.setOnClickListener {
            onBackPressed()
        }
    }



    private fun initializeNavigationGraph() {
        val navigationGraph = R.navigation.nav_graph
        navigationGraph.let {
            val navHostFragment = binding.navHostFragment as NavHostFragment
//                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
            navController?.setGraph(it)
        }
    }
}