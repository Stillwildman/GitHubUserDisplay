package com.vincent.githubusers.ui.fragments

import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vincent.githubusers.R
import com.vincent.githubusers.callbacks.FavoriteListInterface
import com.vincent.githubusers.callbacks.OnFavoriteClickCallback
import com.vincent.githubusers.callbacks.OnUserClickCallback
import com.vincent.githubusers.callbacks.PagingStatusCallback
import com.vincent.githubusers.database.UserDatabase
import com.vincent.githubusers.databinding.FragmentUserListBinding
import com.vincent.githubusers.model.Const
import com.vincent.githubusers.model.items.ItemUser
import com.vincent.githubusers.presenter.FavoritePresenter
import com.vincent.githubusers.ui.adapters.UserListAdapter
import com.vincent.githubusers.ui.bases.BaseFragment
import com.vincent.githubusers.utilities.Utility
import com.vincent.githubusers.viewmodel.UserListViewModel

/**
 * Created by Vincent on 2020/9/24.
 */
class UiUserListFragment : BaseFragment<FragmentUserListBinding>(), PagingStatusCallback, OnUserClickCallback, OnFavoriteClickCallback, FavoriteListInterface {

    private val viewModel by lazy { ViewModelProvider(this).get(UserListViewModel::class.java)}

    private val currentFavoriteUserList = arrayListOf<ItemUser>()

    private val favoritePresenter by lazy { FavoritePresenter() }

    override fun getLayoutId(): Int = R.layout.fragment_user_list

    override fun init() {
        initRecycler()
        initViewModel()
        observeLiveFavoriteUserList()
    }

    private fun initRecycler() {
        mBinding.recyclerUserList.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))

            it.adapter = UserListAdapter(this, this, this)
        }
    }

    private fun initViewModel() {
        viewModel.setStatusCallback(this)
        observeStatus()
    }

    private fun observeStatus() {
        viewModel.let {
            it.getUserList().observe(this, { pagedUserList: PagedList<ItemUser>? ->
                getUserListAdapter().updateList(pagedUserList)
            })

            it.liveLoadingStatus.observe(this, { isLoading ->
                if (isLoading) showLoading() else hideLoading()
            })

            it.liveErrorMessage.observe(this, { errorMessage ->
                Utility.toastShort(errorMessage)
            })
        }
    }

    override fun onLoading(isLoading: Boolean) {
        viewModel.liveLoadingStatus.postValue(isLoading)
    }

    override fun onFailed(errorMessage: String?) {
        errorMessage?.let {
            viewModel.liveErrorMessage.value = it
        }
    }

    override fun onLoginGet(login: String) {
        openFragment(UiUserDetailFragment.newInstance(login), false, Const.BACK_USER_DETAIL)
    }

    override fun onFavoriteClick(userItem: ItemUser, favoriteView: View) {
        favoritePresenter.switchTheFavoriteState(userItem, favoriteView)
    }

    private fun observeLiveFavoriteUserList() {
        UserDatabase.getInstance().getUsersDao().getLiveUserList().observe(this, { userList: List<ItemUser> ->
            favoritePresenter.updateCurrentFavoriteUserList(this, userList)
            notifyFavoritesChanged()
        })
    }

    override fun getCurrentFavoriteUserList(): MutableList<ItemUser> = currentFavoriteUserList

    private fun notifyFavoritesChanged() {
        getUserListAdapter().notifyFavoritesChanged()
    }

    private fun getUserListAdapter(): UserListAdapter = mBinding.recyclerUserList.adapter as UserListAdapter

    override fun onResume() {
        super.onResume()
        observeLiveFavoriteUserList()
    }

    override fun onBackKeyPressed(): Boolean = true
}