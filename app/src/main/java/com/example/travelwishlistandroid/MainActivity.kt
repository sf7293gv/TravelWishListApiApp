package com.example.travelwishlistandroid

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainActivity : AppCompatActivity(), OnListItemClickedListener, OnDataChangedListener {

    private lateinit var newPlaceEditText: EditText
    private lateinit var reasonEditText: EditText
    private lateinit var addNewPlaceButton: Button
    private lateinit var placeListRecyclerView: RecyclerView

    private lateinit var placesRecyclerAdapter: PlaceRecyclerAdapter

    private val placesViewModel: PlacesViewModel by lazy {
        ViewModelProvider(this).get(PlacesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeListRecyclerView = findViewById(R.id.place_list)
        addNewPlaceButton = findViewById(R.id.add_new_place_button)
        newPlaceEditText = findViewById(R.id.new_place_name)
        reasonEditText = findViewById(R.id.reason_text)

        val places = placesViewModel.getPlaces()

        placesRecyclerAdapter = PlaceRecyclerAdapter(places, this)
        placeListRecyclerView.layoutManager = LinearLayoutManager(this)
        placeListRecyclerView.adapter = placesRecyclerAdapter

        val itemSwipeListener = OnListItemSwipeListener(this)
        val itemTouchHelper = ItemTouchHelper(itemSwipeListener)
        itemTouchHelper.attachToRecyclerView(placeListRecyclerView)



        addNewPlaceButton.setOnClickListener {
            addNewPlace()
        }

    }

    private fun addNewPlace() {
        val name = newPlaceEditText.text.toString().trim()
        val reason = reasonEditText.text.toString().trim()

        if (name.isEmpty() || reason.isEmpty()) {
            Toast.makeText(this, "Enter a place name and reason", Toast.LENGTH_SHORT).show()
        } else {
            val newPlace = Place(name)
            val positionAdded = placesViewModel.addNewPlace(newPlace)
            if (positionAdded == -1) {
                Toast.makeText(this, "Place exists", Toast.LENGTH_SHORT).show()
            } else {
                placesRecyclerAdapter.notifyItemInserted(positionAdded)
                clearForm()
                hideKeyboard()
            }
        }
    }

    private fun clearForm() {
        newPlaceEditText.text.clear()
        reasonEditText.text.clear()
    }

    private fun hideKeyboard() {
        this.currentFocus?.let {view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken,0)
        }
    }

    override fun onMapRequestButtonClicked(place: Place) {
        Toast.makeText(this, "${place.name} map icon was clicked", Toast.LENGTH_SHORT).show()
        val placeLocationUri = Uri.parse("geo:0,0?q=${place.name}")
        val mapIntent = Intent(Intent.ACTION_VIEW, placeLocationUri)
        startActivity(mapIntent)
    }

    override fun onStarredStatusChanged(place: Place, isStarred: Boolean) {
        place.starred = isStarred
        placesViewModel.updatePlace(place)
    }

//    override fun onListItemMoved(from: Int, to: Int) {
//        placesViewModel.movePlace(from, to)
//        placesRecyclerAdapter.notifyItemMoved(from, to)
//    }

    override fun onListItemDeleted(position: Int) {
        val deletedPlace = placesViewModel.deletePlace(position)
        placesRecyclerAdapter.notifyItemRemoved(position)
//
//        Snackbar.make(findViewById(R.id.wishlist_container), getString(R.string.place_deleted, deletedPlace.name), Snackbar.LENGTH_LONG).setActionTextColor(resources.getColor(R.color.red)).setBackgroundTint(resources.getColor(R.color.purple_700)).setAction(getString(R.string.undo)) {
//            // displays an undo button
//            placesViewModel.addNewPlace(deletedPlace, position)
//            placesRecyclerAdapter.notifyItemInserted(position)
//        }.show()
    }


}