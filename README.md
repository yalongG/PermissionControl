# PermissionControl
引入 aar 包的时候，验证SHA1值和包名

```kotlin
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
```

根据包名 和 SHA1 值来判断 当前的aar包能否被引用。如果不匹配，直接抛出异常。

```java
class PermissionUtil {
    private static final String SHA1 = "SHA1";

    public static boolean permissionJudge(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;
            for (Signature signature : signatures) {
                byte[] hexBytes = signature.toByteArray();
                try {
                    // 可以填入 MD5 SHA1 SHA256
                    MessageDigest digest = MessageDigest.getInstance(SHA1);
                    byte[] digestBytes = digest.digest(hexBytes);
                    StringBuilder sb = new StringBuilder();
                    for (byte digestByte : digestBytes) {
                        sb.append((Integer.toHexString((digestByte & 0xFF) | 0x100)).substring(1, 3).toUpperCase()).append(":");
                    }
                    if (sb.toString().startsWith(BuildConfig.SDK_SHA1)) {
                        return true;
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}

```

获取签名文件的SHA1值、MD5值或者SHA256。

```groovy
android {
    compileSdkVersion 31
    buildToolsVersion "30.0.3"

    defaultConfig {
      // 修改 applicationId
        applicationId "com.gyl.permission.control"
//        applicationId "com.gyl.permission.control1"
		...
    }
 }
```

