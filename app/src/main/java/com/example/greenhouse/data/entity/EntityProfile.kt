package com.example.greenhouse.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class EntityProfile(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int?,
    @ColumnInfo(name = "avatar_name")
    val avatarName: String?,
    @ColumnInfo(name = "birthday")
    val birthday: String?,
    @ColumnInfo(name = "city")
    val city: String?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "phone")
    val phone: String?,
    @ColumnInfo(name = "status")
    val status: String?,
    @ColumnInfo(name = "username")
    val username: String?,
    @ColumnInfo(name = "avatar")
    val avatar: String?
)