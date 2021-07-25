package com.anggit97.albumin

import android.os.Bundle
import com.anggit97.albumin.databinding.ActivityHomeBinding
import com.anggit97.core.base.BaseActivity
import com.anggit97.core.util.viewBindings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val binding by viewBindings(ActivityHomeBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}