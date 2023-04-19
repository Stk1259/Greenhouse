package com.example.greenhouse.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.greenhouse.data.entity.EntityProfile

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile WHERE id = :id")
    suspend fun getProfileData(id: Int): EntityProfile

    @Query("INSERT INTO profile (id, phone, name, username) VALUES (:id, :phone, :name, :userName)")
    suspend fun insertRegistration(id: Int, phone: String, name: String, userName: String)

    @Query("UPDATE profile SET name = :name WHERE id = :id")
    suspend fun updateName(id: Int, name: String)

    @Query("UPDATE profile SET city = :city WHERE id = :id")
    suspend fun updateCity(id: Int, city: String)

    @Query("UPDATE profile SET birthday = :birthday WHERE id = :id")
    suspend fun updateBirthday(id: Int, birthday: String)

    @Query("UPDATE profile SET status = :about WHERE id = :id")
    suspend fun updateAbout(id: Int, about: String)

    @Query("UPDATE profile SET avatar = :avatar , avatar_name = :avatarName WHERE id = :id")
    suspend fun updateAvatar(id: Int, avatar: String, avatarName: String)
}