package com.example.checkapp.ui.login;

import android.Manifest;
import android.app.Activity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkapp.CustomDialog;
import com.example.checkapp.R;
import com.example.checkapp.data.ApplicationData;
import com.example.checkapp.data.model.Employee;
import com.example.checkapp.util.ResponseData;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 로그인 페이지
 * username = 아이디
 * password = 비밀번호
 */
public class LoginActivity extends AppCompatActivity{

    private LoginViewModel loginViewModel;
    private EditText usernameEditText;
    private EditText passwordEditText;

    Employee employee;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 권한 허가 신청
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        }

        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);


        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final Button loginButton = findViewById(R.id.login);
        usernameEditText = findViewById(R.id.username);
        usernameEditText.setText("e0003");
        passwordEditText = findViewById(R.id.password);
        passwordEditText.setText("test1234");

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        // 로그인버튼 클릭
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingProgressBar.setVisibility(View.VISIBLE);

//                Result login = loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
//                if(login instanceof Result.Success) {
//                    startActivity(new Intent(LoginActivity.this , CheckDate.class));
//                }

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        String ip = ApplicationData.getIp(); // 자신의 IP주소를 쓰시면 됩니다.(학원)
//                        String url = "http://" + ip + ":8081/login";
//
//                        String id = usernameEditText.getText().toString();
//                        String pass = passwordEditText.getText().toString();
//
//                        try {
//                            DefaultHttpClient http = new DefaultHttpClient();
//                            ArrayList<NameValuePair> postData = new ArrayList<>();
//
//                            postData.add(new BasicNameValuePair("emp_id", id));
//                            postData.add(new BasicNameValuePair("pass", pass));
//
//                            UrlEncodedFormEntity request = new UrlEncodedFormEntity(postData, "utf-8");
//                            HttpPost httpPost = new HttpPost(url);
//
//                            httpPost.setEntity(request);
//
//                            HttpResponse response = http.execute(httpPost);
//                            String body = EntityUtils.toString(response.getEntity());
//
//                            Gson gson = new Gson();
//
//                            JSONObject jsonObject = new JSONObject(body);
//                            String user = jsonObject.getString("user");
//                            employee = gson.fromJson(user, Employee.class);
//
//                            if (employee != null) {
//                                ApplicationData.setEmployee(employee);
//                                loadingProgressBar.setVisibility(View.INVISIBLE);
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
////                                        String str = employee.getEmp_nm() + "님 환영합니다";
////                                        Toast.makeText(getApplicationContext(),str, Toast.LENGTH_LONG);
//                                        CustomDialog customDialog = new CustomDialog(LoginActivity.this ,R.layout.dialog_fingerprint);
//                                        customDialog.show();
//                                    }
//                                });
//
//                            }
//                        } catch (Exception e) {
//
//                        }
                        ResponseData responseData = new ResponseData();

                        String id = usernameEditText.getText().toString();
                        String pass = passwordEditText.getText().toString();

                        ArrayList<NameValuePair> postData = new ArrayList<>();
                        postData.add(new BasicNameValuePair("emp_id", id));
                        postData.add(new BasicNameValuePair("pass", pass));

                        try {
                            String body = responseData.postResponse("login", postData);

                            Gson gson = new Gson();

                            JSONObject jsonObject = new JSONObject(body);
                            String user = jsonObject.getString("user");
                            employee = gson.fromJson(user, Employee.class);

                            if (employee != null) {
                                ApplicationData.setEmployee(employee);
                                loadingProgressBar.setVisibility(View.INVISIBLE);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CustomDialog customDialog = new CustomDialog(LoginActivity.this ,R.layout.dialog_fingerprint);
                                        customDialog.show();
                                    }
                                });

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                th.start();
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
