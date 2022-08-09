package pe.idat.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.idat.common.entities.ComercioEntity

@Database(entities = [ComercioEntity::class], version = 2)
abstract class ComercioDatabase : RoomDatabase() {
    abstract fun ComercioDao(): ComercioDao
}