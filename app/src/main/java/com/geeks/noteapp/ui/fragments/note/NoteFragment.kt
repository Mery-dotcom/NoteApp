package com.geeks.noteapp.ui.fragments.note

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeks.notapp.ui.adapters.NoteAdapter
import com.geeks.noteapp.R
import com.geeks.noteapp.data.models.NoteModel
import com.geeks.noteapp.databinding.FragmentNoteBinding
import com.geeks.noteapp.presenter.noteList.NoteContract
import com.geeks.noteapp.presenter.noteList.NotePresenter
import com.geeks.noteapp.ui.interfaces.OnClickItem

class NoteFragment : Fragment(), OnClickItem, NoteContract.View {

    private lateinit var binding: FragmentNoteBinding
    private val noteAdapter = NoteAdapter(this, this)
    private var isLinearLayout = true
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    private val presenter by lazy { NotePresenter(this) }

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
//        getData()
        setupDrawer()
        presenter.loadNotes()
    }

    private fun initialize() {
        binding.rvInfo.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
        }
        drawerLayout = binding.drawerLayout
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
            drawerLayout.closeDrawers()
            findNavController().navigate(R.id.action_noteFragment_to_noteDetailFragment)
        }
        store.setOnClickListener {
            drawerLayout.closeDrawers()
            findNavController().navigate(R.id.storeFragment)
        }

        btnVariations.setOnClickListener {
            drawerLayout.closeDrawers()
            toggleLayoutManager()
        }

        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

//    private fun getData() {
//        App.appDataBase?.noteDao()?.getAll()?.observe(viewLifecycleOwner) { model ->
//            noteAdapter.submitList(model)
//        }
//    }

    private fun setupDrawer() {
        actionBarDrawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            drawerLayout,
            R.string.nav_open,
            R.string.nav_close
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        binding.navView.setNavigationItemSelectedListener { navMenu ->
            when (navMenu.itemId) {
                R.id.nav_first_folder -> findNavController().navigate(R.id.firstFolderFragment)
                R.id.nav_store -> findNavController().navigate(R.id.storeFragment)
                R.id.nav_second_folder -> findNavController().navigate(R.id.secondFolderFragment)
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onLongClick(noteModel: NoteModel) {
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle("Удалить заметку")
            setPositiveButton("Удалить") { dialog, _ ->
                presenter.deleteNote(noteModel)
                dialog.dismiss()
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

    override fun showNotes(notes: List<NoteModel>) {
        noteAdapter.submitList(notes)
        noteAdapter.notifyDataSetChanged()
    }

    override fun showError(message: String) {
        Log.e("NotesFragment", message)
    }
}