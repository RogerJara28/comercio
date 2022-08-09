package pe.idat.mainModule.model

import android.util.Log
import kotlinx.coroutines.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import pe.idat.ComercioApplication
import pe.idat.common.entities.ComercioEntity

//MODEL
class MainInteractor {

    fun getComercios(callback: (List<ComercioEntity>) -> Unit) {
        doAsync {
            val comercioDB = ComercioApplication.database.ComercioDao().findAllDB()
            uiThread {
                callback(comercioDB)
            }
        }
    }

    fun deleteComercio(comercioEntity: ComercioEntity, callback: (ComercioEntity) -> Unit) {
        doAsync {
            ComercioApplication.database.ComercioDao().deleteDB(comercioEntity)

            uiThread {
                callback(comercioEntity)
            }
        }
    }

    fun updateComercio(comercioEntity: ComercioEntity, callback: (ComercioEntity) -> Unit) {
        doAsync {
            ComercioApplication.database.ComercioDao().updateDB(comercioEntity)

            uiThread {
                callback(comercioEntity)
            }
        }
    }
}
