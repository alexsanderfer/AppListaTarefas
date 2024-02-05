package com.alexsanderdev.applistatarefas.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.alexsanderdev.applistatarefas.model.Tarefa

class TarefaDAO(context: Context) : ITarefaDAO {

    private val escrita = DatabaseHelper(context).writableDatabase
    private val leitura = DatabaseHelper(context).readableDatabase

    override fun salvar(tarefa: Tarefa): Boolean {
        val conteudos = ContentValues()
        conteudos.put("${DatabaseHelper.DESCRICAO}", tarefa.descricao)

        escrita.insert(DatabaseHelper.TABELA_TAREFAS, null, conteudos)
        try {
            Log.i("info_db", "Sucesso ao salvar a tarefa")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao criar a tarefa")
            return false
        }
        return true
    }

    override fun atualizar(tarefa: Tarefa): Boolean {
        TODO("Not yet implemented")
    }

    override fun remover(idTarefa: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun listar(): List<Tarefa> {
        TODO("Not yet implemented")
    }
}