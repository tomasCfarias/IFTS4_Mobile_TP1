package com.example.tp_1.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_1.data.User
import com.example.tp_1.databinding.ItemListAdapterBinding

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>()  {

    inner class MyViewHolder(val binding: ItemListAdapterBinding) :RecyclerView.ViewHolder(binding.root) {

    }

    private var userList = emptyList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =ItemListAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding = binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]

        with(holder.binding) {
            id.text = currentItem.id.toString()
            name.text = currentItem.firstName
            lastname.text = currentItem.lastName
            age.text = currentItem.age.toString()
            gender.text = currentItem.gender

            rowLayoutIdFrag.setOnClickListener{

                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                holder.itemView.findNavController().navigate(action)
            }
        }
    }

    fun setData(users: List<User>) {
        this.userList = users
        notifyDataSetChanged()
    }

}