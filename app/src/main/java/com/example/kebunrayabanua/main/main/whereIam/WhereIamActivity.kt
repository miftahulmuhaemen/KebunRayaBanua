package com.example.kebunrayabanua.main.main.whereIam

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.kebunrayabanua.BuildConfig
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.util.Permission.WHEREIAM
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import kotlinx.android.synthetic.main.where_iam_activity.*
import org.jetbrains.anko.AnkoLogger
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint


class WhereIamActivity : AppCompatActivity(), View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, AnkoLogger {

    override fun onConnected(p0: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onClick(v: View?) {
        when(v){
            backBtn -> finish()
        }
    }

    private var googleApiClient: GoogleApiClient? = null
    private val startPoint = GeoPoint(-3.4844549, 114.8316283)
    private var UPDATE_INTERVAL: Long = 10 * 1000  /* 10 secs */
    private var FASTEST_INTERVAL: Long = 2000 /* 2 sec */

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

        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.controller.setZoom(17.0)
        map.controller.setCenter(startPoint)

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
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }


    @Synchronized
    private fun connectingToGoogleAPI() {
        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, 0, this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        googleApiClient?.connect()
    }

    private fun startLocationUpdates() {

        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = UPDATE_INTERVAL
        locationRequest.fastestInterval = FASTEST_INTERVAL

        // Create LocationSettingsRequest object using location request
        val builder = LocationSettingsRequest.Builder()
        val locationSettingsRequest = builder.addLocationRequest(locationRequest).build()

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permission == PackageManager.PERMISSION_GRANTED)
            getFusedLocationProviderClient(this).requestLocationUpdates(
                locationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult?) {
//                        onLocationChanged(locationResult!!.lastLocation)
                    }
                },
                Looper.myLooper()
            )
    }

//    private fun startTrackingService(){
//
//        val locationRequest = LocationRequest.create()
//        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        locationRequest.interval = (30 * 1000).toLong()
//        locationRequest.fastestInterval = (5 * 1000).toLong()
//        val builder = LocationSettingsRequest.Builder()
//            .addLocationRequest(locationRequest)
//
//        builder.setAlwaysShow(true)
//        val result = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build())
//        result.addOnCompleteListener { task ->
//            try {
//                task.getResult(ApiException::class.java)
//            } catch (exception: ApiException) {
//                when (exception.statusCode) {
//                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
//                        val resolvable = exception as ResolvableApiException
//                        resolvable.startResolutionForResult(this@YourActivity, 100)
//                    } catch (e: IntentSender.SendIntentException) {
//                        Log.d(TAG, e.message)
//                    } catch (e: ClassCastException) {
//                        Log.d(TAG, e.message)
//                    }
//
//                }
//            }
//        }
//    }


//            val request = LocationRequest()
//            request.interval = 10000
//            request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//
//            val client = LocationServices.getFusedLocationProviderClient(this)
//            val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//
//            if (permission == PackageManager.PERMISSION_GRANTED)
//                client.requestLocationUpdates(request, object : LocationCallback() {
//                    override fun onLocationResult(locationResult: LocationResult) {
//                        val location = locationResult.lastLocation
//                        if (location != null) {
//                            val startPoint = GeoPoint(location.latitude, location.longitude)
//                            map.controller.setCenter(startPoint)
//                            info(location.latitude)
//                        }
//                    }
//                }, null)


//    val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//    if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
//    alert("Hi, we can't use your GPS Tracker", "You want to turn on your GPS Tracker?") {
//        yesButton { startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
//        noButton { }
//    }.show()
//    else {

}
