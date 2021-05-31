package com.cornershop.counterstest.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cornershop.counterstest.data.common.DomainMapper
import com.cornershop.counterstest.data.database.COUNTER_TABLE_NAME
import com.cornershop.counterstest.domain.model.Counter

@Entity(tableName = COUNTER_TABLE_NAME)
data class CounterEntity(
    @ColumnInfo(name = "id")
    val id: String = "",
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "count")
    val count: Int,
    @ColumnInfo(name = "selected")
    val isSelected: Boolean = false
) : DomainMapper<Counter> {
    override fun mapToDomainModel() =
        Counter(id = id, title = title, count = count, isSelected = isSelected)

    companion object {
        fun mapToCounterEntity(counter: Counter) = CounterEntity(
            id = counter.id,
            title = counter.title,
            count = counter.count,
            isSelected = counter.isSelected
        )
    }
}