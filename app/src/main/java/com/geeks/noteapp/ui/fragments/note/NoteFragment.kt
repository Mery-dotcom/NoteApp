package com.geeks.noteapp.ui.fragments.note

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.geeks.noteapp.sealed.NoteState
import com.geeks.noteapp.ui.interfaces.OnClickItem
import java.util.Locale

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
        fabAdd.setOnClickListener { presenter.onAddClicked() }
        store.setOnClickListener { presenter.onStoreClicked() }
        btnVariations.setOnClickListener { toggleLayoutManager() }
        btnMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
    }


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
            presenter.onDrawerItemClicked(navMenu.itemId)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }

    override fun onLongClick(noteModel: NoteModel) {
        presenter.onNoteLongClicked(noteModel)
//        val builder = AlertDialog.Builder(requireContext())
//        with(builder) {
//            setTitle("Удалить заметку")
//            setPositiveButton("Удалить") { dialog, _ ->
//                presenter.deleteNote(noteModel)
//                dialog.dismiss()
//            }
//            setNegativeButton("Отмена") { dialog, _ ->
//                dialog.cancel()
//
//            }
//            show()
//        }
//        builder.create()
    }

    override fun onClick(noteModel: NoteModel) {
        presenter.onNoteClicked(noteModel)
    }

    override fun showNotes(notes: List<NoteModel>) {
        noteAdapter.submitList(notes)
        noteAdapter.notifyDataSetChanged()
    }

    override fun showError(message: String) {
        Log.e("NotesFragment", message)
    }

    override fun showNoteState(state: NoteState) {
        when (state) {
            is NoteState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is NoteState.Success -> {
                binding.progressBar.visibility = View.GONE
                noteAdapter.submitList(state.notes)
                noteAdapter.notifyDataSetChanged()
            }
            is NoteState.Error -> {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun navigateToNoteDetail(noteId: Int?) {
        val action = NoteFragmentDirections.actionNoteFragmentToNoteDetailFragment(noteId ?: -1)
        findNavController().navigate(action)
    }

    override fun navigateToStore() {
        findNavController().navigate(R.id.storeFragment)
    }

    override fun navigateToFragment(fragmentId: Int) {
        findNavController().navigate(fragmentId)
    }

    override fun showDeleteDialog(note: NoteModel) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.delete_note)
            .setPositiveButton(R.string.delete) { dialog, _ ->
                presenter.deleteNote(note)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

//    fun setLocale(languageCode: String) {
//        val locale = Locale(languageCode)
//        Locale.setDefault(locale)
//        val config = resources.configuration
//        config.setLocale(locale)
//        resources.updateConfiguration(config, resources.displayMetrics)
//
//        val language = "ru"
//        setLocale(language)
//    }
}