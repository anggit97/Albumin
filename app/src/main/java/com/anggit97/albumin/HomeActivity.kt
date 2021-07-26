package com.anggit97.albumin

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.anggit97.albumin.databinding.ActivityHomeBinding
import com.anggit97.core.base.BaseActivity
import com.anggit97.core.ui.base.consumeBackEventInChildFragment
import com.anggit97.core.util.viewBindings
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.Insetter
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val binding by viewBindings(ActivityHomeBinding::inflate)

    private lateinit var navController: NavController

    private val navHostFragment: NavHostFragment
        get() = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        navController = navHostFragment.navController
        navHostFragment.childFragmentManager.addOnBackStackChangedListener {
            val backStackEntry = navHostFragment.childFragmentManager.backStackEntryCount
            Timber.d("BACKSTACK COUNT : $backStackEntry")
        }

        setUpStatusBarColor()
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun onBackPressed() {
        if (isTaskRoot
            && supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.backStackEntryCount == 0
            && supportFragmentManager.backStackEntryCount == 0
        ) {
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }

    private fun setUpStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    override fun onDestroy() {
        if (isTaskRoot && isFinishing) {
            finishAfterTransition()
        }
        super.onDestroy()
    }
}