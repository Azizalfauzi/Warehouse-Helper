package org.d3ifcool.warehousehelper.boading

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.FragmentOnboardingSatuBinding

/**
 * A simple [Fragment] subclass.
 */
class OnboardingSatu : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentOnboardingSatuBinding>(
            inflater,
            R.layout.fragment_onboarding_satu,
            container,
            false
        )
        binding.btnNextsatu.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_onboardingSatu_to_onboardingDua)
        }
        binding.btnSkipsatu.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_onboardingSatu_to_loginActivity)
        }
        return binding.root
    }
}
