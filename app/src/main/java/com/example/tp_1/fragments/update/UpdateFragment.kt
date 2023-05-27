package com.example.tp_1.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tp_1.R
import com.example.tp_1.data.User
import com.example.tp_1.data.UserViewModel
import com.example.tp_1.databinding.FragmentUpdateBinding
import com.example.tp_1.fragments.add.AddFragment
import java.lang.Integer.parseInt

class UpdateFragment : Fragment(), MenuProvider {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var binding: FragmentUpdateBinding
    private val userViewModel by viewModels<UserViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(inflater,container,false)


        //
        //Spinner
        setSpinnerData()

        //Sets Current user info
        with(binding) {
            etUpdateNombre.editText?.setText(args.currentUser.firstName)
            etUpdateApellido.editText?.setText(args.currentUser.lastName)
            etUpdateAge.editText?.setText(args.currentUser.age.toString())
        }
        binding.bttnUpdate.setOnClickListener{
            updateItem()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun updateItem() {
        val firstName = binding.etUpdateNombre.editText?.text.toString()
        val lastName = binding.etUpdateApellido.editText?.text.toString()
        val gender = binding.spinnerUpdateGender.selectedItem.toString()
        val edad = parseInt(binding.etUpdateAge.editText?.text.toString())

        if (inputCheck(firstName, lastName)) {
            //Create updated entry
            val updateUser = User(args.currentUser.id, firstName = firstName, lastName = lastName, age = edad,gender = gender)
            userViewModel.updateUser(updateUser)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
            val action =UpdateFragmentDirections.actionUpdateFragmentToListFragment()
            findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Complete all the fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, secondName: String): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(secondName))
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.menu_delete -> {
                deleteUser()
                true
            }

            else -> {
                true
            }
        }
    }
    private fun deleteUser() {
        val dialog = AlertDialog.Builder(requireContext())

        dialog.setPositiveButton("Yes") { _,_ ->
            userViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(), "Successfully removed: ${args.currentUser.firstName}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        dialog.setNegativeButton("No") { _,_ ->
        }

        dialog.setTitle("Delete ${args.currentUser.firstName}")
        dialog.setMessage("Are you sure you want to delete ${args.currentUser.firstName +""+args.currentUser.lastName}")

        dialog.create().show()

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