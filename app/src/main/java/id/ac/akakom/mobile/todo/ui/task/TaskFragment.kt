package id.ac.akakom.mobile.todo.ui.task

import androidx.appcompat.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation

import id.ac.akakom.mobile.todo.R
import id.ac.akakom.mobile.todo.databinding.TaskFragmentBinding
import id.ac.akakom.mobile.todo.utility.ViewModelInjector
import id.ac.akakom.mobile.todo.data.model.ToDo
import id.ac.akakom.mobile.todo.databinding.DialogDatepickerBinding
import java.text.SimpleDateFormat
import java.util.*

class TaskFragment : Fragment() {

    companion object {
        fun newInstance() = TaskFragment()
    }

    private lateinit var binding: TaskFragmentBinding
    private lateinit var viewModel: TaskViewModel
    private lateinit var navController: NavController
    private lateinit var todo: ToDo
    private lateinit var c: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity!!.title = "Task"

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setHasOptionsMenu(true)

        binding = TaskFragmentBinding.inflate(inflater, container, false)
        val factory = ViewModelInjector.provideTaskViewModelFactory(requireContext())

        viewModel = ViewModelProviders.of(this, factory).get(TaskViewModel::class.java)

        navController = Navigation.findNavController(activity!!, R.id.container)

        viewModel.getItem(arguments!!.getInt("id")).observe(viewLifecycleOwner, Observer { todo ->
            this.todo = todo
            binding.editText.setText(todo.taskName)

            if (todo.reminder) {
                val simpleDateFormat = SimpleDateFormat("EEE, d MMM yyyy")
                binding.dateContainer.visibility = View.VISIBLE
                binding.setReminderButton.visibility = View.GONE
                binding.dateButton.text = simpleDateFormat.format(todo.reminderDate!!.time)
            } else {
                binding.dateContainer.visibility = View.GONE
            }
        })

        setEvent()

        return binding.root
    }

    private fun setEvent() {
        binding.setReminderButton.setOnClickListener {
            dialog()
        }

        binding.dateButton.setOnClickListener {
            dialog()
        }

//        binding.timeButton.setOnClickListener {
//
//        }
//
//        binding.timeRemoveButton.setOnClickListener {
//            binding.reminderContainer.visibility = View.GONE
//        }

        binding.dateRemoveButton.setOnClickListener {
            todo.reminder = false
            todo.reminderDate = Calendar.getInstance()
            binding.setReminderButton.visibility = View.VISIBLE
            binding.dateContainer.visibility = View.GONE
//            binding.reminderContainer.visibility = View.GONE
        }
    }

    private fun dialog() {
        val builder = AlertDialog.Builder(context!!)
        val view = DialogDatepickerBinding.inflate(layoutInflater)

        if (todo.reminder) {
            val day = todo.reminderDate!!.get(Calendar.DAY_OF_MONTH)
            val month = todo.reminderDate!!.get(Calendar.MONTH)
            val year = todo.reminderDate!!.get(Calendar.YEAR)
            view.datepicker.updateDate(year, month, day)
        }

        builder.setView(view.root)
            .setPositiveButton(R.string.save) { _, _ ->
                val day = view.datepicker.dayOfMonth
                val month = view.datepicker.month
                val year = view.datepicker.year

                c = Calendar.getInstance()
                c.set(year, month, day)
                val simpleDateFormat = SimpleDateFormat("EEE, d MMM yyyy")

                todo.reminder = true
                todo.reminderDate = c
                binding.dateButton.text = simpleDateFormat.format(c.time)

                binding.setReminderButton.visibility = View.GONE
                binding.dateContainer.visibility = View.VISIBLE
            }
            .setCancelable(true)
            .create()
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.update -> {
                todo.taskName = binding.editText.text.toString()

                viewModel.update(todo)
                Toast.makeText(context, "Task has been updated", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
            R.id.delete -> {
                viewModel.delete(todo)
                Toast.makeText(context, "Task has been deleted", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
