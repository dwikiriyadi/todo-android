package id.ac.akakom.mobile.todo.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import id.ac.akakom.mobile.todo.data.model.ToDo
import id.ac.akakom.mobile.todo.data.repository.ToDoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TaskViewModel(private val toDoRepository: ToDoRepository): ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    fun getItem(id: Int) = toDoRepository.getItem(id)

    fun delete(toDo: ToDo) = scope.launch(Dispatchers.IO) { toDoRepository.delete(toDo) }

    fun update(toDo: ToDo) = scope.launch(Dispatchers.IO) { toDoRepository.update(toDo) }
}