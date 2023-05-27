package com.example.tp_1.fragments.add

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tp_1.R
import com.example.tp_1.data.User
import com.example.tp_1.data.UserViewModel
import com.example.tp_1.databinding.FragmentAddBinding
import java.lang.Integer.parseInt

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)

        //Spinner
        setSpinnerData()

        //Add function to button
        binding.bttnAdd.setOnClickListener {
            insertDataToDatabase()
        }


        return binding.root
    }

    // Add entry to Database
    private fun insertDataToDatabase() {
        val firstName = binding.etName.editText?.text.toString()
        val lastName = binding.etLastName.editText?.text.toString()
        val edad = parseInt(binding.etAge.editText?.text.toString())
        val gender = binding.spinnerUpdateGender.selectedItem.toString()
        if (inputCheck(firstName, lastName)) {
            //Create entry
            val user = User(id = 0, firstName = firstName, lastName = lastName ,age = edad, gender = gender)
            userViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Complete all the fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, secondName: String): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(secondName))
    }


    private fun setSpinnerData() {
        val generos = resources.getStringArray(R.array.Genre)
        val spinner = binding.spinnerUpdateGender

        if (spinner != null) {
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, generos)
            spinner.adapter = adapter
        }

    }
}

