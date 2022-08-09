package pe.idat.mainModule.model

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import pe.idat.ComercioApplication
import pe.idat.common.entities.ComercioEntity

//MODEL
class MainInteractor
{
    /*
    //definimos una interfaz
    interface ComerciosCallback {
        fun getComerciosCallback(comercios:MutableList<ComercioEntity>)
    } */

    /*
    //función para el ViewModel
    fun getComerciosCallback(callback:ComerciosCallback)
    {
        doAsync {
            val comercioDB=ComercioApplication.database.ComercioDao().findAllDB()

            uiThread {
                //respuesta para el ViewModel
                callback.getComerciosCallback(comercioDB)
            }
        }
    } */

    fun getComercios(callback:(MutableList<ComercioEntity>) -> Unit)
    {
        doAsync {
            val comercioDB=ComercioApplication.database.ComercioDao().findAllDB()

            uiThread {
                //respuesta para el ViewModel
                callback(comercioDB)
            }
        }
    }

    fun deleteComercio(comercioEntity:ComercioEntity,callback:(ComercioEntity) -> Unit)
    {
        doAsync {
            ComercioApplication.database.ComercioDao().deleteDB(comercioEntity)

            uiThread {
                callback(comercioEntity)
            }
        }
    }

    fun updateComercio(comercioEntity:ComercioEntity,callback:(ComercioEntity) -> Unit)
    {
        doAsync {
            ComercioApplication.database.ComercioDao().updateDB(comercioEntity)

            uiThread {
                callback(comercioEntity)
            }
        }
    }
}
