package com.example.kebunrayabanua.main.login

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.R.drawable.*
import com.example.kebunrayabanua.R.layout.login_activity
import com.example.kebunrayabanua.main.login.Login.GOOGLE_PROVIDER
import com.example.kebunrayabanua.main.login.Login.RC_SIGN_IN
import com.example.kebunrayabanua.main.login.Login.TWITTER_PROVIDER
import com.example.kebunrayabanua.main.main.MainActivity
import com.example.kebunrayabanua.main.util.gone
import com.example.kebunrayabanua.main.util.visible
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity

/*

TODO Kotlin DSL Depedency, Network Module, Maps Module, Dagger

 */

object Login {
    const val RC_SIGN_IN: Int = 1
    val GOOGLE_PROVIDER = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
    val TWITTER_PROVIDER = arrayListOf(AuthUI.IdpConfig.TwitterBuilder().build())
}

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v){
            google_signIn -> startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(GOOGLE_PROVIDER)
                .build(), RC_SIGN_IN
            )
            twitter_signIn -> startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(TWITTER_PROVIDER)
                .build(), RC_SIGN_IN
            )
        }
    }

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null){
            startActivity<MainActivity>()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(login_activity)

        Glide.with(this).load(bg_spouse).into(background)
        Glide.with(this).load(ic_logo).into(img_logo)
        Glide.with(this).load(ic_labirin).into(mazeView)

        GlobalScope.launch(Dispatchers.Main) {
            delay(1200)
            TransitionManager.beginDelayedTransition(container)
            mazeView.gone()
            buttons.visible()
        }

        google_signIn.setOnClickListener(this)
        twitter_signIn.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_SIGN_IN -> {
                if(FirebaseAuth.getInstance().currentUser != null){
                    startActivity<MainActivity>()
                    finish()
                }
            }
        }
    }

}
