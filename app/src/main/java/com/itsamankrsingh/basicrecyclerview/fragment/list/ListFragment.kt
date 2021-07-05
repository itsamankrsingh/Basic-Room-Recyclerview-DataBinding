package com.itsamankrsingh.basicrecyclerview.fragment.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.itsamankrsingh.basicrecyclerview.R
import com.itsamankrsingh.basicrecyclerview.UserViewModel
import com.itsamankrsingh.basicrecyclerview.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater)

        adapter = ListAdapter(UserListItemClickListener { user ->
            user.let {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(user)
                findNavController().navigate(action)
            }
        })

        binding.recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        viewModel.readAllData.observe(viewLifecycleOwner, { user ->
            user.let {
                adapter.setData(user)
            }

        })


        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)

        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_everything_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_everything_menu) {
            deleteAllUser()
        }
        return true
    }

    private fun deleteAllUser() {

        val builder = AlertDialog.Builder(requireContext()).apply {
            setPositiveButton("Yes") { _, _ ->
                viewModel.deleteAllUser()
                Toast.makeText(requireContext(), "Deleted All User Successfully :)", Toast.LENGTH_SHORT)
                    .show()

            }
            setNegativeButton("No") { _, _ ->

            }
            setTitle("Delete All Users")
            setMessage("Are you sure you want to delete all users ?")
        }
        builder.create().show()

    }

}