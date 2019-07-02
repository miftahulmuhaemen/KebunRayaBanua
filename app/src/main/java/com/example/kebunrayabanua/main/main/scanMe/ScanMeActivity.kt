package com.example.kebunrayabanua.main.main.scanMe

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.util.Permission.SCAN
import com.google.zxing.Result
import kotlinx.android.synthetic.main.scan_me_activity.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.jetbrains.anko.toast

class ScanMeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler, View.OnClickListener {

    override fun onClick(v: View?) {
        when(v){
            backBtn -> finish()
        }
    }

    override fun handleResult(rawResult: Result?) {
        rawResult?.text?.let { toast(it) }
    }

    private lateinit var mScannerView: ZXingScannerView

    override fun onStart() {
        mScannerView.startCamera()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                requestPermissions(arrayOf(Manifest.permission.CAMERA), SCAN)
        super.onStart()
    }

    override fun onResume() {
        mScannerView.startCamera()
        super.onResume()
    }

    override fun onPause() {
        mScannerView.stopCamera()
        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scan_me_activity)
        mScannerView = ZXingScannerView(this)
        mScannerView.setAutoFocus(true)
        mScannerView.setResultHandler(this)
        frame_layout_camera.addView(mScannerView)
        backBtn.setOnClickListener(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            SCAN -> frame_layout_camera.addView(mScannerView)
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}
