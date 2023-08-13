package com.example.taskmaster.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProvider
import com.example.taskmaster.R
import com.example.taskmaster.TaskmasterApp
import com.example.taskmaster.databinding.FragmentTaskEditBinding
import com.example.taskmaster.model.Tag
import com.example.taskmaster.repository.TagRepository
import com.example.taskmaster.repository.TaskRepository
import com.example.taskmaster.ui.viewModel.TaskEditViewModel
import com.example.taskmaster.ui.viewModel.TaskEditViewModelFactory
import com.google.android.material.chip.Chip
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAmount
import java.time.temporal.TemporalField
import java.util.Calendar


class TaskEditFragment : Fragment() {

    private lateinit var binding: FragmentTaskEditBinding
    private lateinit var viewModel: TaskEditViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        viewModel = ViewModelProvider(
            this, TaskEditViewModelFactory(
                TagRepository(TaskmasterApp.INSTANCE.database.tagDao()),
                TaskRepository(TaskmasterApp.INSTANCE.database.taskDao())
            )
        ).get(TaskEditViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tagList.observe(viewLifecycleOwner) { listEntry ->
            viewModel.removeAllChosenTags()
            binding.chipGroup.removeAllViews()
            listEntry.forEach {
                binding.chipGroup.addView(createChip(it))
            }
        }

        val date = Calendar.getInstance()
        val year = date.get(Calendar.YEAR)
        val month = date.get(Calendar.MONTH)
        val day = date.get(Calendar.DAY_OF_MONTH)
        val datePicker =
            DatePickerDialog(requireContext(),
                { view, year, month, dayOfMonth ->
                    viewModel.setDate(LocalDate.of(year, month + 1, dayOfMonth).toEpochDay())
//                    binding.dateChosenText.text =
//                        String.format("%d/%d/%d", dayOfMonth, month + 1, year)
                }, year, month, day
            )
        binding.chooseExpirationButton.setOnClickListener { datePicker.show() }
        viewModel.date.observe(viewLifecycleOwner) {
            val format = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            binding.dateChosenText.text = LocalDate.ofEpochDay(it).format(format)

        }
        val timePicker: TimePickerDialog
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)
        timePicker =
            TimePickerDialog(requireContext(),
                { view, hourOfDay, minute ->
                    viewModel.setTime(LocalTime.of(hourOfDay,minute).toSecondOfDay().toLong())
//                    binding.timeChosenText.text = String.format("%d : %d", hourOfDay, minute)
                }, hour, minute, false)
        binding.chooseNotifyTimeButton.setOnClickListener { timePicker.show() }
        viewModel.time.observe(viewLifecycleOwner){
            val timer = LocalTime.ofSecondOfDay(it)
            binding.timeChosenText.text = String.format("%d:%d",timer.hour,timer.minute)
        }
}

private fun createChip(tag: Tag): Chip {
    val chip = Chip(context, null, R.layout.item_tag_chip)
    chip.text = tag.tagName
    chip.isCheckable = true
    chip.isClickable = true
    chip.setOnCheckedChangeListener { buttonView, checked ->
        if (checked) {
            viewModel.addChosenTag(tag.id)
            buttonView.isChecked = true
        } else {
            viewModel.removeChosenTag(tag.id)
            buttonView.isChecked = false
        }
    }
    return chip
}

companion object {
    @JvmStatic
    fun newInstance() =
        TaskEditFragment().apply {
            arguments = Bundle().apply {
            }
        }
}
}