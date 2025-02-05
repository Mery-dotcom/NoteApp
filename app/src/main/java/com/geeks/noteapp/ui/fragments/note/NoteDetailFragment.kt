package com.geeks.noteapp.ui.fragments.note

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.geeks.noteapp.App
import com.geeks.noteapp.R
import com.geeks.noteapp.data.models.NoteModel
import com.geeks.noteapp.databinding.FragmentNoteDetailBinding
import com.geeks.noteapp.presenter.noteDetail.NoteDetailContract
import com.geeks.noteapp.presenter.noteDetail.NoteDetailPresenter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteDetailFragment : Fragment(), NoteDetailContract.View {

    private lateinit var binding: FragmentNoteDetailBinding
    private var selectedColor: Int = Color.WHITE

    private val presenter by lazy { NoteDetailPresenter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
        presenter.getCurrentDateTime()
        presenter.loadNote(arguments)
    }

    private fun setupListener() = with(binding){
        btnAdd.setOnClickListener{
            presenter.onSaveClicked(
            etTitle.text.toString(),
            etDescription.text.toString(),
            txtDate.text.toString(),
            txtTime.text.toString(),
                selectedColor)
        }

        btnBack.setOnClickListener{
            presenter.onBackClicked()
        }

        btnChooseColor.setOnClickListener{
            chooseColorPicker()
        }

        colorYellow.setOnClickListener { selectColor(Color.parseColor("#F6E360")) }
        colorPurple.setOnClickListener { selectColor(Color.parseColor("#ED5A8A")) }
        colorPink.setOnClickListener { selectColor(Color.parseColor("#DD68EF")) }
        colorRed.setOnClickListener { selectColor(Color.parseColor("#E26259")) }
        colorGreen.setOnClickListener { selectColor(Color.parseColor("#67BF6B")) }
        colorBlue.setOnClickListener { selectColor(Color.parseColor("#51A7EB")) }
    }

    private fun chooseColorPicker() {
        binding.colorPickerContainer.visibility =
            if (binding.colorPickerContainer.visibility == View.GONE) View.VISIBLE else View.GONE
    }

    private fun selectColor(color: Int) {
        selectedColor = color
        binding.colorPickerContainer.visibility = View.GONE
        Toast.makeText(requireContext(), "Цвет выбран", Toast.LENGTH_SHORT).show()
    }

    override fun showSaved(note: NoteModel) {
    }

    override fun showError(message: String) {
        Log.e("NoteDetailFragment", message)
    }

    override fun noteUpdated() {
    }

    override fun showNote(note: NoteModel) {
        binding.etTitle.setText(note.title)
        binding.etDescription.setText(note.description)
        selectedColor = note.color!!
    }

    override fun showDateTime(date: String, time: String) {
        binding.txtDate.text = date
        binding.txtTime.text = time
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateBack() {
        findNavController().navigateUp()
    }
}