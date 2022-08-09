package pe.idat.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.idat.common.entities.ComercioEntity
import pe.idat.mainModule.model.MainInteractor

//VIEWMODEL
class MainViewModel : ViewModel() {

    //reflejar datos del model
    private val interactor: MainInteractor = MainInteractor()

    // creo que esta guarda la lista interna de objetos, para luego solo
    // manipular esta variable e ir actualizando valores basados en esta.
    // quiero eliminarla pero la dejo por acá por ahora
    private var comercioList: MutableList<ComercioEntity> = mutableListOf()

    private val _comercios = MutableLiveData<List<ComercioEntity>>()
    val comercios: LiveData<List<ComercioEntity>>
        get() = _comercios

    init {
        loadComercios()
    }

    // this is the one that loads it up
    fun loadComercios() {
        interactor.getComercios {
            comercioList = it.toMutableList()
            _comercios.value = it
        }
    }

    fun deleteComercio(comercioEntity: ComercioEntity) {
        interactor.deleteComercio(comercioEntity) {
            val index = comercioList.indexOf(comercioEntity)

            if (index != -1) {
                comercioList.removeAt(index)
                _comercios.value = comercioList
            }
        }
    }

    fun updateComercio(comercioEntity: ComercioEntity) {
        comercioEntity.isFavorite = !comercioEntity.isFavorite

        interactor.updateComercio(comercioEntity) {
            val index = comercioList.indexOf(comercioEntity)

            if (index != -1) {
                comercioList.set(index, comercioEntity)
                _comercios.value = comercioList
            }
        }
    }
}