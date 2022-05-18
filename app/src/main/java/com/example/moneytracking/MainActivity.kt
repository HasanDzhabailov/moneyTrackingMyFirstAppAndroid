package com.example.moneytracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

const val  KEY_BALANCE = "balance_key"
const val KEY_ALL_SUM_EXPENSIVE = "sum_expensive_key"
const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private var balance:Long = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val allSumExpensive : TextView = findViewById(R.id.textSumExpensive)
        val editTextExpensive: TextInputLayout = findViewById(R.id.editTextExpensive)
        val btnAddExpensive : Button = findViewById(R.id.btnAddExpensive)

        if(savedInstanceState!=null){
            balance = savedInstanceState.getLong(KEY_BALANCE,0)
            allSumExpensive.text = savedInstanceState.getString(KEY_ALL_SUM_EXPENSIVE,"0")
        }

        btnAddExpensive.setOnClickListener {
            if(editTextExpensive.editText?.text.toString() != ""){
                balance+=  Integer.parseInt(editTextExpensive.editText?.text.toString())
                allSumExpensive.text = "$balance руб."

                editTextExpensive.editText?.text?.clear()
                }
            else Toast.makeText(this, "Заполните поле: Сумма расхода", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val allSumExpensive : TextView = findViewById(R.id.textSumExpensive);
        outState.putLong(KEY_BALANCE,balance)
        outState.putString(KEY_ALL_SUM_EXPENSIVE,allSumExpensive.text.toString())
        Log.d(TAG, "onSaveInstanceState Called")
    }



}