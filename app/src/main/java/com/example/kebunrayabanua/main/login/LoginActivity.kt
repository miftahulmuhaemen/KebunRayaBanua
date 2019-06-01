package com.example.kebunrayabanua.main.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.main.util.Util.GOOGLE_PROVIDER
import com.example.kebunrayabanua.main.util.Util.RC_SIGN_IN
import com.example.kebunrayabanua.main.util.Util.TWITTER_PROVIDER
import com.example.kebunrayabanua.R.drawable.*
import com.example.kebunrayabanua.R.layout.activity_login
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.activity_login.*
import android.transition.TransitionManager
import android.view.View
import com.example.kebunrayabanua.main.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity


class LoginActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null){
            startActivity<MainActivity>()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_login)

        Glide.with(this).load(bg_spouse).into(background)
        Glide.with(this).load(ic_logo).into(img_logo)
        Glide.with(this).load(ic_labirin).into(mazeView)

        PushDownAnim.setPushDownAnimTo(google_signIn, facebook_signIn)
            .setOnClickListener {
                if (google_signIn == it)
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(GOOGLE_PROVIDER)
                        .build(), RC_SIGN_IN
                    )
                else
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(TWITTER_PROVIDER)
                        .build(), RC_SIGN_IN
                    )
            }

        GlobalScope.launch(Dispatchers.Main) {
            delay(1200)
            TransitionManager.beginDelayedTransition(container)
            mazeView.visibility = if(mazeView.visibility == View.GONE) View.VISIBLE else View.GONE
            buttons.visibility = if(buttons.visibility == View.GONE) View.VISIBLE else View.GONE
        }

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
