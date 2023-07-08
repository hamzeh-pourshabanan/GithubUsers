package com.example.myapplication.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.User

/**
 * View Holder for a [User] RecyclerView list item.
 */
class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.repo_name)
    private val description: TextView = view.findViewById(R.id.repo_description)
    private val stars: TextView = view.findViewById(R.id.repo_stars)
    private val language: TextView = view.findViewById(R.id.repo_language)
    private val forks: TextView = view.findViewById(R.id.repo_forks)

    private var user: User? = null

    init {
        view.setOnClickListener {
            user?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(user: User?) {
        if (user == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            description.visibility = View.GONE
            language.visibility = View.GONE
            stars.text = resources.getString(R.string.unknown)
            forks.text = resources.getString(R.string.unknown)
        } else {
            showRepoData(user)
        }
    }

    private fun showRepoData(user: User) {
        this.user = user
        name.text = user.login

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (user.score != null) {
            description.text = user.score.toString()
            descriptionVisibility = View.VISIBLE
        }
        description.visibility = descriptionVisibility

        stars.text = user.score.toString()
        forks.text = user.html_url.toString()

        // if the language is missing, hide the label and the value
        var languageVisibility = View.GONE
        if (!user.followers_url.isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            language.text = resources.getString(R.string.language, user.followers_url)
            languageVisibility = View.VISIBLE
        }
        language.visibility = languageVisibility
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_view_item, parent, false)
            return UserViewHolder(view)
        }
    }
}