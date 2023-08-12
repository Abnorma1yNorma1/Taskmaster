package com.example.taskmaster.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.taskmaster.databinding.ActivityEditBinding
import com.example.taskmaster.ui.pagerAdapters.EditAdapter
import com.google.android.material.tabs.TabLayoutMediator

private lateinit var binding: ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var editAdapter: EditAdapter
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        editAdapter = EditAdapter(this)
        viewPager2 = binding.pager
        viewPager2.adapter = editAdapter
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout,viewPager2){
            tab, position ->
            tab.text = when (position){
                0 -> "Tag"
                1 -> "Task"
                else -> "Period"
            }
        }.attach()

    }
}