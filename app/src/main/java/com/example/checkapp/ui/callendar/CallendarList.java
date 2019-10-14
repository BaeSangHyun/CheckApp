package com.example.checkapp.ui.callendar;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.checkapp.R;
import com.example.checkapp.data.ApplicationData;
import com.example.checkapp.data.CalendarData;
import com.example.checkapp.data.model.Commute;
import com.example.checkapp.data.model.Employee;
import com.example.checkapp.ui.callendar.decorators.EventDecorator;
import com.example.checkapp.ui.callendar.decorators.OneDayDecorator;
import com.example.checkapp.ui.callendar.decorators.SaturdayDecorator;
import com.example.checkapp.ui.callendar.decorators.SundayDecorator;
import com.example.checkapp.util.ResponseData;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

public class CallendarList extends AppCompatActivity {
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    MaterialCalendarView materialCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callendar_list);

        final TextView txtDt = findViewById(R.id.commuteDate);
        final TextView txtGtwTm = findViewById(R.id.commuteGtw_tm);
        final TextView txtOwTm = findViewById(R.id.commuteOw_tm);
        final TextView txtRes = findViewById(R.id.commuteRes);
        final EditText txtReas = findViewById(R.id.commuteReas);
        final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        final View commuteInfo = findViewById(R.id.commuteInfo);

        final Button reasBtn = findViewById(R.id.reasBtn);


        if( getSupportActionBar() != null ) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        materialCalendarView = findViewById(R.id.calendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator);



        new Thread(new Runnable() {
            @Override
            public void run() {
                // 데이터 전송 및 리스트 받아오기
                final Employee employee = ApplicationData.getEmployee();
                ResponseData responseData = new ResponseData();
                String emp_id = employee.getEmp_id();

                ArrayList<NameValuePair> postData = new ArrayList<>();
                postData.add(new BasicNameValuePair("emp_id", emp_id));

                try {
                    String body = responseData.postResponse("commuteList", postData);

                    Gson gson = new Gson();

                    JSONObject jsonObject = new JSONObject(body);
                    List<Commute> commuteList = new ArrayList<>();

                    JSONArray commuteListArr = jsonObject.getJSONArray("commuteList");
                    if (commuteListArr != null) {
                        Commute commute;
                        for (int i = 0 ; i < commuteListArr.length(); i++ ) {
                            Log.d("test", "object : " + commuteListArr.getJSONObject(i));
                            commute = gson.fromJson(commuteListArr.getJSONObject(i).toString(), Commute.class);
                            commuteList.add(commute);
                        }
                    }
                    Log.d("test", "commuteList : " + commuteList);

                    CalendarData.setCommuteList(commuteList);

                    new ApiSimulator(commuteList).executeOnExecutor(Executors.newSingleThreadExecutor());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

        reasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtReas.getText().length() != 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ResponseData responseData = new ResponseData();

                            String reas = txtReas.getText().toString();

                            ArrayList<NameValuePair> postData = new ArrayList<>();
                            postData.add(new BasicNameValuePair("reas", reas));
                            postData.add(new BasicNameValuePair("emp_id", ApplicationData.getEmployee().getEmp_id()));
                            postData.add(new BasicNameValuePair("dt", CalendarData.date));

                            try {
                                String body = responseData.postResponse("setReas", postData);

                                Gson gson = new Gson();

                                JSONObject jsonObject = new JSONObject(body);
                                final int result = jsonObject.getString("result") != null ? Integer.parseInt(jsonObject.getString("result")) : 0;

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (result == 1) {
                                            Toast.makeText(getApplicationContext(), "사유서가 정상 등록되었습니다", Toast.LENGTH_SHORT).show();
                                            imm.hideSoftInputFromWindow(txtReas.getWindowToken(), 0);
                                            txtReas.setFocusable(false);
                                            txtReas.setClickable(false);
                                            reasBtn.setEnabled(false);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "사유서 등록이 실패하였습니다", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    Toast.makeText(getApplicationContext(), "사유를 적어주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 캘린더 클릭 이벤트
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Date now_date = new Date();

                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

                final String shot_Day = sf.format(date.getDate());
                CalendarData.date = shot_Day;

                materialCalendarView.clearSelection();

                imm.hideSoftInputFromWindow(txtReas.getWindowToken(), 0);

                // 사유란 비우기
                txtReas.setText("");
                commuteInfo.setVisibility(View.INVISIBLE);

                if (now_date.compareTo(date.getDate()) == -1) {
                    Toast.makeText(getApplicationContext(), shot_Day + "는 확인 가능할 날자가 아닙니다", Toast.LENGTH_SHORT).show();

                    commuteInfo.setVisibility(View.INVISIBLE);

                } else {
//                    Toast.makeText(getApplicationContext(), shot_Day, Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ResponseData responseData = new ResponseData();

                            ArrayList<NameValuePair> postData = new ArrayList<>();
                            postData.add(new BasicNameValuePair("emp_id", ApplicationData.employee.getEmp_id()));
                            postData.add(new BasicNameValuePair("dt", shot_Day));

                            try {
                                String body = responseData.postResponse("commuteInfo", postData);

                                Gson gson = new Gson();

                                JSONObject jsonObject = new JSONObject(body);
                                String commuteData = jsonObject.getString("commute");
                                final Commute commute = gson.fromJson(commuteData, Commute.class);

                                if (commute != null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            txtDt.setText("날짜 : " + commute.getDt());
                                            txtGtwTm.setText("출근 시간 : " + commute.getGtw_tm());
                                            txtOwTm.setText("퇴근 시간 : " + commute.getOw_tm());
                                            txtRes.setText("결과 : " + commute.getRes());

                                            if (commute.getReas() != null) {
                                                txtReas.setText(commute.getReas());
                                                txtReas.setFocusable(false);
                                                txtReas.setClickable(false);
                                                reasBtn.setEnabled(false);
                                            }
                                            commuteInfo.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        View commuteInfo = findViewById(R.id.commuteInfo);
        if (commuteInfo.getVisibility() == View.VISIBLE) {
            commuteInfo.setVisibility(View.INVISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        List<Commute> commuteList;

        ApiSimulator(List<Commute> commuteList){
            this.commuteList = commuteList;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();


            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for(int i = 0 ; i < commuteList.size() ; i ++){
                if (!commuteList.get(i).getRes().equals("정상근무")) {
                    String[] time = commuteList.get(i).getDt().split("-");
                    int year = Integer.parseInt(time[0]);
                    int month = Integer.parseInt(time[1]);
                    int dayy = Integer.parseInt(time[2]);

                    calendar.set(year, month - 1, dayy);
                    CalendarDay day = CalendarDay.from(calendar);
                    dates.add(day);
                }
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            materialCalendarView.addDecorator(new EventDecorator(Color.RED, calendarDays, CallendarList.this));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ( item.getItemId() ) {
            case android.R.id.home : {
                onBackPressed();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
