package id.ac.akakom.mobile.todo.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.akakom.mobile.todo.databinding.MainFragmentBinding
import id.ac.akakom.mobile.todo.data.model.*
import id.ac.akakom.mobile.todo.adapter.ToDoItemAdapter
import id.ac.akakom.mobile.todo.utility.ViewModelInjector

class MainFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity!!.title = "To Do List"

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        binding = MainFragmentBinding.inflate(inflater, container, false)

        val factory = ViewModelInjector.provideMainViewModelFactory(requireContext())

        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        viewModel.getAllItems.observe(viewLifecycleOwner, Observer {todos ->
            if (todos != null && todos.isNotEmpty()) {
                binding.recyclerView.adapter = ToDoItemAdapter(todos, viewModel)
                binding.recyclerView.visibility = View.VISIBLE
                binding.message.visibility = View.GONE
            } else {
                binding.recyclerView.visibility = View.GONE
                binding.message.visibility = View.VISIBLE
            }
        })

        binding.addButton.setOnClickListener {
            if (!binding.editText.text.isNullOrEmpty()) {
                val todo = ToDo(taskName = binding.editText.text.toString())

                viewModel.insert(todo)
            }
        }

        return binding.root
    }
}
