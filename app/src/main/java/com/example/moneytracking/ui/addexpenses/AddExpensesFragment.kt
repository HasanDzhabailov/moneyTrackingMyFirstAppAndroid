package com.example.moneytracking.ui.addexpenses

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moneytracking.*

import com.example.moneytracking.databinding.FragmentAddExpensesBinding
import com.example.moneytracking.di.Injectable

import com.example.moneytracking.utils.*
import javax.inject.Inject


class AddExpensesFragment : Fragment(), Injectable {
@Inject
lateinit var viewModelFactory: ViewModelProvider.Factory
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		// Inflate the layout for this fragment
		val binding: FragmentAddExpensesBinding =
			DataBindingUtil.inflate(inflater, R.layout.fragment_add_expenses, container, false)

		// Create an drop down menu.
		val categoryExpenses = resources.getStringArray(R.array.expenses)
		val adapter =
			ArrayAdapter(requireContext(), R.layout.list_item_category_expensive, categoryExpenses)
		binding.AutoCompleteTextView.setAdapter(adapter)

		// Get a reference to the ViewModel associated with this fragment.
		val addExpensesViewModel: AddExpensesViewModel by viewModels {
			viewModelFactory
		}


		// To use the View Model with data binding, you have to explicitly
		// give the binding object a reference to it.
		binding.addExpensesViewModel = addExpensesViewModel

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

				addExpensesViewModel.addExpenses(categoryExpensesString, sumExpenseString.toLong())

				binding.menu.editText?.text?.clear()
				binding.textSumExpense.text?.clear()

				binding.menu.clearFocus()
				binding.TextFieldSumExpense.clearFocus()
			}
			this.findNavController().navigate(AddExpensesFragmentDirections.actionMoneyTrackerFragmentToHomeFragment())
			it.hideKeyboard()
		}

		return binding.root
	}

}
