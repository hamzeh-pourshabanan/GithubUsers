package com.example.myapplication

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.myapplication.api.GithubService
import com.example.myapplication.data.GithubRepository
import com.example.myapplication.db.UserDatabase

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {
    /**
     * Creates an instance of [GithubRepository] based on the [GithubService] and a
     * [GithubLocalCache]
     */
    private fun provideGithubRepository(context: Context): GithubRepository {
        return GithubRepository(GithubService.create(), UserDatabase.getInstance(context))
    }
}