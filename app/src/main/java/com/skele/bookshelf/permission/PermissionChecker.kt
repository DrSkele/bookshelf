package com.skele.bookshelf.permission

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

/**
 * Permission Handler
 * @param activityOrFragment pass an activity or fragment for showing permission dialogs.
 */
class PermissionChecker(private val activityOrFragment : Any?) {
    private lateinit var context: Context

    private var grantListener : OnGrantedListener? = null
    private var rejectionListener : OnRejectedListener? = null

    /**
     * Set a listener for permission grant event.
     * Called when all permissions are granted.
     * If one or more permissions are rejected, RejectedListener is called instead.
     * By default, a toast is shown on granted.
     */
    fun setOnGrantedListener(listener: OnGrantedListener){
        grantListener = listener
    }

    /**
     * Set a listener for permission rejection event.
     * Called when one or more permissions are rejected.
     * By default, a toast is shown on granted.
     */
    fun setOnRejectedListener(listener: OnRejectedListener){
        rejectionListener = listener
    }

    /**
     * Checks for permissions.
     * @param context context for permission check.
     * @param permissions array of permissions to check. ( see Manifest.permissions )
     * @param launchForPermission whether to launch a permission request on permissions are not granted.
     */
    fun checkPermission(context: Context, permissions: Array<String>, launchForPermission : Boolean = true): Boolean {
        this.context = context
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission( context, permission ) != PackageManager.PERMISSION_GRANTED ) {
                if(launchForPermission) requestPermissionLauncher.launch(permissions)
                return false
            }
        }
        return true
    }

    private val requestPermissionLauncher: ActivityResultLauncher<Array<String>> = when (activityOrFragment) {
        is AppCompatActivity -> {
            activityOrFragment.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()){
                checkPermissionRequest(it)
            }
        }

        is Fragment -> {
            activityOrFragment.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()){
                checkPermissionRequest(it)
            }
        }

        else -> {
            throw RuntimeException("Requires Activity or Fragment.")
        }
    }

    private val defaultRejection = OnRejectedListener{
        Toast.makeText(context, "Permission rejected.", Toast.LENGTH_SHORT).show()
        moveToSettings()
    }

    private val defaultGrant = OnGrantedListener{
        Toast.makeText(context, "All permissions are granted.", Toast.LENGTH_SHORT).show()
    }

    private fun checkPermissionRequest(result: Map<String, Boolean>){
        val onRejected = rejectionListener ?: defaultRejection
        val onGranted = grantListener ?: defaultGrant
        if(result.values.contains(false)){
            onRejected.onRejected()
        }else{
            onGranted.onGranted()
        }
    }

    fun moveToSettings() {
        val alertDialog = AlertDialog.Builder( context )
        alertDialog.setTitle("Permission required.")
        alertDialog.setMessage("Grant permissions in settings?")
        alertDialog.setPositiveButton("OK") { dialogInterface, _ ->
            val intent =
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + context.packageName))
            context.startActivity(intent)
            dialogInterface.cancel()
        }
        alertDialog.setNegativeButton("Cancel") { dialogInterface, _ -> dialogInterface.cancel() }
        alertDialog.show()
    }
}