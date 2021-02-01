package com.joesemper.githubclient.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joesemper.githubclient.R
import com.joesemper.githubclient.mvp.presenter.list.IRepositoryListPresenter
import com.joesemper.githubclient.mvp.view.list.RepositoryItemView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_repository.view.*


class RepositoriesRVAdapter(val presenter : IRepositoryListPresenter) :
    RecyclerView.Adapter<RepositoriesRVAdapter.ViewHolder>() {

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer, RepositoryItemView {
        override var pos = -1

        override fun setName(name: String) = with(containerView) {
            tv_repository_name.text = name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false))

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        holder.containerView.setOnClickListener { presenter.itemClickListener?.invoke(holder) }
        presenter.bindView(holder)
    }
}