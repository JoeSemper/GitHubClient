package com.joesemper.githubclient.mvp.presenter

import com.joesemper.githubclient.mvp.model.Model
import com.joesemper.githubclient.mvp.view.MainView

enum class Buttons {
    FIRST,
    SECOND,
    THIRD,
}

class Presenter(private val view: MainView) {

    private val model = Model()

    fun counterClick(button: Buttons) {
        val nextValue = model.next(button.ordinal)
        view.setButtonText(button, nextValue.toString())
    }
}