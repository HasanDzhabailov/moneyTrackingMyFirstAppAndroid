package com.example.moneytracking.ui

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moneytracking.R
import com.example.moneytracking.database.MoneyTrackDatabase
import com.example.moneytracking.databinding.FragmentMoneyTrackerBinding
import com.example.moneytracking.viewmodels.MoneyTrackerViewModel
import com.example.moneytracking.viewmodels.MoneyTrackerViewModelFactory


class MoneyTrackerFragment : Fragment() {
    fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentMoneyTrackerBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_money_tracker, container, false)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = MoneyTrackDatabase.getInstance(application).moneyTrackDatabaseDao
        val viewModelFactory = MoneyTrackerViewModelFactory(dataSource, application)

        // Create an drop down menu.
        val categoryExpenses = resources.getStringArray(R.array.expenses)
        val adapter =
            ArrayAdapter(requireContext(), R.layout.list_item_category_expensive, categoryExpenses)
        binding.AutoCompleteTextView.setAdapter(adapter)

        // Get a reference to the ViewModel associated with this fragment.
        val moneyTrackViewModel =
            ViewModelProvider(this, viewModelFactory).get(MoneyTrackerViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.moneyTrackViewModel = moneyTrackViewModel

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this

        binding.btnAddExpenses.setOnClickListener {
            var categoryExpensesString = binding.menu.editText!!.text.toString()
            var sumExpenseString = binding.textSumExpense.text.toString()

            if (TextUtils.isEmpty(categoryExpensesString)) {
                binding.menu.error = "Заполните поле"
            } else if (TextUtils.isEmpty(sumExpenseString) || sumExpenseString == "0") {
                binding.TextFieldSumExpense.error = "Заполните поле"
            } else {
                binding.menu.isErrorEnabled = false
                binding.TextFieldSumExpense.isErrorEnabled = false

                moneyTrackViewModel.addExpenses(categoryExpensesString, sumExpenseString.toLong())

                binding.menu.editText?.text?.clear()
                binding.textSumExpense.text?.clear()

                binding.menu.clearFocus()
                binding.TextFieldSumExpense.clearFocus()
            }

            it.hideKeyboard()
        }

        return binding.root
    }
}
