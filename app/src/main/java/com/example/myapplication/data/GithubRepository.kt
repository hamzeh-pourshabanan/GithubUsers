package com.example.myapplication.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.RemoteMediator
import com.example.myapplication.api.GithubService
import com.example.myapplication.api.UserDetailsResponse
import com.example.myapplication.db.UserDatabase
import com.example.myapplication.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.HttpException
import java.io.IOException
import java.lang.Error

/**
 * Repository class that works with local and remote data sources.
 */
class GithubRepository(
    private val service: GithubService,
    private val database: UserDatabase
) {

    /**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    fun getSearchResultStream(query: String): Flow<PagingData<User>> {
        Log.d("GithubRepository", "New query: $query")

        // appending '%' so we can allow other characters to be before and after the query string
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { database.usersDao().usersByName(dbQuery) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = GithubRemoteMediator(
                query,
                service,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getDetails(query: String): GenericResponse {
        try {
            val details = service.getUserInfo(query)
            return GenericResponse.Success(details)
        }catch (exception: IOException) {
            return GenericResponse.Error(exception.message)
        } catch (exception: HttpException) {
            return GenericResponse.ApiError(exception.message)
        }

    }

    sealed class GenericResponse {
        data class Success(val data: UserDetailsResponse): GenericResponse()
        data class Error(val error: String?): GenericResponse()
        data class ApiError(val error: String?): GenericResponse()
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }
}