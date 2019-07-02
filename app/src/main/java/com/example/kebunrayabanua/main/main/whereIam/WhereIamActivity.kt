package com.example.kebunrayabanua.main.main.whereIam

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.core.content.ContextCompat
import com.example.kebunrayabanua.BuildConfig
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.util.Permission.WHEREIAM
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.where_iam_activity.*
import kotlinx.android.synthetic.main.where_iam_activity.backBtn
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint

class WhereIamActivity : AppCompatActivity(), View.OnClickListener, AnkoLogger {

    override fun onClick(v: View?) {
        when(v){
            backBtn -> finish()
        }
    }

    private val startPoint = GeoPoint(-3.4844549, 114.8316283)

    override fun onStart() {
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION), WHEREIAM)
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))
        setContentView(R.layout.where_iam_activity)

        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.controller.setZoom(17.0)
        map.controller.setCenter(startPoint)

        startTrackingService()

        backBtn.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            WHEREIAM -> {
                map.onResume()
                startTrackingService()
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    private fun startTrackingService(){
        val request = LocationRequest()
        request.interval = 10000
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val client = LocationServices.getFusedLocationProviderClient(this)
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION )

        if (permission == PackageManager.PERMISSION_GRANTED)
            client.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location = locationResult.lastLocation
                    if (location != null) {
                        val startPoint = GeoPoint(location.latitude, location.longitude)
                        map.controller.setCenter(startPoint)
                        info(location.latitude)
                    }
                }
            }, null)

    }

}
