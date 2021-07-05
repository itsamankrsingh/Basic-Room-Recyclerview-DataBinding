package com.itsamankrsingh.basicrecyclerview.fragment.update

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.itsamankrsingh.basicrecyclerview.R
import com.itsamankrsingh.basicrecyclerview.UserViewModel
import com.itsamankrsingh.basicrecyclerview.data.User
import com.itsamankrsingh.basicrecyclerview.databinding.FragmentUpdateBinding


class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var viewModel: UserViewModel

    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.editTextUpdateFirstName.setText(args.user.firstName)
        binding.editTextUpdateLastName.setText(args.user.lastName)
        binding.editTextUpdateAge.setText(args.user.age.toString())

        binding.updateButton.setOnClickListener {
            updateUser()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun updateUser() {
        val firstName = binding.editTextUpdateFirstName.text.toString()
        val lastName = binding.editTextUpdateLastName.text.toString()
        val age = binding.editTextUpdateAge.text

        if (binding.editTextUpdateAge.text.isEmpty()) {
            binding.editTextUpdateAge.error = "Enter the Age"
        } else if (binding.editTextUpdateFirstName.text.isEmpty()) {
            binding.editTextUpdateFirstName.error = "Enter the first Name"
        } else if (binding.editTextUpdateLastName.text.isEmpty()) {
            binding.editTextUpdateLastName.error = "Enter the Last Name"
        } else {
            val user = User(args.user.id, firstName, lastName, Integer.parseInt(age.toString()))
            viewModel.updateUser(user)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            Toast.makeText(requireContext(), "Updated Successfully :)", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_menu) {
            deleteUser()
        }
        return true
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext()).apply {
            setPositiveButton("Yes") { _, _ ->
                viewModel.deleteUser(args.user)
                Toast.makeText(requireContext(), "Deleted Successfully :)", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            setNegativeButton("No") { _, _ ->

            }
            setTitle("Delete ${args.user.firstName}")
            setMessage("Are you sure you want to delete ${args.user.firstName} ?")
        }
        builder.create()
        builder.show()

    }

}