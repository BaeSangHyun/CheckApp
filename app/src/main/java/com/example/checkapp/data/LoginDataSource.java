package com.example.checkapp.data;

import com.example.checkapp.data.model.LoggedInUser;
import com.example.checkapp.util.Task;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    // 로그인 정보 비교 후 성공 시 Sucess 실패 시 Error
    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication

            Task networkTest = new Task();

            Map<String, String> params = new HashMap<>();
            params.put("emp_id", username);
            params.put("pass", password);

            networkTest.execute(params);

//            Thread thread = Thread.currentThread();
//            Thread.sleep(1000);

            LoggedInUser fakeUser =
                    new LoggedInUser(
//                            java.util.UUID.randomUUID().toString(),
                            username,
                            networkTest.getUserInfo().getEmp_nm());

            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
