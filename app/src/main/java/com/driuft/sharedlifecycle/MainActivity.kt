package com.driuft.sharedlifecycle

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var _sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _sharedPreferences = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)
        _sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        with (_sharedPreferences.edit()) {
            putString(getString(R.string.current_lifecycle_state), "onCreate")
            apply()
        }
    }

    private val listener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            Toast.makeText(
                this,
                sharedPreferences.getString(
                    key,
                    resources.getString(R.string.default_state)
                ),
                Toast.LENGTH_SHORT
            ).show()
        }

    override fun onStart() {
        super.onStart()

        _sharedPreferences = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        with (_sharedPreferences.edit()) {
            // replace "onCreate" with the name of the method you're saving
            putString(getString(R.string.current_lifecycle_state), "onStart")
            apply()
        }

        // Read the state
        val currentState = _sharedPreferences.getString(
            getString(R.string.current_lifecycle_state),
            resources.getString(R.string.default_state)
        ) ?: resources.getString(R.string.default_state)

        // Log the state
        Log.d("Lifecycle: ", currentState)
        // Toast the state
        Toast.makeText(this, currentState, Toast.LENGTH_SHORT).show()
    }

    private fun unregisterListener() {
        _sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }
    override fun onDestroy() {
        super.onDestroy()
        // ...
        unregisterListener()
    }
    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
    }
}