package com.vincent.githubusers.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vincent.githubusers.model.DatabaseParams
import com.vincent.githubusers.model.items.ItemUser

/**
 * Created by Vincent on 2020/9/28.
 */
@Dao
interface DaoUsers {

    @Query("SELECT * FROM ${DatabaseParams.TABLE_USERS}")
    fun getLiveUserList(): LiveData<List<ItemUser>>

    @Query("SELECT EXISTS (SELECT 1 FROM ${DatabaseParams.TABLE_USERS} WHERE ${DatabaseParams.COLUMN_ID} = :id)")
    fun isUserAddedById(id: Int): Boolean

    @Query("SELECT EXISTS (SELECT 1 FROM ${DatabaseParams.TABLE_USERS} WHERE ${DatabaseParams.COLUMN_LOGIN} = :login)")
    fun isUserAddedByLogin(login: String): LiveData<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: ItemUser): Long

    @Delete
    fun deleteUser(user: ItemUser): Int
}