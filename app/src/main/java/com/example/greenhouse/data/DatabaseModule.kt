package com.example.greenhouse.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.greenhouse.data.dao.ProfileDao
import com.example.greenhouse.data.entity.EntityProfile

@Database(entities = [EntityProfile::class], version = 1, exportSchema = false)
abstract class DatabaseModule : RoomDatabase() {
abstract val profileDao: ProfileDao
}