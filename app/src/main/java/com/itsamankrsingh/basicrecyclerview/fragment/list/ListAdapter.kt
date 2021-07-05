package com.itsamankrsingh.basicrecyclerview.fragment.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itsamankrsingh.basicrecyclerview.R
import com.itsamankrsingh.basicrecyclerview.data.User
import com.itsamankrsingh.basicrecyclerview.databinding.UserItemBinding

class ListAdapter(private val clickListener: UserListItemClickListener) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    class MyViewHolder(private var binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, clickListener: UserListItemClickListener) {
            binding.firstNameTv.text = user.firstName
            binding.lastNameTv.text = user.lastName
            binding.ageTv.text = user.age.toString()
            binding.itemIdTv.text = user.id.toString()

            binding.user = user
            binding.clicklistener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.bind(currentItem, clickListener)

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(user: List<User>) {
        userList = user
        notifyDataSetChanged()
    }
}

class UserListItemClickListener(val clickListener: (user: User) -> Unit) {
    fun onClick(user: User) = clickListener(user)
}