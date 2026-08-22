package com.example.plaintext.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.plaintext.data.model.Password
import com.example.plaintext.data.model.PasswordInfo
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PasswordDao : BaseDao<Password> {

    @Query("SELECT * FROM passwords")
    abstract fun getAll(): Flow<List<Password>>

    @Query("SELECT * FROM passwords WHERE id = :id")
    abstract suspend fun getById(id: Int): Password?

    @Query("SELECT (SELECT COUNT(*) FROM passwords) == 0")
    abstract fun isEmpty(): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun add(password: Password)

    suspend fun save(passwordInfo: PasswordInfo) {
        insert(passwordInfo.toPassword())
    }
}
