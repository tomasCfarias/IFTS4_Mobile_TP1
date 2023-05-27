package com.example.tp_1.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tp_1.R
import com.example.tp_1.data.UserViewModel
import com.example.tp_1.databinding.FragmentListBinding

class ListFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentListBinding
    private val userViewModel by viewModels<UserViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentListBinding.inflate(inflater, container, false)


        //Menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        //Add button navigation
        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        //RecyclerView
        val adapter = ListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager  = LinearLayoutManager(requireContext())

        val divider =DividerItemDecoration(requireContext(),LinearLayoutManager(requireContext()).orientation)
        binding.recyclerView.addItemDecoration(divider)


        //ADD
        userViewModel.readAllData.observe(viewLifecycleOwner, Observer { users ->
            adapter.setData(users = users)
        })

        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.menu_delete -> {
                deleteAllUsers()
                true
            }

            else -> {
                true
            }
        }
    }
    private fun deleteAllUsers() {

        val dialog = AlertDialog.Builder(requireContext())

        dialog.setPositiveButton("Yes") { _, _ ->
            userViewModel.deleteAllUsers()
            Toast.makeText(requireContext(),
                "Successfully deleted database",
                Toast.LENGTH_SHORT
            ).show()
        }

        dialog.setNegativeButton("No") { _, _ ->
        }

        dialog.setTitle("Delete All Users")
        dialog.setMessage("Are you sure want to delete all users!?")

        dialog.create().show()

    }
}