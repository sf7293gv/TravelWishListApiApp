package com.example.travelwishlistandroid

import android.util.Log
import androidx.lifecycle.ViewModel

const val TAG = "PLACES_VIEW_MODEL"

class PlacesViewModel: ViewModel() {

//    private val places = mutableListOf<Place>(Place("Amman"), Place("Minneapolis"))

    fun getPlaces() {
//        return places // smart cast
    }

    fun addNewPlace(place: Place) {
        // return location in the list that the new item was added

//        if (places.any { placeName -> placeName.name.uppercase() == place.name.uppercase()}) {
//            return -1
//        }
//
//        if (position == null) {
//            places.add(place) // this adds at the end
//            return places.lastIndex
//        } else {
//            places.add(position, place)
//            return position
//        }
    }
//
//
//
//        // todo avoid duplicates
//        // todo implement add at position
//    }

//    fun movePlace(from: Int, to: Int) {
//        val place = places.removeAt(from)
//        places.add(to, place)
//        Log.d(TAG, places.toString())
//    }

    fun deletePlace(place: Place) {
//        return places.removeAt(from)
    }

    fun updatePlace(place: Place) {
        // todo
    }

}