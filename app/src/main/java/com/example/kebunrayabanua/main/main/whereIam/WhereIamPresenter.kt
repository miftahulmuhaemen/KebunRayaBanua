package com.example.kebunrayabanua.main.main.whereIam

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.kebunrayabanua.main.util.IntervalTime.FASTEST_INTERVAL
import com.example.kebunrayabanua.main.util.IntervalTime.UPDATE_INTERVAL
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class WhereIamPresenter(private val context: Context, private val view: WhereIamView) :
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, AnkoLogger {

    private var googleApiClient: GoogleApiClient? = null

    override fun onConnected(p0: Bundle?) {
        startLocationUpdates()
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

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

        val permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
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

}