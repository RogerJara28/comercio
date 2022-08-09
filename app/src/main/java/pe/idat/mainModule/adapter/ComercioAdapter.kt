package pe.idat.mainModule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import pe.idat.R
import pe.idat.common.entities.ComercioEntity
import pe.idat.databinding.ItemComercioBinding

class ComercioAdapter(private var comercios:MutableList<ComercioEntity>,
                      private var listener: OnClickListener
): ListAdapter<ComercioEntity,ComercioAdapter.ViewHolder>(ComercioDiff())
{
    private lateinit var mContext:Context

    //clase interna que recibe una vista
    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view)
    {
        //referencia a la vista item_comercio
        val binding=ItemComercioBinding.bind(view)

        //función que recibe un comercio
        fun setListener(comercioEntity: ComercioEntity)
        {
            //clik normal -> evento onClick
            binding.root.setOnClickListener {
                listener.onClick(comercioEntity)
            }

            //click largo -> evento onClickDelete
            binding.root.setOnLongClickListener {
                listener.onClickDelete(comercioEntity)
                true
            }

            //click favorite -> evento onClickFavorite
            binding.cbFavorite.setOnClickListener {
                listener.onClickFavorite(comercioEntity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        mContext=parent.context
        val view=LayoutInflater.from(mContext).inflate(R.layout.item_comercio,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val comercio=comercios.get(position)

        with(holder)
        {
            //escuchar el comercio
            setListener(comercio)

            //pintar nombre
            binding.tvNameComercio.text=comercio.name

            //pintar favorite
            binding.cbFavorite.isChecked=comercio.isFavorite

            //pintar imagen
            Glide.with(mContext)
                .load(comercio.photoUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imgPhoto)
        }
    }

    override fun getItemCount(): Int {
        return comercios.size
    }

    /*
    fun setCollection(comerciosDB:MutableList<ComercioEntity>)
    {
        this.comercios=comerciosDB
        notifyDataSetChanged() //refrescar los cambios
    } */

    fun setCollection(comercios:List<ComercioEntity>)
    {
        this.comercios=comercios as MutableList<ComercioEntity>
        notifyDataSetChanged() //refrescar los cambios
    }

    //insert or update
    fun saveMemory(comercioEntity: ComercioEntity)
    {
        if(comercioEntity.comercioId!=0L)
        {
            //si comercio no existe en la lista de comercios
            if(!comercios.contains(comercioEntity))
            {
                comercios.add(comercioEntity)
                notifyItemInserted(comercios.size-1)
            }
            else {
                updateMemory(comercioEntity)
            }
        }
    }

    private fun updateMemory(comercioEntity: ComercioEntity)
    {
        val index=comercios.indexOf(comercioEntity)

        if(index!=-1)
        {
            comercios.set(index,comercioEntity)
            notifyDataSetChanged()
        }
    }

    /*fun deleteMemory(comercioEntity: ComercioEntity)
    {
        val index=comercios.indexOf(comercioEntity)

        if(index!=-1)
        {
            comercios.removeAt(index)
            notifyItemRemoved(index) //refrescar los cambios
        }
    } */

    class ComercioDiff:DiffUtil.ItemCallback<ComercioEntity>(){
        override fun areItemsTheSame(oldItem: ComercioEntity, newItem: ComercioEntity): Boolean {
            return oldItem.comercioId==newItem.comercioId
        }

        override fun areContentsTheSame(oldItem: ComercioEntity, newItem: ComercioEntity): Boolean {
            return oldItem==newItem
        }
    }

}


