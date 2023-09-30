package com.hfad.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(val dao : TaskDao) : ViewModel(){
    var newTaskName =""
    fun addTask() {
        // launch the coroutine in the same scope as the view model
        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskName
            dao.insert(task)
        }
    }
}