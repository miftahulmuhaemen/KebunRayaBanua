package com.example.kebunrayabanua.main.main.whereIam

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.kebunrayabanua.BuildConfig
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.util.Permission.WHEREIAM
import kotlinx.android.synthetic.main.where_iam_activity.*
import org.jetbrains.anko.AnkoLogger
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class WhereIamActivity : AppCompatActivity(), View.OnClickListener, WhereIamView, AnkoLogger {

    override fun onLocationChanged(location: Location?) {
        val point = location?.latitude?.let { GeoPoint(it, location.longitude) }
        map.controller.setCenter(point)
    }

    override fun onClick(v: View?) {
        when(v){
            backBtn -> finish()
        }
    }

    private val startPoint = GeoPoint(-3.4844549, 114.8316283)
    private lateinit var presenter: WhereIamPresenter

    override fun onStart() {
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            )
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION), WHEREIAM)
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))
        setContentView(R.layout.where_iam_activity)

        presenter = WhereIamPresenter(this,this)
        presenter.connectingToGoogleAPI()

        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.controller.setZoom(19.0)
        map.controller.setCenter(startPoint)

        val overlay = MyLocationNewOverlay(GpsMyLocationProvider(this), map)
        overlay.enableMyLocation()
        map.overlays.add(overlay)

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
                presenter.startLocationUpdates()
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

}
