package com.example.plaintext.data.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.plaintext.data.model.Password

@Dao
abstract class PasswordDao : BaseDao<Password> {

    @Query("""
        SELECT * FROM passwords WHERE name = :name
    """)
    abstract fun getByName(name: String): Flow<PasswordDao>

    @Query("""
        SELECT * FROM passwords WHERE login = :user
    """)
    abstract fun getByUser(user: String): Flow<PasswordDao>

    @Query("""
        SELECT * FROM passwords WHERE password = :password
    """)
    abstract fun getByPassword(password: String): Flow<PasswordDao>

    @Query("""
        SELECT * FROM passwords WHERE notes = :notes
    """)
    abstract fun getByNotes(notes: String): Flow<PasswordDao>

}