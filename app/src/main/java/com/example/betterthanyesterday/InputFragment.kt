package com.example.mymultifragapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.mymultifragapplication.databinding.FragmentInputBinding
import androidx.fragment.app.setFragmentResult




class InputFragment() : Fragment() {

    var text: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentInputBinding.inflate(inflater, container, false)//arguments가 null일때 text를 null로 setting하지 않는다.
        arguments?.let {
            binding.edttext.setText(it.getString("text"))
        }
        val addTextChangedListener = binding.edttext.addTextChangedListener {
            val resBundle = Bundle().apply {
                putString("input", binding.edttext.text.toString())
            }
            setFragmentResult("input_text", resBundle)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(str: String?= null) =
            InputFragment().apply {
                arguments = Bundle().apply {
                    str.let {
                        putString("text", it)
                    }

                }
            }
    }
}