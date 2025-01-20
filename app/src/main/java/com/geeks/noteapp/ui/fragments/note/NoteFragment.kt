package com.geeks.noteapp.ui.fragments.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeks.notapp.ui.adapters.NoteAdapter
import com.geeks.noteapp.App
import com.geeks.noteapp.R
import com.geeks.noteapp.data.models.NoteModel
import com.geeks.noteapp.databinding.FragmentNoteBinding
import com.geeks.noteapp.ui.interfaces.OnClickItem


class NoteFragment : Fragment(), OnClickItem {

    private lateinit var binding: FragmentNoteBinding
    private val noteAdapter = NoteAdapter(this, this)
    private var isLinearLayout = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListener()
        getData()
    }

    private fun initialize() {
        binding.rvInfo.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
        }
    }

    private fun toggleLayoutManager() {
        isLinearLayout = !isLinearLayout
        binding.rvInfo.layoutManager = if (isLinearLayout) {
            LinearLayoutManager(requireContext())
        } else {
            GridLayoutManager(requireContext(), 2)
        }
        noteAdapter.notifyDataSetChanged()
    }

    private fun setupListener() = with(binding) {
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_noteDetailFragment)
        }

        btnVariations.setOnClickListener {
            toggleLayoutManager()
        }
    }

    private fun getData() {
        App.appDataBase?.noteDao()?.getAll()?.observe(viewLifecycleOwner) { model ->
            noteAdapter.submitList(model)
        }
    }

    override fun onLongClick(noteModel: NoteModel) {
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle("Удалить заметку")
            setPositiveButton("Удалить") { dialog, _ ->
                App.appDataBase?.noteDao()?.deleteNote(noteModel)
            }
            setNegativeButton("Отмена") { dialog, _ ->
                dialog.cancel()
            }
            show()
        }
        builder.create()
    }

    override fun onClick(noteModel: NoteModel) {
        val action = NoteFragmentDirections.actionNoteFragmentToNoteDetailFragment(noteModel.id)
        findNavController().navigate(action)
    }
}