package com.example.myapplication.ui.search

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.ui.UiModel

class UsersAdapter(private inline val action: (String) -> Unit) : PagingDataAdapter<UiModel, UserViewHolder>(UIMODEL_COMPARATOR)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent, action = action)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val uiModel = getItem(position)
        holder.bind((uiModel as UiModel.UserItem).user)
    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<UiModel>() {
            override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return (oldItem is UiModel.UserItem && newItem is UiModel.UserItem &&
                        oldItem.user.id == newItem.user.id && oldItem.user.login == newItem.user.login)
            }

            override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean =
                oldItem == newItem
        }
    }
}