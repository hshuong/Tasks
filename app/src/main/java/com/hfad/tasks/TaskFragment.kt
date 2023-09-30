package com.hfad.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hfad.tasks.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao

        val viewModelFactory = TasksViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]

        // dung data binding, binding chinh la layout
        binding.viewModel= viewModel
        // make each view respond to live data
        // changes by adding the following line to the fragment code
        // Dung data binding thi fragment khong can observe nua ma
        // view (layout, binding) se observe cac thuoc tinh live data cua view model
        // de cap nhat chinh view
        binding.lifecycleOwner = viewLifecycleOwner

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}