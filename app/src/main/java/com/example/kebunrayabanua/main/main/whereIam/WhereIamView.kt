package com.example.kebunrayabanua.main.main.whereIam

import android.location.Location

interface WhereIamView {
    fun onLocationChanged(location: Location?)
}