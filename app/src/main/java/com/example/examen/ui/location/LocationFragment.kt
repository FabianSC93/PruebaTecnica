package com.example.examen.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.examen.R
import com.example.examen.databinding.LocationFragmentBinding
import com.example.examen.ui.notifications.PushNotifications
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class LocationFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mBinding: LocationFragmentBinding
    private lateinit var map: GoogleMap
    private lateinit var mContext: Context
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    @Inject internal lateinit var notifications: PushNotifications

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = LocationFragmentBinding.inflate(inflater, container, false)
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        supportMapFragment?.getMapAsync(this)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initElements()
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        checkLocationPermission()
    }

    private fun initElements() {
        mContext = mBinding.root.context
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext)

    }

    private fun checkLocationPermission() {
        if(!::map.isInitialized) return
        if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            goToCurrentLocation()
        } else {
            requestLocationPermissions()
        }
    }

    private fun requestLocationPermissions() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.ACCESS_FINE_LOCATION)) {
            AlertDialog.Builder(mContext)
                .setTitle(R.string.title_dialog_permissions)
                .setMessage(R.string.message_dialog_permissions)
                .setPositiveButton(R.string.button_permissions) { _, _ ->
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        CODE_LOCATION
                    )
                }
                .create()
                .show()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), CODE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                goToCurrentLocation()
            }else {
                Toast.makeText(mContext, R.string.location_denied, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun goToCurrentLocation() {
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                if(location != null) {
                    Toast.makeText(mContext, R.string.location_accepted, Toast.LENGTH_SHORT).show()
                    map.addMarker(MarkerOptions().position(LatLng(location.latitude, location.longitude)))
                    map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 18f),
                        4000,
                        null
                    )
                }
            }
        notifications.notificationsBuilder(
            R.drawable.ic_location,
            getString(R.string.title_notifications),
            getString(R.string.message_notifications, DateFormat.format("dd-MM-yyyy", Calendar.getInstance().timeInMillis).toString())
        )
    }

    companion object {
        const val CODE_LOCATION = 1
    }

}