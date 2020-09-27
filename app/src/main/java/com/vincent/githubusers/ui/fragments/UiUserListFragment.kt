package com.vincent.githubusers.ui.fragments

import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vincent.githubusers.R
import com.vincent.githubusers.callbacks.PagingStatusCallback
import com.vincent.githubusers.databinding.FragmentUserListBinding
import com.vincent.githubusers.model.items.ItemUser
import com.vincent.githubusers.ui.adapters.UserListAdapter
import com.vincent.githubusers.ui.bases.BaseFragment
import com.vincent.githubusers.utilities.Utility
import com.vincent.githubusers.viewmodel.UserListViewModel

/**
 * Created by Vincent on 2020/9/24.
 */
class UiUserListFragment : BaseFragment<FragmentUserListBinding>(), PagingStatusCallback {

    private val viewModel by lazy { ViewModelProvider(this).get(UserListViewModel::class.java)}

    override fun getLayoutId(): Int = R.layout.fragment_user_list

    override fun init() {
        initRecycler()
        initViewModel()
    }

    private fun initRecycler() {
        mBinding.recyclerUserList.run {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))

            adapter = UserListAdapter(object : DiffUtil.ItemCallback<ItemUser>() {
                override fun areItemsTheSame(oldItem: ItemUser, newItem: ItemUser): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: ItemUser, newItem: ItemUser): Boolean {
                    return oldItem.id == newItem.id && oldItem.login == newItem.login
                }
            })
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

    private fun getUserListAdapter(): UserListAdapter = mBinding.recyclerUserList.adapter as UserListAdapter

    override fun onBackKeyPressed(): Boolean = true
}