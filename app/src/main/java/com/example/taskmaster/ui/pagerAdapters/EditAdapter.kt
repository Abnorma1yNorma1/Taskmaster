package com.example.taskmaster.ui.pagerAdapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.taskmaster.ui.activity.EditActivity
import com.example.taskmaster.ui.fragments.PeriodEditFragment
import com.example.taskmaster.ui.fragments.TagEditFragment
import com.example.taskmaster.ui.fragments.TaskEditFragment

class EditAdapter(fragment: EditActivity) :
    FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position){
            0 -> TagEditFragment()
            1 -> TaskEditFragment()
            else -> PeriodEditFragment()
        }
        fragment.arguments = Bundle().apply {
            putInt(POSITION, position)
        }
        return fragment
    }

    companion object{
        const val POSITION = "position"
    }

}