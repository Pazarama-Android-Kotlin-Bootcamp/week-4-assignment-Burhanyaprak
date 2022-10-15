package com.burhanyaprak.patikaweatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.burhanyaprak.patikaweatherapp.databinding.FragmentLoginApiKeyBinding

class LoginApiKeyFragment : Fragment() {
    private var _binding: FragmentLoginApiKeyBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginApiKeyBinding.inflate(layoutInflater, container, false)

        binding.buttonLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginApiKeyFragment_to_weatherFragment)
        }

        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}