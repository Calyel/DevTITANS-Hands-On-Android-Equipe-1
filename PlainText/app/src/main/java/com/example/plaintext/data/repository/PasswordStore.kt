package com.example.plaintext.data.repository

import com.example.plaintext.data.dao.PasswordDao
import com.example.plaintext.data.model.Password
import com.example.plaintext.data.model.PasswordInfo
import kotlinx.coroutines.flow.Flow

interface PasswordDBStore {
    fun getList(): Flow<List<Password>>
    suspend fun add(password: Password)
    suspend fun update(password: Password)
    suspend fun get(id: Int): Password?
    suspend fun save(passwordInfo: PasswordInfo)
    fun isEmpty(): Flow<Boolean>
}

class LocalPasswordDBStore(
    private val passwordDao: PasswordDao
): PasswordDBStore {
    override fun getList(): Flow<List<Password>> {
        return passwordDao.getAll()
    }

    override suspend fun add(password: Password) {
        return passwordDao.add(password)
    }

    override suspend fun update(password: Password) {
        return passwordDao.update(password)
    }

    override suspend fun get(id: Int): Password? {
        return passwordDao.getById(id)
    }

    override suspend fun save(passwordInfo: PasswordInfo) {
        return passwordDao.save(passwordInfo)
    }

    override fun isEmpty(): Flow<Boolean> {
        return passwordDao.isEmpty()
    }
}
