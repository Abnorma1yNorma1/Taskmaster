package com.example.taskmaster.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
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
import java.time.format.DateTimeFormatter
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

        val time = Calendar.getInstance()
        val year = time.get(Calendar.YEAR)
        val month = time.get(Calendar.MONTH)
        val day = time.get(Calendar.DAY_OF_MONTH)
        val datePicker =
            DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    viewModel.setDate(LocalDate.of(year, month+1, dayOfMonth).toEpochDay())
                    binding.dateChosenText.text =
                        String.format("%d/%d/%d", dayOfMonth, month + 1, year)
                }
            }, year, month, day);
        binding.chooseExpirationButton.setOnClickListener { datePicker.show() }
        viewModel.date.observe(viewLifecycleOwner) {
            val format = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            binding.dateChosenText.text = LocalDate.ofEpochDay(it).format(format)

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