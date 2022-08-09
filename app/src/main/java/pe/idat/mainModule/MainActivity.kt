package pe.idat.mainModule

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import pe.idat.ComercioApplication
import pe.idat.R
import pe.idat.common.entities.ComercioEntity
import pe.idat.common.utils.MainAux
import pe.idat.databinding.ActivityMainBinding
import pe.idat.editModule.ComercioFragment
import pe.idat.editModule.viewModel.ComercioViewModel
import pe.idat.mainModule.adapter.ComercioAdapter
import pe.idat.mainModule.adapter.OnClickListener
import pe.idat.mainModule.viewModel.MainViewModel

//VIEW
class MainActivity : AppCompatActivity(), OnClickListener //, MainAux
{
    lateinit var mBinding:ActivityMainBinding

    private lateinit var mAdapter: ComercioAdapter
    private lateinit var mGridLayout:GridLayoutManager

    //usando patrón MVVM
    private lateinit var mMainViewModel:MainViewModel
    private lateinit var mComercioViewModel:ComercioViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //nuevo sistema de vinculación de vistas
        mBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        /*
        //evento del Button
        mBinding.btnSave.setOnClickListener {
            val comercio=ComercioEntity(name=mBinding.etName.text.toString().trim())

            Thread {
                val comercioId=ComercioApplication.database.ComercioDao().insertDB(comercio)
                comercio.comercioId=comercioId
            }.start()

            mAdapter.insertMemory(comercio)
        }
        */

        //evento del boton flotante
        mBinding.fabAddComercio.setOnClickListener {
            launchFragment()
        }

        //inicializar ViewModel
        setupVideModel()

        //configurar el RecyclerView
        mAdapter= ComercioAdapter(mutableListOf(),this)
        mGridLayout=GridLayoutManager(this,2)

        /*
        //ejecutar hilo (cargar colección)
        doAsync {
            val comercioDB= ComercioApplication.database.ComercioDao().findAllDB()

            uiThread {
                mAdapter.setCollection(comercioDB)
            }
        } */

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            adapter=mAdapter
            layoutManager=mGridLayout
        }
       mMainViewModel.getComercios().observe(this){
           it.let {
               mAdapter.submitList(it)
           }
       }

    }

    override fun onClick(comercioEntity: ComercioEntity) {
        launchFragment(comercioEntity)
    }

    override fun onClickFavorite(comercioEntity: ComercioEntity)
    {
        //comercioEntity.isFavorite=!comercioEntity.isFavorite

        /*
        doAsync {
            ComercioApplication.database.ComercioDao().updateDB(comercioEntity)

            uiThread {
                mAdapter.updateMemory(comercioEntity)
            }
        } */

        mMainViewModel.updateComercio(comercioEntity)
    }

    override fun onClickDelete(comercioEntity: ComercioEntity)
    {
        //opciones
        val items=arrayOf("Eliminar","Llamar","Ir al sitio web")

        //venatana de dialogo como menu
        MaterialAlertDialogBuilder(this)
            .setTitle("¿Que desea hacer?")
            .setItems(items,DialogInterface.OnClickListener { dialogInterface, i ->

                when(i) {
                    0 -> confirmDelete(comercioEntity)
                    1 -> callPhone(comercioEntity.phone)
                    2 -> goToWebsite(comercioEntity.website)
                }
            }).show()
    }

    //lanzar fragmento MVVM
    private fun launchFragment(comercioEntity:ComercioEntity=ComercioEntity())
    {
        val fragment=ComercioFragment()
        mComercioViewModel.setComercioSelected(comercioEntity)

        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()

        //transacción para el fragemnto
        fragmentTransaction.add(R.id.containerMain,fragment)

        //volver al fragmento principal
        fragmentTransaction.addToBackStack(null)

        //transacción que aplque los cambios
        fragmentTransaction.commit()

        //ocultar boton flotante
        mComercioViewModel.setShowFab(false)
    }

    /*override fun insertMemory(comercioEntity: ComercioEntity) {
        mAdapter.insertMemory(comercioEntity)
    } */

    /*override fun updateMemory(comercioEntity: ComercioEntity) {
        mAdapter.updateMemory(comercioEntity)
    } */

    private fun confirmDelete(comercioEntity: ComercioEntity)
    {
        //ventana de dialogo
        MaterialAlertDialogBuilder(this)
            .setTitle("¿Eliminar comercio?")
            .setPositiveButton("Eliminar",
                DialogInterface.OnClickListener { dialogInterface, i ->

                    /*
                    //proceder con la eliminación
                    doAsync {
                        ComercioApplication.database.ComercioDao().deleteDB(comercioEntity)

                        uiThread {
                            mAdapter.deleteMemory(comercioEntity)
                        }
                    } */

                    mMainViewModel.deleteComercio(comercioEntity)
                })
            .setNegativeButton("Cancelar",null).show()
    }

    private fun callPhone(phone:String)
    {
        val call=Intent().apply {
            action=Intent.ACTION_DIAL //acción de llamar a teléfonos
            data=Uri.parse("tel:$phone") //data del número que se quiere marcar
        }

        if(call.resolveActivity(packageManager)!=null) {
            //realizar de actividad de llamadas
            startActivity(call)
        }
        else {
            Toast.makeText(this, R.string.error_no_resolve,Toast.LENGTH_LONG).show()
        }
    }

    private fun goToWebsite(website:String)
    {
        val call=Intent().apply {
            action=Intent.ACTION_VIEW //acción de llamar a vistas
            data=Uri.parse(website) //data del website que se quiere marcar
        }

        if(call.resolveActivity(packageManager)!=null) {
            //realizar de actividad de llamadas
            startActivity(call)
        }
        else {
            Toast.makeText(this, R.string.error_no_resolve,Toast.LENGTH_LONG).show()
        }
    }

    //inicializar ViewModel
    private fun setupVideModel()
    {
        mMainViewModel=ViewModelProvider(this).get(MainViewModel::class.java)

        mMainViewModel.getComercios().observe(this,{comercios ->
            mAdapter.setCollection(comercios)
        })

        //inicializado
        mComercioViewModel=ViewModelProvider(this).get(ComercioViewModel::class.java)

        //visualización del floating action button
        mComercioViewModel.getShowFab().observe(this,{isVisible->
            if(isVisible) {
                mBinding.fabAddComercio.show()
            } else {
                mBinding.fabAddComercio.hide()
            }
        })

        //funcionamiento en memoria
        mComercioViewModel.getComercioSelected().observe(this,{comercioEntity->
            mAdapter.saveMemory(comercioEntity)
        })
    }
}















