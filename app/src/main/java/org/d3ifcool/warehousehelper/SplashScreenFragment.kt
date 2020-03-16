package org.d3ifcool.warehousehelper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import org.d3ifcool.warehousehelper.databinding.FragmentSplashScreenBinding

/**
 * A simple [Fragment] subclass.
 */
class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentSplashScreenBinding>(
            inflater,
            R.layout.fragment_splash_screen,
            container,
            false
        )
        binding.btStart.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_splashScreenFragment_to_onboardingSatu)
        }
        return binding.root
    }

}
