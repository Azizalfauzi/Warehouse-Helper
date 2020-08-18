package org.d3ifcool.warehousehelper.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.FragmentEmailBinding

/**
 * A simple [Fragment] subclass.
 */
class EmailFragment : Fragment() {
    private lateinit var binding: FragmentEmailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_email, container, false)

        return binding.root
    }

}
