package com.hfad.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hfad.tasks.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        val adapter = TaskItemAdapter{
            //taskId ->  Toast.makeText(context, "Clicked task $taskId", Toast.LENGTH_SHORT).show()
            // truyen ham can thuc hien khi nguoi dung bam click vao item cua recycler view
            taskId ->  viewModel.onTaskClicked(taskId)
        }
        // Recycler View co id la task_list trong fragment_task
        // => duoc tao 1 thuoc tinh cho binding co ten la taskList: nen se la binding.taskList
        // gan adapter cua recycler view task_list la adapter
        binding.taskList.adapter = adapter

        // viewModel.tasks co dinh nghia tasks = dao.getAll() ket qua la
        // LiveData dang <List<Task>: fun getAll(): LiveData<List<Task>>
        // Fragment quan sat su thay doi cua thuoc tinh tasks cua viewModel
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            it?.let {
                // co thay doi tasks thi gan du lieu tasks cho data cua adapter
                // Trong TaskItemAdapter, thuoc tinh data co dang la List<Task>: var data = listOf<Task>()
                //adapter.data = it
                adapter.submitList(it)
            }
        })

        viewModel.navigateToTask.observe(viewLifecycleOwner, Observer { taskId ->
            taskId?.let {
                // kiem tra taskId? hay chinh la navigateToTask co ko null moi thuc hien
                val action = TaskFragmentDirections
                                .actionTaskFragmentToEditTaskFragment(taskId)
                this.findNavController().navigate(action)
                // vi chi ko null moi thuc hien nen dat navigateToTask tro lai la null
                // sau khi da navigate xong
                viewModel.onTaskNavigated()
            }
        })

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}