package com.example.smarthabittracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class FirstFragment : Fragment() {

    private lateinit var adapter: HabitAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewHabits)

        adapter = HabitAdapter()
        recyclerView.adapter = adapter

        // Sample data
        val habits = listOf(
            Habit("Exercise", "30 mins daily", "7:00 AM"),
            Habit("Meditate", "10 mins daily", "8:00 AM"),
            Habit("Read Book", "20 pages", "9:00 PM")
        )

        adapter.submitList(habits)

        return view
    }
}