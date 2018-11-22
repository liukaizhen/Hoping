package debug;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.erly.simplemodule.simple.SimpleModuleActivity;

/**
 * 业务模块单独调试时的启动页面
 * 直接从该页面跳转到业务模块的主页面
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashActivity.this,SimpleModuleActivity.class));
        finish();
    }
}
