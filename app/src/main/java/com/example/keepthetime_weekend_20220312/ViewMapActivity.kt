package com.example.keepthetime_weekend_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.keepthetime_weekend_20220312.databinding.ActivityViewMapBinding

class ViewMapActivity : BaseActivity() {

    lateinit var binding : ActivityViewMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_map)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {
    }

    override fun setValues() {
    }
}