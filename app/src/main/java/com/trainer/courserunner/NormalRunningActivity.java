package com.trainer.courserunner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.core.app.ActivityCompat;

import com.trainer.courserunner.Application.enumtype.StartType;
import com.trainer.courserunner.course.activity.GuideRunnerActivity;
import com.trainer.courserunner.course.component.maker.CourseMaker;
import com.trainer.courserunner.course.component.maker.layer.line.LineConnectLayerDfsCustom;
import com.trainer.courserunner.course.component.maker.layer.quanzation.QuanzationMininumGuarantee;
import com.trainer.courserunner.course.component.maker.layer.regist.CourseRegistLayerAll;
import com.trainer.courserunner.course.component.maker.layer.selection.MarkerSelectionNone;
import com.trainer.courserunner.course.component.maker.scopetype.ScopeDotAddress;
import com.trainer.courserunner.course.component.maker.scopetype.ScopeMapInfo;
import com.trainer.courserunner.loader.AssetLoader;

public class NormalRunningActivity extends BaseRunningActivity implements NextActivityInterface{
    private Location currentLocation;

    private Button km2_btn;
    private Button km4_btn;
    private Button km6_btn;
    private Button km8_btn;
    private Button km10_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_running);

        km2_btn = (Button) findViewById(R.id.km2_btn);
        km2_btn.setOnClickListener(getMeterBtnListener(2));

        km4_btn = (Button) findViewById(R.id.km4_btn);
        km4_btn.setOnClickListener(getMeterBtnListener(4));

        km6_btn = (Button) findViewById(R.id.km6_btn);
        km6_btn.setOnClickListener(getMeterBtnListener(6));

        km8_btn = (Button) findViewById(R.id.km8_btn);
        km8_btn.setOnClickListener(getMeterBtnListener(8));

        km10_btn = (Button) findViewById(R.id.km10_btn);
        km10_btn.setOnClickListener(getMeterBtnListener(10));

        lockWhenGetLocation();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // 새로운 위치의 발견
                currentLocation = location;
                unlockWhenGetLocation();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        //권한체크
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
    }
    @Override
    protected void lockWhenGetLocation() {
        /*
        km2_btn.setEnabled(false);
        km4_btn.setEnabled(false);
        km6_btn.setEnabled(false);
        km8_btn.setEnabled(false);
        km10_btn.setEnabled(false);

         */
    }

    @Override
    protected void unlockWhenGetLocation() {
        km2_btn.setEnabled(true);
        km4_btn.setEnabled(true);
        km6_btn.setEnabled(true);
        km8_btn.setEnabled(true);
        km10_btn.setEnabled(true);
    }

    View.OnClickListener getMeterBtnListener(double kilometer) {
        return (View view) -> {
            //테스트코드
            ScopeMapInfo scopeMapInfo = ScopeMapInfo.makeScopeMapInfoOriginLeftDown(
                    currentLocation.getLatitude(), currentLocation.getLongitude(), kilometer * 1000);
            Bitmap bitmap = BitmapFactory.decodeResource(NormalRunningActivity.this.getResources(),getIntent().getIntExtra("bitmap",-1));

            CourseMaker.Builder builder = new CourseMaker.Builder();
            builder.setCourseRegistLayer(new CourseRegistLayerAll());
            builder.setLineConnectLayer(new LineConnectLayerDfsCustom(0.1));
            builder.setQuanzationLayer(new QuanzationMininumGuarantee());
            builder.setBitmap(bitmap);
            builder.setScopeMapInfo(scopeMapInfo);
            builder.setStartLocation(new ScopeDotAddress(scopeMapInfo, currentLocation.getLongitude(), currentLocation.getLatitude()));
            builder.setFinishEvent(o -> nextActivity((Long) o));
            builder.setMarkerSelectionLayer(new MarkerSelectionNone());

            CourseMaker courseMaker = builder.build();
            courseMaker.runComponent();
        };
    }

    @Override
    public void nextActivity(Object sendData) {
        Intent intent = new Intent(getBaseContext(), GuideRunnerActivity.class);
        intent.putExtra("courseId", (Long)sendData);
        intent.putExtra("startType", StartType.NEW);
        startActivity(intent);
    }
}