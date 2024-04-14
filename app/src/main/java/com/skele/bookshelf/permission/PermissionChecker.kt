package com.skele.bookshelf.permission

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

/**
 * Permission Handler
 * Uses EasyPermissions plugin
 */
class PermissionChecker(val host : Fragment, val context : Context) : EasyPermissions.PermissionCallbacks {
    companion object{
        const val PERMISSION_REQUEST_CODE = 1
    }
    fun hasPermission() =
        EasyPermissions.hasPermissions(
            context,
            android.Manifest.permission.POST_NOTIFICATIONS
        )
    fun requestPermission() =
        EasyPermissions.requestPermissions(
            host,
            "On Permission Denied message",
            PERMISSION_REQUEST_CODE,
            android.Manifest.permission.POST_NOTIFICATIONS
        )
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(host, perms)){
            SettingsDialog.Builder(context).build().show()
        } else {
            requestPermission()
        }
    }
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
    }
}