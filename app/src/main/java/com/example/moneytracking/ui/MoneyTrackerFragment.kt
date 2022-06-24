package com.example.moneytracking.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.moneytracking.R
import com.example.moneytracking.database.MoneyTrackDatabase
import com.example.moneytracking.databinding.FragmentMoneyTrackerBinding
import com.example.moneytracking.viewmodels.MoneyTrackerViewModel
import com.example.moneytracking.viewmodels.MoneyTrackerViewModelFactory


class MoneyTrackerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container:  ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding:FragmentMoneyTrackerBinding  = DataBindingUtil.inflate(
          inflater,R.layout.fragment_money_tracker,container,false)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = MoneyTrackDatabase.getInstance(application).moneyTrackDatabaseDao
        val viewModelFactory = MoneyTrackerViewModelFactory(dataSource, application)

        //Create an drop down menu.
        val categoryExpenses = resources.getStringArray(R.array.expenses)
        val adapter = ArrayAdapter(requireContext(),R.layout.list_item_category_expensive,categoryExpenses)
        binding.AutoCompleteTextView.setAdapter(adapter)

        val moneyTrackViewModel = ViewModelProvider(
            this,viewModelFactory).get(MoneyTrackerViewModel::class.java)
        binding.moneyTrackViewModel = moneyTrackViewModel
        binding.lifecycleOwner = this


        return binding.root
    }


}