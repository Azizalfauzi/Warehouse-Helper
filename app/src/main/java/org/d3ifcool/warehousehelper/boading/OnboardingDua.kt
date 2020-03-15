package org.d3ifcool.warehousehelper.boading

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.FragmentOnboardingDuaBinding

/**
 * A simple [Fragment] subclass.
 */
class OnboardingDua : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentOnboardingDuaBinding>(
            inflater,
            R.layout.fragment_onboarding_dua, container, false
        )
        binding.btnNextdua.setOnClickListener { view: View ->
            view.findNavController().navigate(
                R.id.action_onboardingDua_to_onboardingTiga
            )
        }
        return binding.root
    }

}
