package com.example.pexelsproject.data.source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pexelsproject.data.source.dao.model.CollectionEntity

@Dao
interface CollectionsDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCollectionToDatabase(collectionEntity: CollectionEntity)

    @Query("SELECT * FROM `collection_database`")
    suspend fun getAllCollectionsFromDatabase(): List<CollectionEntity>

    @Query("DELETE FROM `collection_database`")
    suspend fun clearTable()

}