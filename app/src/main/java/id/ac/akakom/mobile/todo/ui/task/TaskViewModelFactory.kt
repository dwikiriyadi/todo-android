package id.ac.akakom.mobile.todo.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.ac.akakom.mobile.todo.data.repository.ToDoRepository

class TaskViewModelFactory(private val repository: ToDoRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TaskViewModel(repository) as T
    }
}