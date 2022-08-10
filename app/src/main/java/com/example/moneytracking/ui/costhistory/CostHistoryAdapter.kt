package com.example.moneytracking.ui.costhistory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.moneytracking.R
import com.example.moneytracking.model.MoneyTrack
import com.example.moneytracking.repository.MoneyRepository
import com.example.moneytracking.utils.convertLongToDateString
import kotlinx.android.synthetic.main.cost_history_item.view.*

class CostHistoryAdapter : Adapter<CostHistoryAdapter.ViewHolder>() {
	private var expenseList = emptyList<MoneyTrack>()

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(LayoutInflater.from(parent.context)
			.inflate(R.layout.cost_history_item, parent, false))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val currentItem = expenseList[position]

		holder.itemView.dateExpense.text =
			convertLongToDateString(currentItem.dateExpense).replace("-", ".")
		holder.itemView.categoryExpense.text = currentItem.categoryExpense
		holder.itemView.sumExpense.text = currentItem.sumExpense.toString() + " ₽"

	}

	override fun getItemCount(): Int {
		return expenseList.size
	}

	fun setData(expense: List<MoneyTrack>) {
		this.expenseList = expense
		notifyDataSetChanged()
	}

    fun updateItem(context: Fragment,position: Int){
	val action =CostHistoryFragmentDirections.actionCostHistoryFragmentToUpdateExpensesFragment(expenseList[position])
		context.findNavController().navigate(action)
	}
	fun deleteItem(position: Int): MoneyTrack {
		return expenseList[position]
	}


}