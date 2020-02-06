package ir.alirezanazari.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.alirezanazari.data.db.dao.VenueDao
import ir.alirezanazari.data.db.entity.VenueDataEntity

@Database(
    entities = [VenueDataEntity::class],
    version = 1
)
abstract class FourSquareDatabase : RoomDatabase() {

    abstract fun getVenueDao(): VenueDao

    companion object {

        private const val DATABASE_NAME = "foursquare_db.db"
        const val VENUE_TABLE_NAME = "venues_tbl"

        @Volatile
        private var instance: FourSquareDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, FourSquareDatabase::class.java, DATABASE_NAME
        ).build()
    }
}