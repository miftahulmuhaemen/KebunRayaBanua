package com.example.kebunrayabanua.LoginActivity.loginActivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.LoginActivity.util.util.FACEBOOK_PROVIDER
import com.example.kebunrayabanua.LoginActivity.util.util.GOOGLE_PROVIDER
import com.example.kebunrayabanua.LoginActivity.util.util.RC_SIGN_IN
import com.example.kebunrayabanua.LoginActivity.util.util.TWITTER_PROVIDER
import com.example.kebunrayabanua.LoginActivity.util.util.USERNAME
import com.example.kebunrayabanua.R
import org.jetbrains.anko.*
import com.example.kebunrayabanua.R.layout.activity_login
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    override fun onStart() {
        super.onStart()
        AuthUI.getInstance()
        .signOut(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_login)

        Glide.with(this).load(R.drawable.logo).into(img_logo)
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

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_SIGN_IN -> {
                val userName = FirebaseAuth.getInstance().currentUser
                userName?.displayName.let { toast(it + "sssss") }

            }
        }
    }

}
