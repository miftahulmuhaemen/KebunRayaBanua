package com.example.kebunrayabanua

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import org.jetbrains.anko.*
import com.example.kebunrayabanua.R.layout.activity_login
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_login)

        Glide.with(this).load(R.drawable.logo).into(img_logo)
        PushDownAnim.setPushDownAnimTo(google_signIn, facebook_signIn)
            .setOnClickListener {
                if (google_signIn == it)
                    startActivityForResult(mGoogleSignInClient.signInIntent, RC_SIGN_IN)
                else
                    toast("Faceboo")
            }

        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)

        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_SIGN_IN -> {
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    val credential = GoogleAuthProvider.getCredential(account?.idToken,null)

                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
                        if(it.isSuccessful)
                            startActivity<LoginActivity>()
                        else
                            toast("Google Sign In Failed")
                    }
                } catch (e: ApiException) {
                    toast("Google Sign In Failed")
                }
            }
        }
    }


    //    https://www.javatpoint.com/kotlin-android-firebase-authentication-google-login
//    https://medium.com/@myric.september/authenticate-using-google-sign-in-kotlin-firebase-4490f71d9e44
//    7:xH"}aB={'}'L$(
}
