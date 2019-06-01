package com.example.kebunrayabanua.main.main.scanMe

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.kebunrayabanua.R
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_scan_me.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.jetbrains.anko.toast

class ScanMeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    override fun handleResult(rawResult: Result?) {
        rawResult?.text?.let { toast(it) }
    }

    private lateinit var mScannerView: ZXingScannerView

    override fun onStart() {
        mScannerView.startCamera()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_me)
        initScanView()
    }

    override fun onResume() {
        mScannerView.startCamera()
        super.onResume()
    }

    private fun initScanView(){
        mScannerView = ZXingScannerView(this)
        mScannerView.setAutoFocus(true)
        mScannerView.setResultHandler(this)
        frame_layout_camera.addView(mScannerView)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            100 -> {
                initScanView()
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    override fun onPause() {
        mScannerView.stopCamera()
        super.onPause()
    }
}
