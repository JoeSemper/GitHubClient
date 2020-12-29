package com.joesemper.githubclient.mvp.view

import com.joesemper.githubclient.mvp.presenter.Buttons

interface MainView {
    fun setButtonText(button: Buttons, text:String)
}