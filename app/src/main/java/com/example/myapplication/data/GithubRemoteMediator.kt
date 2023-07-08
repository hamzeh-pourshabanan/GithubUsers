package com.example.myapplication.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.myapplication.api.GithubService
import com.example.myapplication.model.User

private const val GITHUB_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class GithubRemoteMediator(
    private val query: String,
    private val service: GithubService,
) : RemoteMediator<Int, User>()  {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, User>): MediatorResult {
        TODO("Not yet implemented")
    }
}