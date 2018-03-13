package com.naka.atzap.ui

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.telephony.SmsManager
import android.util.Log
import com.naka.atzap.R
import com.naka.atzap.helper.Permissao
import com.naka.atzap.helper.Preferencias
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    private val permissoesNecessarias = listOf(Manifest.permission.SEND_SMS,
                                                Manifest.permission.INTERNET)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonCadastrar.setOnClickListener(){
            //val intent = Intent(this, ValidacaoActivity::class.java)
            //startActivity(intent)

            val permissao = Permissao()
            permissao.validaPermissoes(this, permissoesNecessarias)


            val nomeUsuario = editTextLoginNome.text.toString()
            val ddi = editTextLoginDdi.text.toString()
            val ddd = editTextLoginDdd.text.toString()
            val celular = editTextoLoginCelular.text.toString()
            val token = (Random().nextInt(8999) + 1000).toString()

            val preferencias = Preferencias(this)
            preferencias.salvarPreferenciasUsuario(nomeUsuario, ddi + ddd + celular, token)
            val usuario = preferencias.getDadosUsuario()

            val smsEnviado = enviaSMS("+5524", "Código de Confirmação do AtZap: $token")

        }
    }

    private fun enviaSMS(telefone: String, msg: String): Boolean {
        return try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(telefone, null, msg, null, null)
            true

        } catch (e : Exception){
            e.printStackTrace()
            false
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            if (it == PackageManager.PERMISSION_DENIED){
                emiteAlertaPermissaoNegada()
            }
        }
    }

    private fun emiteAlertaPermissaoNegada() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Permissão negada...")
        builder.setMessage("Para que o app funcione corretamente é necessário aceitar as permissões.")
        builder.setPositiveButton("CONFIRMAR") {
            dialog, whichButton -> finish()
        }

        val dialog = builder.create()
        dialog.show()
    }
}
