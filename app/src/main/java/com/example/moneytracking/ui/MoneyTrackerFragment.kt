package com.example.moneytracking.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.moneytracking.R
import com.example.moneytracking.databinding.FragmentMoneyTrackerBinding


/**
 * A simple [Fragment] subclass.
 * Use the [AddingSpendingMoney.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoneyTrackerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container:  ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
      val binding:FragmentMoneyTrackerBinding  = FragmentMoneyTrackerBinding.inflate(inflater,container,false)
       val categoryExpenses = resources.getStringArray(R.array.expenses)
        val adapter = ArrayAdapter(requireContext(),R.layout.list_item_category_expensive,categoryExpenses)
        binding.AutoCompleteTextView.setAdapter(adapter)
           return binding.root
    }


}