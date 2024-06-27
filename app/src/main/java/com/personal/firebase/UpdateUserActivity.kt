package com.personal.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.personal.firebase.databinding.ActivityUpdateUserBinding

class UpdateUserActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityUpdateUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}