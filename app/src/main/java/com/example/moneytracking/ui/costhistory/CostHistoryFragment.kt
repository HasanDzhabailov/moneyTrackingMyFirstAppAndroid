package com.example.moneytracking.ui.costhistory

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.moneytracking.R
import com.example.moneytracking.database.MoneyTrackDatabase

import com.example.moneytracking.databinding.FragmentCostHistoryBinding
import com.example.moneytracking.di.Injectable

import com.example.moneytracking.utils.*
import com.google.android.material.datepicker.MaterialDatePicker
import javax.inject.Inject


class CostHistoryFragment : Fragment(), Injectable {
	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		val binding: FragmentCostHistoryBinding =
			DataBindingUtil.inflate(inflater, R.layout.fragment_cost_history, container, false)

		// Get a reference to the ViewModel associated with this fragment.
		val costHistoryViewModel:CostHistoryViewModel by viewModels {
			viewModelFactory
		}
		// To use the View Model with data binding, you have to explicitly
		// give the binding object a reference to it.
		binding.costHistoryViewModel = costHistoryViewModel
		// Specify the current activity as the lifecycle owner of the binding.
		binding.lifecycleOwner = this
		// Recyclerview
		val adapter = CostHistoryAdapter()
		binding.recyclerViewCostHistory.adapter = adapter
		binding.recyclerViewCostHistory.layoutManager = LinearLayoutManager(requireContext())
		binding.recyclerViewCostHistory.addItemDecoration(DividerItemDecoration(requireContext(),
			DividerItemDecoration.VERTICAL))


		fun deleteButton(position: Int): SwipeHelper.UnderlayButton {

			return SwipeHelper.UnderlayButton(
				requireContext(),
				"Удалить",
				14.0f,
				android.R.color.holo_red_light,
				object : SwipeHelper.UnderlayButtonClickListener {
					override fun onClick() {
						val builderDialog = AlertDialog.Builder(requireContext())
						builderDialog.setTitle(R.string.dialog_title)
						builderDialog.setMessage(R.string.dialog_message)
						builderDialog.setIcon(R.drawable.ic_baseline_delete_24)
						builderDialog.setPositiveButton("Да") { dialogInterface, which ->
							adapter.notifyItemRemoved(position)
							costHistoryViewModel.delete(adapter.deleteItem(position))
						}
						builderDialog.setNeutralButton("Отмена") { dialogInterface, which ->
							dialogInterface.cancel()
						}
						val alertDialog: AlertDialog = builderDialog.create()
						alertDialog.setCancelable(false)
						alertDialog.show()
					}
				})
		}

		fun editButton(position: Int): SwipeHelper.UnderlayButton {
			return SwipeHelper.UnderlayButton(
				requireContext(),
				"Редактировать",
				14.0f,
				android.R.color.holo_green_light,
				object : SwipeHelper.UnderlayButtonClickListener {
					override fun onClick() {
						adapter.updateItem(this@CostHistoryFragment, position)
					}
				})
		}


		val itemTouchHelper =
			ItemTouchHelper(object : SwipeHelper(binding.recyclerViewCostHistory) {
				override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
					var buttons = listOf<UnderlayButton>()
					val deleteButton = deleteButton(position)
					val markAsUnreadButton = editButton(position)
					when (position) {
						position -> buttons = listOf(deleteButton, markAsUnreadButton)
						else -> Unit
					}
					return buttons
				}
			})
		itemTouchHelper.attachToRecyclerView(binding.recyclerViewCostHistory)

		fun getPeriodHistoryExpenses(startPeriod: Long, endPeriod: Long) {
			costHistoryViewModel.getHistoryExpensesPeriod(startPeriod, endPeriod)
				.observe(viewLifecycleOwner, Observer { expense ->
					adapter.setData(expense)
				})

		}

		fun showDataRangePicker() {
			val dateRangePicker =
				MaterialDatePicker
					.Builder.dateRangePicker()
					.setTitleText("Выберите дату")
					.build()

			dateRangePicker.show(
				activity!!.supportFragmentManager,
				"date_range_picker"
			)

			dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->
				val startDate = dateSelected.first
				val endDate = dateSelected.second

				if (startDate != null && endDate != null) {
					costHistoryViewModel.getHistoryExpensesPeriod(startDate, endDate)
					getPeriodHistoryExpenses(startDate, endDate)
				}
			}
		}
		getPeriodHistoryExpenses(getStartMonth(), getEndMonth())
		binding.chipsGroupPeriod.chipToday.setOnClickListener {
			getPeriodHistoryExpenses(getStartDay(), getEndDay())
		}
		binding.chipsGroupPeriod.chipWeek.setOnClickListener {
			getPeriodHistoryExpenses(getStartWeek(), getEndWeek())
		}
		binding.chipsGroupPeriod.chipMonth.setOnClickListener {
			getPeriodHistoryExpenses(getStartMonth(), getEndMonth())
		}
		binding.chipsGroupPeriod.chipYear.setOnClickListener {
			getPeriodHistoryExpenses(getStartYear(), getEndYear())
		}
		binding.chipsGroupPeriod.chipSelectDate.setOnClickListener {
			showDataRangePicker()
		}

		return binding.root
	}


}