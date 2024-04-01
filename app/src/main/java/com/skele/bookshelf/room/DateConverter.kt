package com.skele.bookshelf.room

import androidx.room.TypeConverter
import java.util.Date

//https://developer.android.com/reference/androidx/room/TypeConverters
//https://developer.android.com/training/data-storage/room/referencing-data
/**
 * Converter for Date to Timestamp and vice versa.
 * Multiple Converters can be used at same annotation scope.
 *
 * Range of usage differs on where the TypeConverter is annotated.
 *
 * On Database, all DAOs and Entities will be able to use it.
 *
 * On DAOs, all methods in DAO will be able to use it.
 *
 * On Entity, all field will be able to use it.
 *
 * On Entity field, only that field will abe able to use it.
 *
 * Also works same on DAO method parameter.
 */
class DateConverter {
    /**
     * Room database only supports Null, Int, Real, Text, and Blob.
     * In order to persist object references, convert them using TypeConverter
     */
    @TypeConverter
    fun fromTimestampToDate(time : Long?) : Date? = time?.let { Date(it) }
    @TypeConverter
    fun fromDateToTimestamp(date : Date?) : Long? = date?.time?.toLong()
}