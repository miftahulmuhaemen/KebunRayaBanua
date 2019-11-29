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
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.FolderOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
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

    override fun onConnectionSuspended(p0: Int) {}
    override fun onConnectionFailed(p0: ConnectionResult) {}

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


    fun mapSetup(map: MapView) {
        connectingToGoogleAPI()
        kmlOverlaying(map, R.raw.kml_batas_krb)
        map.overlays.add(myLocationOverlay(map))
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.controller.setZoom(19.0)

        val markerLat = context.resources.getStringArray(R.array.markersLat)
        val markerLong = context.resources.getStringArray(R.array.markersLong)
        val markerIcon = context.resources.obtainTypedArray(R.array.markersIcon)
        for ((index, markerTitle) in context.resources.getStringArray(R.array.markersTitle).withIndex())
            addMarker(
                map,
                GeoPoint(markerLat[index].toDouble(), markerLong[index].toDouble()),
                markerIcon.getResourceId(index, 0),
                markerTitle
            )

        markerIcon.recycle()
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

    private fun kmlOverlaying(map: MapView, marker: Int) {
        GlobalScope.launch(contextCoroutine.main) {
            try {
                val kmlDocument = KmlDocument()
                kmlDocument.parseKMLStream(
                    context.resources.openRawResource(marker),
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

    private fun myLocationOverlay(map: MapView): Overlay {
        val overlay = MyLocationNewOverlay(GpsMyLocationProvider(context), map)
        overlay.enableMyLocation()
        return overlay
    }

    private fun addMarker(map: MapView, geoPoint: GeoPoint, icon: Int, title: String) {
        GlobalScope.launch(contextCoroutine.main) {
            try {
                val marker = Marker(map)
                marker.position = geoPoint
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                marker.icon = context.resources.getDrawable(icon, null)
                marker.title = title
                map.overlays.add(marker)
                map.invalidate()
            } catch (e: IOException) {
                error(e)
            }
        }
    }
}

