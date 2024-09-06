package com.example.projetorobert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class SimpleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla o layout do fragmento
        val view = inflater.inflate(R.layout.fragment_new, container, false)

        // Referência ao botão
        val botaoVoltarTela = view.findViewById<Button>(R.id.botaoVoltarTela)

        // Define o listener de clique para remover o fragmento
        botaoVoltarTela.setOnClickListener {
            // Usa o FragmentManager para remover o fragmento
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

        return view
    }
}
