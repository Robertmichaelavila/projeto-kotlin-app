package com.example.projetorobert

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Vincula o botão pelo ID
        val botaoMudarTela = findViewById<Button>(R.id.botaoMudarTela)

        // Define o listener de clique
        botaoMudarTela.setOnClickListener {
            // Cria um Intent para mudar de tela
            val intent = Intent(this, Menu::class.java)
            startActivity(intent) // Inicia a nova Activity
            finish()
        }
    }
}