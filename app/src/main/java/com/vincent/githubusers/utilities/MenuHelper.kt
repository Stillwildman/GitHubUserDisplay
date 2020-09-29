package com.vincent.githubusers.utilities

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.vincent.githubusers.R

/**
 * Created by Vincent on 2020/9/29.
 */
object MenuHelper {

    fun setMenuOptions(menu: Menu, actions: IntArray?) {
        menu.clear()

        if (actions == null) {
            return
        }

        var titleRes = 0
        var iconRes = 0

        for (action in actions) {
            when (action) {
                MenuActions.ACTION_FAVORITES -> {
                    titleRes = R.string.favorite_show_list
                    iconRes = R.drawable.selector_show_favorites
                }
            }

            if (menu.findItem(action) == null) {
                menu.add(Menu.NONE, action, Menu.NONE, titleRes)
                    .setIcon(iconRes)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

                Log.i("MenuHelper", "onMenuSet: $action")
            }
        }
    }

    fun removeMenuOptions(menu: Menu, vararg actions: Int) {
        for (action in actions) {
            menu.removeItem(action)
        }
    }

}