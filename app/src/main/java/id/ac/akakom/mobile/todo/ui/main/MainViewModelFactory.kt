package id.ac.akakom.mobile.todo.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.ac.akakom.mobile.todo.data.repository.ToDoRepository

class MainViewModelFactory(private val repository: ToDoRepository):ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}