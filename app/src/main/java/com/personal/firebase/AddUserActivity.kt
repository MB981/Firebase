package com.personal.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.personal.firebase.databinding.ActivityAddUserBinding

class AddUserActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityAddUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}