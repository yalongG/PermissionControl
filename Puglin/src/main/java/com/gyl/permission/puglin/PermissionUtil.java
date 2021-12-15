package com.gyl.permission.puglin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author guoyalong
 * @time 2021/12/15
 * @desc //TODO
 */
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
