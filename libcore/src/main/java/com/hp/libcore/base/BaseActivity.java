package com.hp.libcore.base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.hp.libcore.tools.Util;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutID());
        ButterKnife.bind(this);
    }

    /**
     * 返回当前页面布局
     * @return
     */
    protected abstract int layoutID();

    /**
     * 检查权限
     * @param reqCode  reqCode（reqCode，接受回调）
     * @param permissions 权限的内容
     * @return 是否开启了权限
     */
    public boolean reqPermissions(int reqCode, String... permissions) {
        List<String> needRequestPermissionList = findDeniedPermissions(permissions);
        if (null != needRequestPermissionList
                && needRequestPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissionList.toArray(
                            new String[needRequestPermissionList.size()]), reqCode);
            return false;
        } else {
            onPermissionsGranted(reqCode, permissions);
            return true;
        }
    }

    /**
     * 同意权限
     * @param permissions
     */
    protected void onPermissionsGranted(int reqCode, String[] permissions) { }

    /**
     * 拒绝权限
     */
    protected void onPermissionsDenied() { }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] paramArrayOfInt) {
        if (!verifyPermissions(paramArrayOfInt)) {
            onPermissionsDenied();
        } else {
            onPermissionsGranted(requestCode, permissions);
        }
    }

    /**
     * 检测是否说有的权限都已经授权
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取权限集中需要申请权限的列表
     * @param permissions
     * @return
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissionList.add(perm);
            }
        }
        return needRequestPermissionList;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Util.cancelToast();
    }
}
