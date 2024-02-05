/*
 * Copyright (c) 2024. Created by Alexsander Fernandes at 2/3. All rights reserved.
 * GitHub: https://github.com/alexsanderfer/
 * Portfolio: https://alexsanderfer.netlify.app/
 */

package com.alexsanderdev.applistatarefas

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alexsanderdev.applistatarefas.database.TarefaDAO
import com.alexsanderdev.applistatarefas.databinding.ActivityAdicionarTarefaBinding
import com.alexsanderdev.applistatarefas.model.Tarefa

class AdicionarTarefaActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAdicionarTarefaBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSalvar.setOnClickListener {

            if (binding.editTarefa.text.isNotEmpty()) {
                val description = binding.editTarefa.text.toString()
                val task = Tarefa(-1, description, "default")
                val tarefaDAO = TarefaDAO(this)

                if (tarefaDAO.salvar(task)) {
                    Toast.makeText(this, "Tarefa cadastrada com sucesso.", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Não foi possível salvar a tarefa, tente novamente.", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun salvar() {

    }
}