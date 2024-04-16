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
 * Uses EasyPermissions plugin
 */
class PermissionChecker(val activityOrFragment : Any?) {
    private lateinit var context: Context

    private var grantListener : OnGrantedListener? = null
    private var rejectionListener : OnRejectedListener? = null
    fun setOnGrantedListener(listener: OnGrantedListener){
        grantListener = listener
    }
    fun setOnRejectedListener(listener: OnRejectedListener){
        rejectionListener = listener
    }

    // 권한 체크
    fun checkPermission(context: Context, permissions: Array<String>): Boolean {
        this.context = context
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission( context, permission ) != PackageManager.PERMISSION_GRANTED ) {
                return false
            }
        }

        return true
    }

    // 권한 호출한 이후 결과받아서 처리할 Launcher (startPermissionRequestResult )
    val requestPermissionLauncher: ActivityResultLauncher<Array<String>> = when (activityOrFragment) {
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
        Toast.makeText(context, "모든 권한이 허가되었습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun checkPermissionRequest(result: Map<String, Boolean>){
        val onRejected = rejectionListener ?: defaultRejection
        val onGranted = grantListener ?: defaultGrant
        if(result.values.contains(false)){ //false가 있는 경우라면..
            onRejected.onRejected()
        }else{
            grantListener?.onGranted()
        }
    }

    //사용자가 권한을 허용하지 않았을때, 설정창으로 이동
    fun moveToSettings() {
        val alertDialog = AlertDialog.Builder( context )
        alertDialog.setTitle("권한이 필요합니다.")
        alertDialog.setMessage("설정으로 이동합니다.")
        alertDialog.setPositiveButton("확인") { dialogInterface, i -> // 안드로이드 버전에 따라 다를 수 있음.
            val intent =
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + context.packageName))
            context.startActivity(intent)
            dialogInterface.cancel()
        }
        alertDialog.setNegativeButton("취소") { dialogInterface, i -> dialogInterface.cancel() }
        alertDialog.show()
    }
}