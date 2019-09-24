package com.example.kebunrayabanua.main.main.whereIam

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.util.CoroutineContextProvider
import com.example.kebunrayabanua.main.util.IntervalTime.FASTEST_INTERVAL
import com.example.kebunrayabanua.main.util.IntervalTime.UPDATE_INTERVAL
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.osmdroid.bonuspack.kml.KmlDocument
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.FolderOverlay
import java.io.IOException

class WhereIamPresenter(
    private val context: Context, private val view: WhereIamView,
    private val contextCoroutine: CoroutineContextProvider = CoroutineContextProvider()
) :
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, AnkoLogger {

    override fun onConnected(p0: Bundle?) {
        startLocationUpdates()
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    private var googleApiClient: GoogleApiClient? = null

    @Synchronized
    fun connectingToGoogleAPI() {
        googleApiClient = GoogleApiClient.Builder(context)
            .enableAutoManage(context as FragmentActivity, 0, this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        googleApiClient?.connect()
    }

    fun startLocationUpdates() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = UPDATE_INTERVAL
        locationRequest.fastestInterval = FASTEST_INTERVAL

        val builder = LocationSettingsRequest.Builder()
        val locationSettingsRequest = builder.addLocationRequest(locationRequest).build()
        val settingsClient = LocationServices.getSettingsClient(context)
        settingsClient.checkLocationSettings(locationSettingsRequest)
        val permission =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permission == PackageManager.PERMISSION_GRANTED)
            LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(
                locationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult?) {
                        view.onLocationChanged(locationResult?.lastLocation)
                    }
                },
                Looper.myLooper()
            )
    }

    fun kmlOverlaying(map: MapView) {
        GlobalScope.launch(contextCoroutine.main) {
            try {
                val kmlDocument = KmlDocument()
                kmlDocument.parseKMLStream(
                    context.resources.openRawResource(R.raw.kml_batas_krb),
                    null
                )
                val boundingBox = kmlDocument.mKmlRoot.boundingBox
                val kmlOverlay =
                    kmlDocument.mKmlRoot.buildOverlay(map, null, null, kmlDocument) as FolderOverlay
                map.overlays.add(kmlOverlay)
                map.zoomToBoundingBox(boundingBox, true)
                map.invalidate()
            } catch (e: IOException) {
                error(e)
            }
        }
    }
}