package com.geeks.noteapp.ui.fragments.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeks.noteapp.R
import com.geeks.noteapp.databinding.FragmentStoreBinding
import com.geeks.noteapp.ui.adapters.StoreAdapter
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StoreFragment : Fragment() {

    private lateinit var binding: FragmentStoreBinding
    private val storeAdapter = StoreAdapter()
    private val db = Firebase.firestore
    private lateinit var query: Query
    private lateinit var listener: ListenerRegistration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListeners()
        observeStore()
    }

    private fun initialize() {
        binding.rvStore.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = storeAdapter
        }
    }

    private fun setupListeners() = with(binding) {
        btnStore.setOnClickListener {
            val user = hashMapOf(
                "name" to etStore.text.toString()
            )
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    etStore.text?.clear()
            }
        }
    }

    private fun observeStore() {
        query = db.collection("users")
        listener = query.addSnapshotListener { value, error ->
            if (error != null){
                return@addSnapshotListener
            }

            value?.let { data ->
                val dataList = mutableListOf<String>()
                for (doc in data.documents){
                    val valueStore = doc.getString("name")
                    valueStore?.let { value->
                        dataList.add(value)
                    }
                }
                storeAdapter.submitList(dataList)
            }
        }
    }
}