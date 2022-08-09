package pe.idat.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import pe.idat.ComercioApplication
import pe.idat.common.entities.ComercioEntity
import pe.idat.mainModule.model.MainInteractor

//VIEWMODEL
class MainViewModel: ViewModel()
{
    //reflejar datos de la vista
    //private var comercios:MutableLiveData<List<ComercioEntity>>

    //reflejar datos del model
    private var interactor:MainInteractor

    private var comercioList:MutableList<ComercioEntity>

    init {
        interactor=MainInteractor()
        comercioList=mutableListOf()

        //comercios=MutableLiveData()
        //loadComercios()
    }

    //inicialización por lazy
    private val comercios:MutableLiveData<List<ComercioEntity>> by lazy {
        MutableLiveData<List<ComercioEntity>>().also {
            loadComercios()
        }
    }

    //encapsulando
    fun getComercios():LiveData<List<ComercioEntity>> {
        return comercios
    }

    private fun loadComercios()
    {
        /*
        doAsync {
            val comercioDB=ComercioApplication.database.ComercioDao().findAllDB()

            uiThread {
                comercios.value=comercioDB
            }
        } */

        /*
        interactor.getComerciosCallback(object:MainInteractor.ComerciosCallback {
            override fun getComerciosCallback(comercios: MutableList<ComercioEntity>) {
                this@MainViewModel.comercios.value=comercios
            }
        }) */

        interactor.getComercios {
            comercios.value=it
            comercioList=it
        }
    }

    fun deleteComercio(comercioEntity:ComercioEntity)
    {
        interactor.deleteComercio(comercioEntity, {
            val index=comercioList.indexOf(comercioEntity)

            if(index!=-1)
            {
                comercioList.removeAt(index)
                comercios.value=comercioList
            }
        })
    }

    fun updateComercio(comercioEntity:ComercioEntity)
    {
        comercioEntity.isFavorite=!comercioEntity.isFavorite

        interactor.updateComercio(comercioEntity, {
            val index=comercioList.indexOf(comercioEntity)

            if(index!=-1)
            {
                comercioList.set(index,comercioEntity)
                comercios.value=comercioList
            }
        })
    }
}