package com.example.taskmaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskmaster.databinding.ActivityMainBinding
import com.example.taskmaster.repository.TaskRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//    private lateinit var taskRepository: TaskRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        taskRepository = TaskRepository(TaskmasterApp.INSTANCE.database.taskDao())
//        TaskmasterApp.INSTANCE.database.taskDao().incert()
    }
}