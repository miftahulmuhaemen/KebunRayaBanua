package com.example.kebunrayabanua.LoginActivity.LoginActivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.LoginActivity.MainActivity.MainActivity
import com.example.kebunrayabanua.R
import org.jetbrains.anko.*
import com.example.kebunrayabanua.R.layout.activity_login
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val RC_SIGN_IN: Int = 1
    val GOOGLE_PROVIDER = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
    val FACEBOOK_PROVIDER = arrayListOf(AuthUI.IdpConfig.FacebookBuilder().build())

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
                        .setAvailableProviders(FACEBOOK_PROVIDER)
                        .build(), RC_SIGN_IN
                    )
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_SIGN_IN -> {
                val user = FirebaseAuth.getInstance().currentUser
                user?.displayName?.let { toast(it) }
            }
        }
    }

}
