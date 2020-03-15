package org.d3ifcool.warehousehelper.boading

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.FragmentOnboardingTigaBinding

/**
 * A simple [Fragment] subclass.
 */
class OnboardingTiga : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentOnboardingTigaBinding>(
            inflater,
            R.layout.fragment_onboarding_tiga,
            container,
            false
        )

        return binding.root
    }

}
