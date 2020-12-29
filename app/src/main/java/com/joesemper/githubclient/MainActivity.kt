package com.joesemper.githubclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.joesemper.githubclient.mvp.presenter.Buttons
import com.joesemper.githubclient.mvp.presenter.Presenter
import com.joesemper.githubclient.mvp.view.MainView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainView {

    private val presenter = Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listener = View.OnClickListener {
            presenter.counterClick(mapToButton(it))
        }

        btn_counter1.setOnClickListener(listener)
        btn_counter2.setOnClickListener(listener)
        btn_counter3.setOnClickListener(listener)
    }

    override fun setButtonText(button: Buttons, text: String) {
        when(button){
            Buttons.FIRST -> btn_counter1.text = text
            Buttons.SECOND -> btn_counter2.text = text
            Buttons.THIRD -> btn_counter3.text = text
        }
    }

    private fun mapToButton(v: View): Buttons {
        when(v.id) {
            R.id.btn_counter1 -> return Buttons.FIRST
            R.id.btn_counter2 -> return Buttons.SECOND
            R.id.btn_counter3 -> return Buttons.THIRD
        }
        throw Throwable(message = "Incorrect button")
    }
}