package com.skele.jetcompose.permission

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat

/**
 * Permission Handler
 */
object PermissionChecker {
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
    @Composable
    fun checkPermission(context: Context, permissions: Array<String>, launchForPermission : Boolean = true): Boolean {
        this.context = context
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission( context, permission ) != PackageManager.PERMISSION_GRANTED ) {
                if(launchForPermission) {
                    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) {
                        checkPermissionRequest(it)
                    }
                    launcher.launch(permissions)
                }
                return false
            }
        }
        return true
    }

    private val defaultRejection = OnRejectedListener{
        Toast.makeText(context, "Permission rejected.", Toast.LENGTH_SHORT).show()
        askMoveToSettings()
    }

    private val defaultGrant = OnGrantedListener{
        Toast.makeText(context, "All permissions are granted.", Toast.LENGTH_SHORT).show()
    }

    private fun checkPermissionRequest(
        result: Map<String, Boolean>,
    ){
        val onRejected = rejectionListener ?: defaultRejection
        val onGranted = grantListener ?: defaultGrant
        if(result.values.contains(false)){
            onRejected.onRejected()
        }else{
            onGranted.onGranted()
        }
    }

    fun askMoveToSettings() {
        val alertDialog = AlertDialog.Builder( context )
        alertDialog.setTitle("Permission required.")
        alertDialog.setMessage("Grant permissions in settings?")
        alertDialog.setPositiveButton("OK") { dialogInterface, _ ->
            moveToSettings()
            dialogInterface.cancel()
        }
        alertDialog.setNegativeButton("Cancel") { dialogInterface, _ -> dialogInterface.cancel() }
        alertDialog.show()
    }

    fun moveToSettings() {
        val intent =
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + context.packageName))
        context.startActivity(intent)
    }
}