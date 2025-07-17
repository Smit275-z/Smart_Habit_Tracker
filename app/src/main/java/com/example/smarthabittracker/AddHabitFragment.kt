package com.example.smarthabittracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AddHabitFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_habit, container, false)

        val editTextName = view.findViewById<EditText>(R.id.editTextHabitName)
        val editTextGoal = view.findViewById<EditText>(R.id.editTextGoal)
        val timePicker = view.findViewById<TimePicker>(R.id.timePicker)
        timePicker.setIs24HourView(true)
        val buttonAdd = view.findViewById<Button>(R.id.buttonAddHabit)

        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString()
            val goal = editTextGoal.text.toString()
            val hour = timePicker.hour
            val minute = timePicker.minute
            val time = String.format("%02d:%02d", hour, minute)

            val newHabit = Habit(name, goal, time)

            // Send back to FirstFragment using FragmentResult
            parentFragmentManager.setFragmentResult(
                "habitRequestKey",
                Bundle().apply {
                    putString("name", newHabit.name)
                    putString("goal", newHabit.goal)
                    putString("time", newHabit.time)
                }
            )

            findNavController().navigateUp()
        }

        return view
    }
}