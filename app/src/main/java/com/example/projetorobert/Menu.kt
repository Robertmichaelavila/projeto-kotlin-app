package com.example.projetorobert

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class Menu : AppCompatActivity() {

    private lateinit var textResult: TextView

    private val resultReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val result = intent?.getStringExtra("RESULT")
            textResult.text = result
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val inputNumber1 = findViewById<EditText>(R.id.input1)
        val inputNumber2 = findViewById<EditText>(R.id.input2)
        val btnCalculate = findViewById<Button>(R.id.calcular)
        textResult = findViewById(R.id.textResult)
        val btnHistory = findViewById<Button>(R.id.btnHistory)
        val botaoFragmente = findViewById<Button>(R.id.botaofragment)
        val intent = Intent(this, MusicService::class.java)

        btnCalculate.setOnClickListener {
            val num1 = inputNumber1.text.toString().toDoubleOrNull()
            val num2 = inputNumber2.text.toString().toDoubleOrNull()

            if (num1 != null && num2 != null) {
                // Configurar e iniciar o WorkManager para o cálculo
                val data = Data.Builder()
                    .putDouble("NUM1", num1)
                    .putDouble("NUM2", num2)
                    .build()

                val workRequest = OneTimeWorkRequest.Builder(CalculationWorker::class.java)
                    .setInputData(data)
                    .build()

                WorkManager.getInstance(this).enqueue(workRequest)

                // Verifica se num1 e num2 são iguais a 1
                if (num1 == 1.0 && num2 == 1.0) {
                    // Exibe o alerta após o cálculo
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Parabêns")
                    builder.setMessage("Você é burro o suficiente para ter que precisar calcular o valor de 1+1, vá estudar pangaré!")

                    // Botão "OK"
                    builder.setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }

                    // Exibe o alerta
                    val alertDialog = builder.create()
                    alertDialog.show()
                    startService(intent)
                }

            } else {
                textResult.text = "Please enter valid numbers"
            }
        }

        btnHistory.setOnClickListener {
            openHistoryActivity()
        }

        botaoFragmente.setOnClickListener {
            if (savedInstanceState == null) {
                val fragment = SimpleFragment()
                val fragmentManager: FragmentManager = supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

                fragmentTransaction.add(R.id.fragment_container, fragment)
                fragmentTransaction.commit()
            }
        }

        // Register BroadcastReceiver
        val intentFilter = IntentFilter("com.example.projetorobert.RESULT")
        registerReceiver(resultReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister BroadcastReceiver
        unregisterReceiver(resultReceiver)
        val intent = Intent(this, MusicService::class.java)
        stopService(intent)
    }

    private fun openHistoryActivity() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }
}
