package com.ahmadshahwaiz.beatexplorer.presenter.ui

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

open class PermissionActivity : AppCompatActivity() {

    fun requestPermission(vararg permissionsArray: String, requestCode: Int) {
        ActivityCompat.requestPermissions(this, permissionsArray, requestCode)
    }
    fun checkPermission(permission: String): Boolean = ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}