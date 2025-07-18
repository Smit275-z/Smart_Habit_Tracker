package com.example.smarthabittracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class FirstFragment : Fragment() {

    private val FILE_NAME = "habits.txt"

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
            saveHabitsToFile()
        }

        return view
    }

    private fun saveHabitsToFile() {
        val fileOutput = requireContext().openFileOutput(FILE_NAME, android.content.Context.MODE_PRIVATE)
        fileOutput.bufferedWriter().use { writer ->
            for (habit in habitList) {
                writer.write("${habit.name},${habit.goal},${habit.time}")
                writer.newLine()
            }
        }
    }

    private fun loadHabitsFromFile() {
        val file = requireContext().getFileStreamPath(FILE_NAME)
        if (!file.exists()) return

        val loadedHabits = mutableListOf<Habit>()
        file.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val parts = line.split(",")
                if (parts.size == 3) {
                    val habit = Habit(parts[0], parts[1], parts[2])
                    loadedHabits.add(habit)
                }
            }
        }

        habitList.clear()
        habitList.addAll(loadedHabits)
        adapter.submitList(habitList.toList())
    }
}