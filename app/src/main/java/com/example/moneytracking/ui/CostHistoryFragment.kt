package com.example.moneytracking.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moneytracking.R
import com.example.moneytracking.viewmodels.CostHistoryViewModel

class CostHistoryFragment : Fragment() {

	companion object {
		fun newInstance() = CostHistoryFragment()
	}

	private lateinit var viewModel: CostHistoryViewModel

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		return inflater.inflate(R.layout.fragment_cost_history, container, false)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		viewModel = ViewModelProvider(this).get(CostHistoryViewModel::class.java)
		// TODO: Use the ViewModel
	}

}