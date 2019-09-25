package com.example.kebunrayabanua.main.main.scanMe

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.main.detailTree.DetailTreeActivity
import com.example.kebunrayabanua.main.util.Permission.SCAN
import com.google.zxing.Result
import kotlinx.android.synthetic.main.scan_me_activity.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.jetbrains.anko.startActivity

class ScanMeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler, View.OnClickListener {

    override fun onClick(v: View?) {
        when(v){
            backBtn -> finish()
        }
    }

    override fun handleResult(rawResult: Result?) {
        rawResult?.text?.let { startActivity<DetailTreeActivity>(
                DetailTreeActivity.TREE_DETAIL to it,
                DetailTreeActivity.IS_FROM_SCANME to 1
        ) }
    }

    private lateinit var mScannerView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scan_me_activity)
        mScannerView = ZXingScannerView(this)
        frame_layout_camera.addView(mScannerView)
    }

    override fun onStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                requestPermissions(arrayOf(Manifest.permission.CAMERA), SCAN)
        mScannerView.setAutoFocus(true)
        backBtn.setOnClickListener(this)
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    override fun onPause() {
        mScannerView.stopCamera()
        super.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            SCAN -> {
                frame_layout_camera.removeAllViews()
                frame_layout_camera.addView(mScannerView)
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}
