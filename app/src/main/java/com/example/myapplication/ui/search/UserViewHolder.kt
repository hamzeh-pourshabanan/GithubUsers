package com.example.myapplication.ui.search

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
class UserViewHolder(view: View, action: (String) -> Unit) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.repo_name)
    private val description: TextView = view.findViewById(R.id.repo_description)


    private var user: User? = null

    init {
        view.setOnClickListener {
            user?.login?.let { login ->
                action.invoke(login)
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                view.context.startActivity(intent)
            }
        }
    }

    fun bind(user: User?) {
        if (user == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            description.visibility = View.GONE

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
    }

    companion object {
        fun create(parent: ViewGroup, action: (String) -> Unit): UserViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_view_item, parent, false)
            return UserViewHolder(view, action)
        }
    }
}