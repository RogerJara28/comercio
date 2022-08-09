package pe.idat.common.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="ComercioTable")
data class ComercioEntity(@PrimaryKey(autoGenerate=true) var comercioId:Long=0,
                          var name:String,
                          var phone:String,
                          var website:String="",
                          var photoUrl:String,
                          var isFavorite:Boolean=false)
{
    //constructor vacío
    constructor():this(name="",phone="",photoUrl="")

    //métodos para evitar la duplicidad por ID
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ComercioEntity

        if (comercioId != other.comercioId) return false

        return true
    }

    override fun hashCode(): Int {
        return comercioId.hashCode()
    }
}
