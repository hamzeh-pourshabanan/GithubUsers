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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchUsersBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        initializeToolbar()

    }

    fun showToolbar(show: Boolean = true): Boolean {
        binding.appBarLayout.visibility = if (show) View.VISIBLE else View.GONE
        return show
    }

    fun showBackButton(show: Boolean = false): Boolean {
        binding.toolbarAction.visibility = if (show) View.VISIBLE else View.GONE
        return show
    }

    fun setToolbarTitle(title: String ) {
        binding.toolbarTitle.text = title
    }

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
}