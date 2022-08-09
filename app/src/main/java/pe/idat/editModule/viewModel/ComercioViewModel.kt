package pe.idat.editModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.idat.common.entities.ComercioEntity
import pe.idat.editModule.model.ComercioInteractor

//VIEWMODEL
class ComercioViewModel : ViewModel() {
    //propiedades para la vista
    private val comercioSelected = MutableLiveData<ComercioEntity>()
    private val showFab = MutableLiveData<Boolean>()
    private val result = MutableLiveData<Any>()
    private val interactor: ComercioInteractor = ComercioInteractor()

    //getters and setters
    fun setComercioSelected(comercioEntity: ComercioEntity) {
        comercioSelected.value = comercioEntity
    }

    fun getComercioSelected(): LiveData<ComercioEntity> {
        return comercioSelected
    }

    fun setShowFab(isVisible: Boolean) {
        showFab.value = isVisible
    }

    fun getShowFab(): LiveData<Boolean> {
        return showFab
    }

    fun setResult(value: Any) {
        result.value = value
    }

    fun getResult(): LiveData<Any> {
        return result
    }

    fun saveComercio(comercioEntity: ComercioEntity) {
        interactor.saveComercio(comercioEntity) { newID ->
            result.value = newID
        }
    }

    fun updateComercio(comercioEntity: ComercioEntity) {
        interactor.updateComercio(comercioEntity) { comercioUpdate ->
            result.value = comercioUpdate
        }
    }
}