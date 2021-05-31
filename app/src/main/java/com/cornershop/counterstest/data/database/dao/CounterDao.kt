package com.cornershop.counterstest.data.database.dao

import androidx.room.*
import com.cornershop.counterstest.data.model.CounterEntity

@Dao
interface CounterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: List<CounterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CounterEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: CounterEntity)

    @Query("SELECT * FROM item_counter WHERE title LIKE '%' || :search || '%';")
    suspend fun findUserWithTitle(search: String): List<CounterEntity>

    @Query("SELECT * FROM item_counter;")
    suspend fun selectAllItems(): List<CounterEntity>

    @Query("DELETE FROM item_counter WHERE title = :title;")
    suspend fun delete(title: String)

    @Query("UPDATE  item_counter  SET selected = :isSelected WHERE title = :title ")
    suspend fun updateCounterIsSelected(title: String, isSelected: Boolean)

}