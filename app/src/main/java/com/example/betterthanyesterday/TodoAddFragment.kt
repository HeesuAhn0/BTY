package com.example.betterthanyesterday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.betterthanyesterday.databinding.FragmentTodoAddBinding

class TodoAddFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTodoAddBinding.inflate(inflater, container, false)

        val text1 = arguments?.getString("text1")
        val text2 = arguments?.getString("text2")

        binding.edtTitle.setText(text1)
        binding.edtDetail.setText(text2)

        binding.addBtn.setOnClickListener {
            val newTitle = binding.edtTitle.text.toString()
            val newDetail = binding.edtDetail.text.toString()

            if (newTitle.isNotBlank()) {
                val bundle = Bundle().apply {
                    putString("newTitle", newTitle)
                    putString("newDetail", newDetail)
                }

                findNavController().navigate(R.id.action_todoAddFragment_to_todoFragment, bundle)
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(str1: String? = null, str2: String? = null): TodoAddFragment {
            val frag = TodoAddFragment()
            val args = Bundle().apply {
                putString("text1", str1)
                putString("text2", str2)
            }
            frag.arguments = args
            return frag
        }
    }

}