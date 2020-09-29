package com.vincent.githubusers.presenter

import android.util.Log
import android.view.View
import com.vincent.githubusers.callbacks.FavoriteListInterface
import com.vincent.githubusers.callbacks.OnProcessingCallback
import com.vincent.githubusers.database.DBAsyncTask
import com.vincent.githubusers.database.UserDatabase
import com.vincent.githubusers.model.items.ItemUser

/**
 * Created by Vincent on 2020/9/28.
 */
class FavoritePresenter {

    fun switchTheFavoriteState(userItem: ItemUser, favoriteView: View? = null) {
        DBAsyncTask(object : OnProcessingCallback {
            override fun onProcessing(): Boolean {
                val db = UserDatabase.getInstance()

                val isSuccess: Boolean

                db.getUsersDao().run {
                    isSuccess = if (isUserAddedById(userItem.id)) {
                        deleteUser(userItem) > 0
                    } else {
                        insertUser(userItem) > 0
                    }
                }
                return isSuccess
            }

            override fun onDone(isSuccess: Boolean) {
                Log.i("FavoritePresenter", "${userItem.login} switchFavoriteState isSuccess: $isSuccess")

                if (isSuccess) {
                    favoriteView?.run {
                        isSelected = !isSelected
                    }
                }
            }
        })
    }

    fun updateCurrentFavoriteUserList(favoriteListInterface: FavoriteListInterface, newUserList: List<ItemUser>) {
        favoriteListInterface.getCurrentFavoriteUserList().let {
            it.clear()
            it.addAll(newUserList)
        }
    }
}