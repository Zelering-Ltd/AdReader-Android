package com.zelering.AdReader.lib.ui.ActivityList;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zelering.AdReader.Permission;
import com.zelering.AdReader.PermissionSupportActivity;
import com.zelering.AdReader.lib.R;
import com.zelering.AdReader.lib.app.CloudRecognition.CloudReco;
import com.zelering.AdReader.lib.model.Config;

public class ActivityAdreadHome extends PermissionSupportActivity implements Permission.PermissionCallback {
    private static String vuforia_lic_key;
    private static int imgLogoInt = R.drawable.logo_vuforia;

    private static String kAccessKey;
    private static String kSecretKey;
    private static Config config;
    TextView tv_Title, tv_heading, tv_description;
    Button btn_start;
    PercentRelativeLayout PLroot;
    private ImageView imgBackArrow, imgLogo;

    public static final String KEY_SECRETKEY = "SECRETKEY";
    public static final String KEY_VUFORIA_LIC_KEY = "VUFORIA_LIC_KEY";
    public static final String KEY_ACCESSKEY = "ACCESSKEY";
    public static final String KEY_CONFIG = "CONFIG";
    private int REQUEST_CODE = 1250;
    private String[] Pemissions = new String[]{Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getDataFromIntent();

        tv_Title = (TextView) findViewById(R.id.tv_Title);
        tv_heading = (TextView) findViewById(R.id.tv_heading);
        tv_description = (TextView) findViewById(R.id.tv_description);
        PLroot = (PercentRelativeLayout) findViewById(R.id.PLroot);
        imgBackArrow = (ImageView) findViewById(R.id.imgBackArrow);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);


        btn_start = (Button) findViewById(R.id.btn_start);
        if (config != null) {
            tv_Title.setTextColor(Color.parseColor(config.getTitleColor()));
            tv_heading.setTextColor(Color.parseColor(config.getHeadingColor()));
            tv_description.setTextColor(Color.parseColor(config.getTexColor()));
            PLroot.setBackgroundColor(Color.parseColor(config.getBackgroundColor()));
            btn_start.setTextColor(Color.parseColor(config.getTitleColor()));
            Drawable btn_bg = btn_start.getBackground();
            btn_bg.setColorFilter(Color.parseColor(config.getButtonColorColor()), PorterDuff.Mode.SRC_ATOP);
            btn_start.setBackground(btn_bg);
            imgLogo.setImageDrawable(this.getResources().getDrawable(config.getApplicationLogo()));
            tv_Title.setText(config.getHomeTitle());
            tv_heading.setText((config.getHeading()));
            tv_description.setText(config.getDiscription());
            btn_start.setText(config.getBtnStart());
            imgBackArrow.setColorFilter(Color.parseColor(config.getBackArrowColor()), PorterDuff.Mode.SRC_ATOP);


        }
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCameraPermission();
            }
        });

        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public static Intent CreateIntent(Activity activity, String vuforia_lic_key, String AccessKey, String SecretKey, Config config) {

        Intent intent = new Intent(activity, ActivityAdreadHome.class);
        intent.putExtra(KEY_VUFORIA_LIC_KEY, vuforia_lic_key);
        intent.putExtra(KEY_SECRETKEY, SecretKey);
        intent.putExtra(KEY_ACCESSKEY, AccessKey);
        intent.putExtra(KEY_CONFIG, config);
        return intent;
    }

    private void getDataFromIntent() {
        vuforia_lic_key = getIntent().getExtras().getString(KEY_VUFORIA_LIC_KEY);
        kSecretKey = getIntent().getExtras().getString(KEY_SECRETKEY);
        kAccessKey = getIntent().getExtras().getString(KEY_ACCESSKEY);
        config = (Config) getIntent().getExtras().getSerializable(KEY_CONFIG);
    }


    public void getCameraPermission() {
        Permission.PermissionBuilder permissionBuilder =
                new Permission.PermissionBuilder(Pemissions, REQUEST_CODE, this)
                        .enableDefaultRationalDialog(getString(R.string.app_name), "required camera permission to scan")
                        .enableDefaultSettingDialog(getString(R.string.app_name), "seems like you denied camera permission");

        requestAppPermissions(permissionBuilder.build());
    }

    @Override
    public void onPermissionGranted(int requestCode) {
        Intent i = CloudReco.CreateIntent(ActivityAdreadHome.this, vuforia_lic_key, kAccessKey, kSecretKey, config);
        startActivity(i);
    }

    @Override
    public void onPermissionDenied(int requestCode) {

        Toast.makeText(this, R.string.camera_permission, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionAccessRemoved(int requestCode) {

    }
}
