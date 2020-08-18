package org.d3ifcool.warehousehelper.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.FragmentOnboardingNolBinding

/**
 * A simple [Fragment] subclass.
 */
class OnboardingNol : Fragment() {
    private lateinit var binding: FragmentOnboardingNolBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_onboarding_nol, container, false)
        binding.btnNextnol.setOnClickListener {
            view:View->view.findNavController().navigate(R.id.action_onboardingNol_to_onboardingSatu)
        }
        binding.btnGetstarted.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_onboardingNol_to_loginActivity)
        }
        return binding.root
    }

}
