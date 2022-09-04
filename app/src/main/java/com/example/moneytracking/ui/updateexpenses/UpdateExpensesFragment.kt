package com.example.moneytracking.ui.updateexpenses

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moneytracking.R


import com.example.moneytracking.databinding.FragmentUpdateExpensesBinding
import com.example.moneytracking.di.Injectable
import com.example.moneytracking.utils.autoCleared

import com.example.moneytracking.utils.hideKeyboard
import javax.inject.Inject


class UpdateExpensesFragment : Fragment(), Injectable {
	private val args by navArgs<UpdateExpensesFragmentArgs>()

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	private var binding by autoCleared<FragmentUpdateExpensesBinding>()
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		binding =
			DataBindingUtil.inflate(inflater, R.layout.fragment_update_expenses, container, false)

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val updateExpensesViewModel: UpdateExpensesViewModel by viewModels {
			viewModelFactory
		}
		binding.updateExpensesViewModel = updateExpensesViewModel
		binding.lifecycleOwner = this
		// Create an drop down menu.
		binding.AutoCompleteTextView.setText(args.currentExpenses.categoryExpense)
		val categoryExpenses = resources.getStringArray(R.array.expenses)
		val adapter =
			ArrayAdapter(requireContext(), R.layout.list_item_category_expensive, categoryExpenses)
		binding.AutoCompleteTextView.setAdapter(adapter)

		binding.textSumExpense.setText(args.currentExpenses.sumExpense.toString())
		binding.btnUpdateExpenses.setOnClickListener {
			val categoryExpensesString = binding.AutoCompleteTextView.text.toString()
			val sumExpenseString = binding.textSumExpense.text.toString()

			if (TextUtils.isEmpty(categoryExpensesString)) {
				binding.menu.error = "Заполните поле"
			} else if (TextUtils.isEmpty(sumExpenseString) || sumExpenseString == "0") {
				binding.TextFieldSumExpense.error = "Заполните поле"
			} else {
				binding.menu.isErrorEnabled = false
				binding.TextFieldSumExpense.isErrorEnabled = false

				updateExpensesViewModel.updateExpenses(args.currentExpenses.expenseId,
					categoryExpensesString,
					sumExpenseString.toLong(),
					args.currentExpenses.dateExpense)

				binding.menu.editText?.text?.clear()
				binding.textSumExpense.text?.clear()

				binding.menu.clearFocus()
				binding.TextFieldSumExpense.clearFocus()
			}
			this.findNavController()
				.navigate(UpdateExpensesFragmentDirections.actionUpdateExpensesFragmentToCostHistoryFragment())
			it.hideKeyboard()
		}
	}

}

