package com.alexsanderdev.applistatarefas.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.alexsanderdev.applistatarefas.database.DatabaseHelper.Companion.DATA_CADASTRO
import com.alexsanderdev.applistatarefas.database.DatabaseHelper.Companion.DESCRICAO
import com.alexsanderdev.applistatarefas.database.DatabaseHelper.Companion.ID_TAREFA
import com.alexsanderdev.applistatarefas.database.DatabaseHelper.Companion.TABELA_TAREFAS
import com.alexsanderdev.applistatarefas.model.Tarefa

class TarefaDAO(context: Context) : ITarefaDAO {

    private val escrita = DatabaseHelper(context).writableDatabase
    private val leitura = DatabaseHelper(context).readableDatabase

    override fun salvar(tarefa: Tarefa): Boolean {
        val conteudos = ContentValues()
        conteudos.put(DESCRICAO, tarefa.descricao)

        try {
            escrita.insert(TABELA_TAREFAS, null, conteudos)
            Log.i("info_db", "Sucesso ao salvar a tarefa")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao criar a tarefa")
            return false
        }
        return true
    }

    override fun atualizar(tarefa: Tarefa): Boolean {
        val args = arrayOf(tarefa.idTarefa.toString())
        val conteudo = ContentValues()
        conteudo.put(DESCRICAO, tarefa.descricao)
        try {
            escrita.update(TABELA_TAREFAS, conteudo, "$ID_TAREFA = ?", args)
            Log.i("info_db", "Sucesso ao atualizar a tarefa")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao atualizar a tarefa")
            return false
        }
        return true
    }

    override fun remover(idTarefa: Int): Boolean {
        val args = arrayOf(idTarefa.toString())

        try {
            escrita.delete(TABELA_TAREFAS, "$ID_TAREFA = ?", args)
            Log.i("info_db", "Sucesso ao remover a tarefa")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao remover a tarefa")
            return false
        }
        return true
    }

    override fun listar(): List<Tarefa> {
        val listaTarefas = mutableListOf<Tarefa>()
        val sql = "SELECT $ID_TAREFA, $DESCRICAO, strftime('%d/%m/%Y - %Hh:%Mm'), $DATA_CADASTRO FROM $TABELA_TAREFAS;"
        val cursor = leitura.rawQuery(sql, null)
        val indiceID = cursor.getColumnIndex(ID_TAREFA)
        val indiceDescricao = cursor.getColumnIndex(DESCRICAO)
        val indiceData = cursor.getColumnIndex(DATA_CADASTRO)

        while (cursor.moveToNext()) {
            val idTarefa = cursor.getInt(indiceID)
            val descricao = cursor.getString(indiceDescricao)
            val data = cursor.getString(indiceData)

            listaTarefas.add(Tarefa(idTarefa, descricao, data))
        }

        cursor.close()
        return listaTarefas
    }
}