package com.example.smarthabittracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class FirstFragment : Fragment() {

    private lateinit var adapter: HabitAdapter
    private val habitList = mutableListOf<Habit>()  // <- Make the list mutable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewHabits)

        adapter = HabitAdapter()
        recyclerView.adapter = adapter

        adapter.submitList(habitList.toList())

        // Listen for result from AddHabitFragment
        parentFragmentManager.setFragmentResultListener(
            "habitRequestKey",
            viewLifecycleOwner
        ) { _, bundle ->
            val name = bundle.getString("name") ?: ""
            val goal = bundle.getString("goal") ?: ""
            val time = bundle.getString("time") ?: ""

            val newHabit = Habit(name, goal, time)
            habitList.add(newHabit)
            adapter.submitList(habitList.toList())
        }

        return view
    }
}