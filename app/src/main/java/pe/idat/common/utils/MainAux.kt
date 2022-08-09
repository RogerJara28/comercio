package pe.idat.common.utils

import pe.idat.common.entities.ComercioEntity

interface MainAux
{
    fun insertMemory(comercioEntity: ComercioEntity)
    fun updateMemory(comercioEntity: ComercioEntity)
}