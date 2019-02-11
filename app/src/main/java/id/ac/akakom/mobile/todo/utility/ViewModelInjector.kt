package id.ac.akakom.mobile.todo.utility

import android.content.Context
import id.ac.akakom.mobile.todo.data.AppDatabase
import id.ac.akakom.mobile.todo.data.repository.ToDoRepository
import id.ac.akakom.mobile.todo.ui.main.MainViewModelFactory
import id.ac.akakom.mobile.todo.ui.task.TaskViewModelFactory

object ViewModelInjector {

    private fun getToDoRepository(context: Context): ToDoRepository {
        return ToDoRepository.getInstance(AppDatabase.getInstance(context).todoDao())
    }

    fun provideMainViewModelFactory(context: Context): MainViewModelFactory {
        val repository = getToDoRepository(context)
        return MainViewModelFactory(repository)
    }

    fun provideTaskViewModelFactory(context: Context): TaskViewModelFactory {
        val repository = getToDoRepository(context)
        return TaskViewModelFactory(repository)
    }
}