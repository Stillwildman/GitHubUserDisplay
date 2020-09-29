package com.vincent.githubusers.ui.fragments

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vincent.githubusers.R
import com.vincent.githubusers.callbacks.FavoriteListInterface
import com.vincent.githubusers.callbacks.OnFavoriteClickCallback
import com.vincent.githubusers.callbacks.OnUserClickCallback
import com.vincent.githubusers.database.UserDatabase
import com.vincent.githubusers.databinding.FragmentFavoriteListBinding
import com.vincent.githubusers.model.Const
import com.vincent.githubusers.model.items.ItemUser
import com.vincent.githubusers.presenter.FavoritePresenter
import com.vincent.githubusers.ui.adapters.UserListAdapter
import com.vincent.githubusers.ui.bases.BaseFragment

/**
 * Created by Vincent on 2020/9/29.
 */
class UiFavoriteListFragment : BaseFragment<FragmentFavoriteListBinding>(), OnUserClickCallback, OnFavoriteClickCallback, FavoriteListInterface {

    private val currentFavoriteUserList = arrayListOf<ItemUser>()

    private val favoritePresenter by lazy { FavoritePresenter() }

    override fun getLayoutId(): Int = R.layout.fragment_favorite_list

    override fun getMenuOptions(): IntArray? = null

    override fun init() {
        initRecycler()
    }

    override fun onResume() {
        super.onResume()
        observeLiveFavoriteList()
    }

    private fun initRecycler() {
        mBinding.recyclerFavoriteList.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
            it.adapter = UserListAdapter(this, this, this)
        }
    }

    private fun observeLiveFavoriteList() {
        UserDatabase.getInstance().getUsersDao().getLiveUserList().observe(this, { userList: List<ItemUser> ->
            if (userList.isEmpty()) {
                showNoFavoritesHint()
            }
            favoritePresenter.updateCurrentFavoriteUserList(this, userList)
            getUserListAdapter().updateList(userList)

            Log.i(TAG, "onChanged! Size: ${userList.size}")
        })
    }

    private fun showNoFavoritesHint() {
        if (!mBinding.viewStubNoFavoriteHint.isInflated) {
            mBinding.viewStubNoFavoriteHint.viewStub?.inflate()
        }
    }

    override fun onLoginGet(login: String) {
        openFragment(UiUserDetailFragment.newInstance(login), false, Const.BACK_USER_DETAIL)
    }

    override fun onFavoriteClick(userItem: ItemUser, favoriteView: View) {
        favoritePresenter.switchTheFavoriteState(userItem)
    }

    override fun getCurrentFavoriteUserList(): MutableList<ItemUser> = currentFavoriteUserList

    private fun getUserListAdapter(): UserListAdapter = mBinding.recyclerFavoriteList.adapter as UserListAdapter

    override fun onBackKeyPressed(): Boolean = false
}