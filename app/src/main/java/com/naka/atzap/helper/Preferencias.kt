package com.naka.atzap.helper

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by naka on 13/03/18.
 */
class Preferencias (val context: Context){

    private val NOME_ARQUIVO = "atzap.preferences"
    private val MODE = 0
    private val CHAVE_NOME = "NOME"
    private val CHAVE_TELEFONE = "TELEFONE"
    private val CHAVE_TOKEN = "TOKEN"

    private val preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE)
    private val editor = preferences.edit()


    fun salvarPreferenciasUsuario(nome : String, telefone : String, token : String){
        editor.putString(CHAVE_NOME, nome)
        editor.putString(CHAVE_TELEFONE, telefone)
        editor.putString(CHAVE_TOKEN, token)
        editor.commit()
    }

    fun getDadosUsuario() : HashMap<String, String> {
        val dados = hashMapOf<String, String>()

        dados.put(CHAVE_NOME,preferences.getString(CHAVE_NOME, null))
        dados.put(CHAVE_TELEFONE,preferences.getString(CHAVE_TELEFONE, null))
        dados.put(CHAVE_TOKEN,preferences.getString(CHAVE_TOKEN, null))

        return dados
    }

}