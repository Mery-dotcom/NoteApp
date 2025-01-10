package com.geeks.noteapp.ui.fragments.onboard

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geeks.noteapp.R
import com.geeks.noteapp.databinding.FragmentOnBoardPagerBinding

class OnBoardPagerFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    @SuppressLint("SetTextI18n")
    private fun initialize() = with(binding){
        when (requireArguments().getInt(ARG_ONBOARD_POSITION)){
            0 -> {
                txtOnboardTitle.text = "Удобство"
                txtOnboardDescription.text = "Создавайте заметки в два клика! " +
                        "\nЗаписывайте мысли, идеи и " +
                        "\nважные задачи мгновенно."
                animation.setAnimation(R.raw.first_animation)
            }
            1 -> {
                txtOnboardTitle.text = "Организация"
                txtOnboardDescription.text = "Организуйте заметки по папкам " +
                        "\nи тегам. Легко находите нужную " +
                        "\nинформацию в любое время."
                animation.setAnimation(R.raw.second_animation)
            }
            2 -> {
                txtOnboardTitle.text = "Синхронизация"
                txtOnboardDescription.text = "Синхронизация на всех " +
                        "\nустройствах. Доступ к записям в " +
                        "\nлюбое время и в любом месте."
                animation.setAnimation(R.raw.third_animation)
            }
        }
    }

    companion object{
        const val ARG_ONBOARD_POSITION = "onBoard"
    }
}