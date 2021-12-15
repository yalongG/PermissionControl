package com.gyl.permission.puglin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (applicationInfo.packageName != BuildConfig.SDK_PACKAGE
            || !PermissionUtil.permissionJudge(this)
        ) {
            throw IllegalAccessException("包名或SHA1值匹配不上")
        }
        setContentView(R.layout.activity_demo)
    }
}