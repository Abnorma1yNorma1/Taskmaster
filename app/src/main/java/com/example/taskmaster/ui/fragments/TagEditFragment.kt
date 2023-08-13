package com.example.taskmaster.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.taskmaster.TaskmasterApp
import com.example.taskmaster.databinding.FragmentTagEditBinding
import com.example.taskmaster.model.ValidateState
import com.example.taskmaster.repository.TagRepository
import com.example.taskmaster.ui.viewModel.TagEditViewModel
import com.example.taskmaster.ui.viewModel.TagEditViewModelFactory

class TagEditFragment : Fragment() {

    private lateinit var binding: FragmentTagEditBinding
    private lateinit var viewModel: TagEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        viewModel = ViewModelProvider(
            this, TagEditViewModelFactory(TagRepository(TaskmasterApp.INSTANCE.database.tagDao()))
        ).get(TagEditViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTagEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tagState.observe(requireActivity()){status ->
            with(binding){
                when(status){
                    ValidateState.FALSE -> {
                        Toast.makeText(requireActivity(), "Data is not valid.", Toast.LENGTH_SHORT).show()
                    }
                    ValidateState.TRUE  -> {
                        tagNameInputEditText.text?.clear()
                    }
                    else  -> {}
                }
            }
        }
        binding.inputButton.setOnClickListener {
            viewModel.editTag(binding.tagNameInputEditText.text?.toString()?:"")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TagEditFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}