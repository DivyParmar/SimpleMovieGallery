package com.myapp.simplegallery.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.myapp.simplegallery.R
import com.myapp.simplegallery.databinding.LootieProgressBarBinding


class LootieProgreeFragment: DialogFragment() {
    private lateinit var binding: LootieProgressBarBinding
    companion object {
        private var instance: LootieProgreeFragment? = null
        fun newInstance(): LootieProgreeFragment {
            if (instance ==null){
                instance = LootieProgreeFragment()
            }
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.transparentDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= LootieProgressBarBinding.inflate(inflater)
        return binding.root
    }
}