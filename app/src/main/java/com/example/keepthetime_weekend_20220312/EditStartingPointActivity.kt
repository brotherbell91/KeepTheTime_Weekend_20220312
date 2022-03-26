package com.example.keepthetime_weekend_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.keepthetime_weekend_20220312.databinding.ActivityEditStartingPointBinding

class EditStartingPointActivity : BaseActivity() {

    lateinit var binding: ActivityEditStartingPointBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_starting_point)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnSave.setOnClickListener {

            val inputName = binding.edtStartingPointName.text.toString()

            if (inputName.isEmpty()) {

                Toast.makeText(mContext, "출발지 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }

        }

    }

    override fun setValues() {

    }
}