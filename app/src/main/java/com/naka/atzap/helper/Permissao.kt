package com.naka.atzap.helper

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


/**
 * Created by naka on 13/03/18.
 */
class Permissao {
    fun validaPermissoes(activity : Activity, permissoes : List<String>): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            val listaPermissoes = ArrayList<String>()
            permissoes.forEach {
                val permissaoGarantida = ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
                if (!permissaoGarantida) listaPermissoes.add(it)
            }

            if (listaPermissoes.isEmpty()) return true

            val permissoesNecessarias = arrayOfNulls<String>(listaPermissoes.size)
            listaPermissoes.toArray(permissoesNecessarias)
            ActivityCompat.requestPermissions(activity, permissoesNecessarias, 1)
        }
        return true
    }

}