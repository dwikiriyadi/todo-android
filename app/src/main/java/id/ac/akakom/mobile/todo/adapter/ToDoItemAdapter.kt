package id.ac.akakom.mobile.todo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import id.ac.akakom.mobile.todo.data.model.ToDo
import id.ac.akakom.mobile.todo.databinding.TodoItemAdapterBinding
import id.ac.akakom.mobile.todo.ui.main.MainViewModel
import id.ac.akakom.mobile.todo.R
import java.text.SimpleDateFormat

class ToDoItemAdapter(private val items: List<ToDo>, private val viewModel: MainViewModel) :
    RecyclerView.Adapter<ToDoItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = TodoItemAdapterBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.reminder.visibility = View.GONE

        holder.binding.checkboxText.text = items[position].taskName

        holder.binding.checkbox.isChecked = items[position].status

        holder.binding.removeButton.visibility = if (items[position].status) View.VISIBLE else View.GONE

        holder.binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            items[position].status = isChecked

            viewModel.update(items[position])
        }

        if (items[position].reminder) {
            if (items[position].reminderRepeat != null) {
                val simpleDateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss")
                holder.binding.reminder.visibility = View.VISIBLE
                holder.binding.reminder.text = simpleDateFormat.format(items[position].reminderDate!!.time)
            } else {
                val simpleDateFormat = SimpleDateFormat("EEE, d MMM yyyy")
                holder.binding.reminder.visibility = View.VISIBLE
                holder.binding.reminder.text = simpleDateFormat.format(items[position].reminderDate!!.time)
            }
        }

        holder.binding.root.setOnClickListener {
            if (!items[position].status) {
                val bundle = bundleOf("id" to items[position].id)
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.action_mainFragment_to_taskFragment, bundle)
            }
        }

        holder.binding.removeButton.setOnClickListener {
            viewModel.delete(items[position])
        }
    }

    class ViewHolder(val binding: TodoItemAdapterBinding) : RecyclerView.ViewHolder(binding.root)
}