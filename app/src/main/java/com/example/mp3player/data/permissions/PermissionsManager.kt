package com.example.mp3player.data.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionsManager {

    companion object {

        private const val PERMISSIONS_REQUEST_CODE = 820

        fun hasReadExternalStoragePermission(context: Context) =
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED

        fun requestPermissions(activity: Activity) {
            if (!hasReadExternalStoragePermission(activity)) {
                ActivityCompat.requestPermissions(
                    activity,
                    listOf(Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray(),
                    PERMISSIONS_REQUEST_CODE
                )
            }
        }

        fun onRequest(
            requestCode: Int,
            grantResults: IntArray
        ): Boolean {
            if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    return true
                }
            }
            return false
        }
    }

}