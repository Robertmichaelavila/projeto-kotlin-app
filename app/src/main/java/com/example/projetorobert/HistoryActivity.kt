package com.example.projetorobert

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Configurar a RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewHistory)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Recuperar o histórico do banco de dados
        val dbHelper = DatabaseHelper(this)
        val historyList = dbHelper.getAllCalculations()

        // Configurar o adaptador da RecyclerView
        val adapter = HistoryAdapter(historyList)
        recyclerView.adapter = adapter

        // Identificar o botão e configurar o OnClickListener
        val botaoMudarTela = findViewById<Button>(R.id.botaoMudarTela)
        botaoMudarTela.setOnClickListener {
            finish() // Volta para a tela anterior
        }
    }
}
