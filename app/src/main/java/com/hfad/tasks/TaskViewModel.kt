package com.hfad.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(val dao : TaskDao) : ViewModel(){
    //var newTaskName =""
    var newTaskName = MutableLiveData<String>("")

    val tasks = dao.getAll()

    private val _navigateToTask = MutableLiveData<Long?>()
    val navigateToTask : LiveData<Long?>
        get() = _navigateToTask

    // ham .map returns a new LiveData object voi kieu cua ham formatTasks tra ve
    // o day formatTasks tra ve la 1 String, nen .map wrap String lai bang LiveData
    // va tra ve la 1 LiveData<String>
//    val tasksString = tasks.map {
//        tasks -> formatTasks(tasks)
//    }

    fun addTask() {
        // launch the coroutine in the same scope as the view model
        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskName.value.toString()
            dao.insert(task)
        }
        newTaskName.value = ""
    }

    fun onTaskClicked(taskId: Long) {
        _navigateToTask.value = taskId
    }

    fun onTaskNavigated() {
        _navigateToTask.value = null
    }


//    private fun formatTasks(tasks: List<Task>) : String {
//        return tasks.fold("") {
//            str, item -> str + " (a task) \n" + formatTask(item)
//        }
//    }
//
//    private fun formatTask(task: Task): String {
//        var str = "ID: ${task.taskId}"
//        str += '\n' +  "Name: ${task.taskName}"
//        str += '\n' +  "Complete: ${task.taskDone}" + '\n'
//        return str
//    }


}