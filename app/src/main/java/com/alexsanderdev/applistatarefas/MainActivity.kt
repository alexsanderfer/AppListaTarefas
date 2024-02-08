package com.alexsanderdev.applistatarefas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexsanderdev.applistatarefas.adapter.TarefaAdapter
import com.alexsanderdev.applistatarefas.database.TarefaDAO
import com.alexsanderdev.applistatarefas.databinding.ActivityMainBinding
import com.alexsanderdev.applistatarefas.model.Tarefa

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var listaTarefas = emptyList<Tarefa>()
    private var tarefaAdapter: TarefaAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fabAdicionar.setOnClickListener {
            val intent = Intent(this, AdicionarTarefaActivity::class.java)
            startActivity(intent)
        }

        // RecyclerView
        tarefaAdapter = TarefaAdapter(
            { id -> confirmarExclusao(id) },
            { tarefa -> editar(tarefa) }
        )
        binding.rvTarefas.adapter = tarefaAdapter
        binding.rvTarefas.layoutManager = LinearLayoutManager(this)
    }

    private fun editar(tarefa: Tarefa) {
        val intent = Intent(this, AdicionarTarefaActivity::class.java)
        intent.putExtra("tarefa", tarefa)
        startActivity(intent)
    }

    private fun confirmarExclusao(id: Int) {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("Confirmar exclusão")
        alertBuilder.setMessage("Deseja realmente excluir a tarefa?")

        alertBuilder.setPositiveButton("Sim") { _, _ ->
            if (TarefaDAO(this).remover(id)) {
                atualizarListaTarefas()
                Toast.makeText(this, "Sucesso ao remover a mensagem", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erro ao remover a mensagem", Toast.LENGTH_SHORT).show()
            }

        }
        alertBuilder.setNegativeButton("Não") { _, _ -> }
        alertBuilder.create().show()

    }

    override fun onStart() {
        super.onStart()
        atualizarListaTarefas()
    }

    private fun atualizarListaTarefas() {
        val tarefaDAO = TarefaDAO(this)
        listaTarefas = tarefaDAO.listar()
        tarefaAdapter?.adicionarLista(listaTarefas)
    }
}