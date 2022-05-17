package com.example.moneytracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    var balance:Long = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sumExpensive : TextView = findViewById(R.id.textSumExpensive)
        val editTextExpensive: TextInputLayout = findViewById(R.id.editTextExpensive)
        val btnAddExpensive : Button = findViewById(R.id.btnAddExpensive)

        btnAddExpensive.setOnClickListener {
        if(editTextExpensive.editText?.text.toString() != ""){
            balance+=  Integer.parseInt(editTextExpensive.editText?.text.toString())
            sumExpensive.text = "$balance руб."
            editTextExpensive.editText?.text?.clear()
        }
          else    Toast.makeText(this, "Заполните поле: Сумма расхода", Toast.LENGTH_SHORT).show()





        }
    }

}