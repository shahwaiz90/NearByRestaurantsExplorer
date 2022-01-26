package com.ahmadshahwaiz.beatexplorer.presenter.ui

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmadshahwaiz.beatexplorer.R
import com.ahmadshahwaiz.beatexplorer.databinding.ActivityMapsBinding
import com.ahmadshahwaiz.beatexplorer.domain.constants.Constants
import com.ahmadshahwaiz.beatexplorer.domain.constants.MapType
import com.ahmadshahwaiz.beatexplorer.domain.constants.PinType
import com.ahmadshahwaiz.beatexplorer.presenter.adapter.RestaurantAdapter
import com.ahmadshahwaiz.beatexplorer.presenter.adapter.callback.RestaurantCallback
import com.ahmadshahwaiz.beatexplorer.presenter.viewmodel.MapsViewModel
import com.ahmadshahwaiz.core.data.model.nearbyvenue.Venues
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.math.RoundingMode

/**
 * This class loads a map screen with list of restaurants near by.
 * Flow of data is: MapsActivity->ViewModel->UseCase->MainRepo->ApiService.
 * UI Test is also written for this class to check for some constraints defined in the requirement.
 * Architecture is: MVVM + Clean
 */

@AndroidEntryPoint
class MapsActivity : BaseActivity(), OnMapReadyCallback, RestaurantCallback,
    GoogleMap.OnCameraIdleListener {

    private lateinit var binding: ActivityMapsBinding
    private var currentLatitude : Double? = null
    private var currentLongitude : Double? = null
    private var mMap: GoogleMap? = null
    private var selectedVenue: Venues? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private val mapsViewModel: MapsViewModel by viewModels()
    private val venueList = arrayListOf<Venues>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        setUpListeners()
        setUpObservers()

    }

    /**
     * This function initiates GoogleMap and sets App to Explorer Mode
     */

    private fun initViews() {
        mapsViewModel.setCurrentMapMode(MapType.EXPLORER)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * This function, sets up listener and assign array to list adapter
     */
    private fun setUpListeners() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.venueList.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = RestaurantAdapter(this@MapsActivity, mapsViewModel, this@MapsActivity, venueList, layoutInflater)
        }
        setLocationListener()
    }

    /**
     * This function checks for location permissions, if not given it asks for it from the user.
     * If permission is given, it gets the lastLocation from mFusedLocationClient class.
     */

    private fun setLocationListener(){
        if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) || !checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, requestCode = Constants.LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            mFusedLocationClient?.lastLocation?.addOnSuccessListener(this) { location ->
                if (location != null) {
                    currentLatitude = location.latitude
                    currentLongitude = location.longitude
                    val currentLatLong = LatLng(currentLatitude!!, currentLongitude!!)
                    addMarkerAndAnimateToMarker(currentLatLong, getString(R.string.you_are_here), PinType.CURRENT_LOCATION)
                }
            }
        }
    }

    /**
     * This function only job is to listen to the observers and act upon those observers.
     */

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpObservers() {
        mapsViewModel.errorMessageObserver().observe(this, {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        })

        mapsViewModel.nearByVenueObserver().observe(this, {
            binding.venueList.visibility = View.VISIBLE
            venueList.clear()
            venueList.addAll(it.venueResponse.venues)
            binding.venueList.adapter?.notifyDataSetChanged()
        })

        mapsViewModel.currentMapModelObserver().observe(this, {
            when(it){
                MapType.NAVIGATION -> {
                    supportActionBar?.title = getString(R.string.navigation_mode)
                }
                MapType.EXPLORER -> {
                    supportActionBar?.title = getString(R.string.exploration_mode)
                }
                else->{
                    supportActionBar?.title = getString(R.string.exploration_mode)
                }
            }
        })
    }

    /**
     * @param requestCode requestCode for the permission
     * @param permissions list of permissions received
     * @param grantResults result of those permissions from user
     * @return Nothing, just makes call to initiate location listener
     */

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setLocationListener()
                } else {
                    Toast.makeText(this, getString(R.string.give_location_permission), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * @param latLong is received to load location on map
     * @param title is received to show on the marker message upon click
     * @param type can be of two types i.e. CurrentLocation or Venue. Its for differentiating between two behaviours and resources.
     */

    private fun addMarkerAndAnimateToMarker(latLong: LatLng, title: String, type: PinType){
        when(type){
            PinType.CURRENT_LOCATION -> {
                mMap?.addMarker(MarkerOptions().position(latLong).title(title).icon(bitmapFromVector(this, R.drawable.ic_location_pin)))
                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, 12f))
            }
            PinType.VENUE -> {
                mMap?.addMarker(MarkerOptions().position(latLong).title(title).icon(bitmapFromVector(this, R.drawable.ic_venue_location_pin)))
                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, 17f))
            }
        }
    }

    /**
     * We used this function to create scale-able bitmap to show on google maps.
     */

    private fun bitmapFromVector(context: Context, vectorResID:Int):BitmapDescriptor {
        val vectorDrawable=ContextCompat.getDrawable(context,vectorResID)
        vectorDrawable!!.setBounds(0,0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap=Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight,Bitmap.Config.ARGB_8888)
        val canvas=Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    /**
     * This function is called from 'OnMapReadyCallback' listener, when Map is fully loading and ready to use on the screen.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.uiSettings?.setAllGesturesEnabled(true)
        mMap?.uiSettings?.isZoomGesturesEnabled = true
        mMap?.uiSettings?.isTiltGesturesEnabled = true
        mMap?.uiSettings?.isMapToolbarEnabled = true
        mMap?.uiSettings?.isScrollGesturesEnabled = true
        mMap?.uiSettings?.isMyLocationButtonEnabled = true
        mMap?.setOnCameraIdleListener(this)
    }

    /**
     * This function is called when camera is idle (have just stopped moving)
     * We call findNearByRestaurants when user stop the map after dragging it.
     */

    override fun onCameraIdle() {
        if (mapsViewModel.getCurrentMapMode() == MapType.EXPLORER) {
            findNearByRestaurants()
        }
    }

    /**
     * We call MapsViewModel after getting current lat and long where map just stopped
     **/

    private fun findNearByRestaurants() {
        val latLong = mMap?.cameraPosition?.target?.latitude?.toBigDecimal()?.setScale(2, RoundingMode.UP)
            ?.toDouble()
            .toString() + "," + mMap?.cameraPosition?.target?.longitude?.toBigDecimal()
            ?.setScale(2, RoundingMode.UP)?.toDouble().toString()

        if (mMap?.cameraPosition?.target?.latitude != 0.0 && mMap?.cameraPosition?.target?.longitude != 0.0) {
            if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) && checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                mapsViewModel.findNearByRestaurants(latLong)
            }
        }
    }

    /**
     * When user clicks on any row, this function is called. Since this class has implemented the RestaurantCallback interface
     */

    override fun onVenueClick(venues: Venues, position:Int, size: Int) {
        showVenueOverlayLayoutUseCase(venues)
    }

    /**
     * @param venues when venue is clicked, this function gets Venues model
     * This function shows an overlay on top of screen of user's selected venue
     * It also insert a new Marker in the Map of selected venue
     */

    @SuppressLint("NotifyDataSetChanged")
    private fun showVenueOverlayLayoutUseCase(venues: Venues){
        binding.venueOverlayContainer.visibility = View.VISIBLE
        binding.includedViewHolder.labelTextView.text = venues.name
        binding.includedViewHolder.distanceValue.text = venues.location.distance
        if(venues.categories?.isNotEmpty() == true) {
            binding.includedViewHolder.valueTextView.text = venues.categories!![0].name
        }
        binding.venueList.adapter?.notifyDataSetChanged()
        mMap?.clear()
        mapsViewModel.setCurrentMapMode(MapType.NAVIGATION)
        val venueLatLng = LatLng(venues.location.lat, venues.location.lng)
        addMarkerAndAnimateToMarker(venueLatLng, venues.name, PinType.VENUE)
    }

    /**
     * @param venues
     * This function saves user's selected venue in memory
     */

    override fun setSelectedVenue(venues: Venues?) {
        selectedVenue = venues
    }

    /**
     * This function get user's selected venue in memory if available otherwise it can be null
     */

    override fun getSelectedVenue(): Venues? {
        return selectedVenue
    }

    /**
     * When user press back button, this function gets called
     */

    override fun onBackPressed() {
        if(mapsViewModel.getCurrentMapMode() == MapType.NAVIGATION) {
            onBackPressedUseCase()
            return
        }
        super.onBackPressed()
    }

    /**
     * App is set to Explorer Mode and map is cleared
     * VenueOverlay layout also gets invisible
     * Map location is set to User's location again.
     */

    private fun onBackPressedUseCase(){
        mMap?.clear()
        mapsViewModel.setCurrentMapMode(MapType.EXPLORER)
        binding.venueOverlayContainer.visibility = View.GONE
        currentLatitude?.let {
            addMarkerAndAnimateToMarker(LatLng(it, currentLongitude!!),getString(R.string.you_are_here), PinType.CURRENT_LOCATION)
        }
    }
}
