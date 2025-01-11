package com.geeks.noteapp.ui.fragments.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.geeks.noteapp.databinding.FragmentOnBoardBinding
import com.geeks.noteapp.ui.adapters.OnBoardAdapter

class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListeners()
    }

    private fun initialize() {
        binding.viewPager.adapter = OnBoardAdapter(this)

        binding.dotsIndicator.setViewPager2(binding.viewPager)

        binding.viewPager.setPageTransformer{ page, position ->
            page.alpha = 0.25f + (1 - Math.abs(position)) * 0.75f
            page.translationX = -position * page.width * 0.5f
        }
    }

    private fun setupListeners() = with(binding.viewPager){
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) = with(binding) {
                super.onPageSelected(position)
                when (position) {
                    0,1 -> {
                        button.animate().alpha(0f).setDuration(300).withEndAction{
                        button.visibility = View.INVISIBLE
                        }.start()
                    }
                    2 -> {
                        button.visibility = View.VISIBLE
                        button.alpha = 0f
                        button.animate().alpha(1f).setDuration(500).start()
                    }
                }
                if (position == 2){
                    txtSkip.visibility = View.INVISIBLE
                }else{
                    txtSkip.visibility = View.VISIBLE
                    txtSkip.setOnClickListener {
                            setCurrentItem(currentItem + 2, true)
                    }
                }
            }
        })
    }
}