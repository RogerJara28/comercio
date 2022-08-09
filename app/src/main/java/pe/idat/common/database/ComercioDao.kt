package pe.idat.common.database

import androidx.room.*
import pe.idat.common.entities.ComercioEntity

@Dao
interface ComercioDao
{
    @Insert
    fun insertDB(comercioEntity: ComercioEntity): Long

    @Update
    fun updateDB(comercioEntity: ComercioEntity)

    @Delete
    fun deleteDB(comercioEntity: ComercioEntity)

    @Query("SELECT * FROM ComercioTable WHERE comercioId IN (:comercioId)")
    fun findByIdDB(comercioId:Int): ComercioEntity

    @Query("SELECT * FROM ComercioTable")
    fun findAllDB(): MutableList<ComercioEntity>
}