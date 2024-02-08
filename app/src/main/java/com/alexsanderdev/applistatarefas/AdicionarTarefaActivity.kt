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

        // Recuperar a tarefa passada
        var tarefa: Tarefa? = null
        val bundle = intent.extras
        if (bundle != null) {
            tarefa = bundle.getSerializable("tarefa") as Tarefa
            binding.editTarefa.setText(tarefa.descricao)
        }

        binding.btnSalvar.setOnClickListener {

            if (binding.editTarefa.text.isNotEmpty()) {
                if (tarefa != null) {
                    editarTarefa(tarefa)
                } else {
                    salvaTarefa()
                }
            } else {
                Toast.makeText(this, "Preencha uma tarefa", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun editarTarefa(tarefa: Tarefa) {
        val descricao = binding.editTarefa.text.toString()
        val tarefaAtualizar = Tarefa(tarefa.idTarefa, descricao, "default")
        val tarefaDAO = TarefaDAO(this)

        if (tarefaDAO.salvar(tarefaAtualizar)) {
            Toast.makeText(this, "Tarefa atualizada com sucesso.", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Não foi possível atualizar a tarefa, tente novamente.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun salvaTarefa() {
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