package com.example.myapplication.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.myapplication.api.GithubService
import com.example.myapplication.api.IN_QUALIFIER
import com.example.myapplication.db.RemoteKeys
import com.example.myapplication.db.UserDatabase
import com.example.myapplication.model.User
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class GithubRemoteMediator(
    private val query: String,
    private val service: GithubService,
    private val userDatabase: UserDatabase
) : RemoteMediator<Int, User>()  {

    val visitedPages = mutableMapOf<Int, String>()
    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, User>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = userDatabase.withTransaction {
                    userDatabase.remoteKeysDao().remoteKeysUserId(query)
                }
                remoteKeys?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }
            LoadType.APPEND -> {
                val remoteKey = userDatabase.withTransaction {
                    userDatabase.remoteKeysDao().remoteKeysUserId(query)

                }
//                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKey?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                }
                nextKey
            }
        }


        val apiQuery = query + IN_QUALIFIER

            try {
                val apiResponse = service.searchUsers(apiQuery, page, state.config.pageSize)
                val users = apiResponse.items
                val endOfPaginationReached = users.isEmpty()

                userDatabase.withTransaction {
                    // clear all tables in the database
                    if (loadType == LoadType.REFRESH) {
                        userDatabase.remoteKeysDao().clearRemoteKeys()
                        val deletedUsers = userDatabase.usersDao().clearUsers()
                    }
                    val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val key = RemoteKeys(query = query, prevKey = prevKey, nextKey = nextKey)
                    userDatabase.usersDao().insertAll(users)
                    userDatabase.remoteKeysDao().insertOrReplace(key)

                }
                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } catch (exception: IOException) {
                return MediatorResult.Error(exception)
            } catch (exception: HttpException) {
                return MediatorResult.Error(exception)
            }


    }
}