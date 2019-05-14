package com.example.kebunrayabanua

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.kebunrayabanua.R.drawable.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
            imageView {
                imageResource = logo
            }.lparams {
                width = dip(100)
                height = dip(100)
                centerInParent()
            }
        }
    }

//    https://www.javatpoint.com/kotlin-android-firebase-authentication-google-login
//    https://medium.com/@myric.september/authenticate-using-google-sign-in-kotlin-firebase-4490f71d9e44
//    7:xH"}aB={'}'L$(

}
