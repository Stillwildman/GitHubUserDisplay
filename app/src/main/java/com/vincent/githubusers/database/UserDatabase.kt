package com.vincent.githubusers.database

import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vincent.githubusers.AppController
import com.vincent.githubusers.database.dao.DaoUsers
import com.vincent.githubusers.model.DatabaseParams
import com.vincent.githubusers.model.items.ItemUser

/**
 * Created by Vincent on 2020/9/28.
 */
@Database(entities = [(ItemUser::class)], version = DatabaseParams.DB_VERSION)
abstract class UserDatabase : RoomDatabase() {

    companion object {
        fun getInstance() : UserDatabase {
            return Room.databaseBuilder(AppController.instance.applicationContext, UserDatabase::class.java, DatabaseParams.DB_USER)
                .addMigrations(MIGRATION_1_2)
                .build()
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.i("PoiDatabase", "onMigrating!!! Current version: " + database.version)
                // TODO Migration if needed.
                Log.i("PoiDatabase", "Migration DONE!!!")
            }
        }
    }

    abstract fun getUsersDao(): DaoUsers
}