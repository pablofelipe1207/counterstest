package com.cornershop.counterstest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cornershop.counterstest.data.database.dao.CounterDao
import com.cornershop.counterstest.data.model.CounterEntity

@Database(entities = arrayOf(CounterEntity::class), version = 1, exportSchema = false)
abstract class CounterDatabase : RoomDatabase() {
    abstract fun counterDao(): CounterDao
}