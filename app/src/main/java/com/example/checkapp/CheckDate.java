package com.example.checkapp;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.checkapp.Handler.BackPressCloseHandler;
import com.example.checkapp.data.ApplicationData;
import com.example.checkapp.data.model.Employee;
import com.example.checkapp.ui.callendar.CallendarList;
import com.example.checkapp.ui.gallery.GalleryViewModel;
import com.example.checkapp.ui.login.LoginActivity;
import com.example.checkapp.util.ResponseData;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class CheckDate extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private BackPressCloseHandler backPressCloseHandler;
    TextView tv;
    Button checkBtn;
    // 위도
    final double location_lat = 36.324835;
    // 경도
    final double location_long = 127.419874;

    LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_date);
        Toolbar toolbar = findViewById(R.id.toolbar);
        backPressCloseHandler = new BackPressCloseHandler(this);

        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_list)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);

        tv = findViewById(R.id.textView2);
        tv.setText("위치정보 미수신중");

        checkBtn = findViewById(R.id.checkBtn);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    tv.setText("수신중..");
                    // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기~!!!
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                            1000, // 통지사이의 최소 시간간격 (miliSecond)
                            0, // 통지사이의 최소 변경거리 (m)
                            mLocationListener);
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                            1000, // 통지사이의 최소 시간간격 (miliSecond)
                            0, // 통지사이의 최소 변경거리 (m)
                            mLocationListener);
                }catch(SecurityException ex){
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.check_date, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            if(location != null) {
                final Employee employee = ApplicationData.getEmployee();

                //여기서 위치값이 갱신되면 이벤트가 발생한다.
                //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

                Log.d("test", "onLocationChanged, location:" + location);
                double longitude = location.getLongitude(); //경도
                double latitude = location.getLatitude();   //위도
//                double altitude = location.getAltitude();   //고도
//                float accuracy = location.getAccuracy();    //정확도
//                String provider = location.getProvider();   //위치제공자

                // 현재 위치
                Location stPos = new Location("Point");
                stPos.setLatitude(latitude);
                stPos.setLongitude(longitude);
                // 회사 위치
                Location endPos = new Location("Company");
                endPos.setLatitude(location_lat);
                endPos.setLongitude(location_long);

                if (stPos.distanceTo(endPos) < 30) {
                    // 세션 제거
                    lm.removeUpdates(this);
                    // 체크 데이터 전송
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ResponseData responseData = new ResponseData();
                            String emp_id = employee.getEmp_id();

                            ArrayList<NameValuePair> postData = new ArrayList<>();
                            postData.add(new BasicNameValuePair("emp_id", emp_id));

                            try {

                                String body = responseData.postResponse("checkCommute", postData);

                                Gson gson = new Gson();

                                JSONObject jsonObject = new JSONObject(body);
                                final String res = jsonObject.getString("res");

                                tv.setText(employee.getEmp_nm() + "님 체크되었습니다. \n상태 : " + res);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), employee.getEmp_nm() + "님 체크되었습니다. \n상태 : " + res, Toast.LENGTH_SHORT).show();
                                    }
                                });

//                                handler.sendEmptyMessage(0);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                } else {
                    tv.setText("30m 이내에서만 체크가 가능합니다");
                    lm.removeUpdates(this);
                }
            }
        }
        public void onProviderDisabled(String provider) {
            // Disabled시
            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if ( id == R.id.nav_home ) {

        } else if ( id == R.id.nav_list ) {
            startActivity(new Intent(CheckDate.this, CallendarList.class));
//            overridePendingTransition(0,0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (ApplicationData.getEmployee() != null) {
                backPressCloseHandler.onBackPressed();
            } else {
                super.onBackPressed();
            }
        }
    }
}
