package pe.idat.mainModule.adapter

import pe.idat.common.entities.ComercioEntity

interface OnClickListener
{
    fun onClick(comercioEntity: ComercioEntity)
    fun onClickFavorite(comercioEntity: ComercioEntity)
    fun onClickDelete(comercioEntity: ComercioEntity)
}