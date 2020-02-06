package ir.alirezanazari.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import ir.alirezanazari.data.db.FourSquareDatabase.Companion.VENUE_TABLE_NAME
import ir.alirezanazari.data.db.entity.VenueDataEntity

@Dao
interface VenueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(venue: VenueDataEntity)

    @Query("delete from $VENUE_TABLE_NAME where userLocation= :latlng")
    fun deleteByLatlng(latlng: String)

    @Query("delete from $VENUE_TABLE_NAME")
    fun deleteAll()

    @Query("select * from $VENUE_TABLE_NAME where userLocation= :latlng")
    fun getVenues(latlng: String): Single<List<VenueDataEntity>>

    @Query("select userLocation from $VENUE_TABLE_NAME")
    fun getLastLocation():Single<List<String>>
}