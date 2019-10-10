package com.example.checkapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkapp.ui.login.LoginActivity;

public class CustomDialog extends Dialog {
    Button btn;
    EditText editPass;
    TextView shakeText;

    public CustomDialog(final Context context, int layoutResID) {
        super(context);

        if(layoutResID==R.layout.dialog_fingerprint){
            FingerPrint fingerPrint = new FingerPrint(getContext());
            if(fingerPrint.isFingerHarWare()){
                if(fingerPrint.isFingerPassCode()){
                    fingerPrint.initalize(new FingerprintManager.AuthenticationCallback() {
                        @Override //지문인증에 치명적인 오류가 발생하면 호출
                        public void onAuthenticationError(int errMsgId, CharSequence errString) {
                            Toast.makeText(getContext(), "Authentication error\n" + errString, Toast.LENGTH_SHORT).show();
                        }
                        @Override //오류가 발생하면 호출되지만 치명적인 예외는 아님
                        public void onAuthenticationHelp(int helpMsgId,CharSequence helpString) {
                            Toast.makeText(getContext(), "Authentication help\n" + helpString, Toast.LENGTH_SHORT).show();
                        }
                        @Override //장치에 등록 된 지문이 아닌 경우 호출
                        public void onAuthenticationFailed() {
                            Toast.makeText(getContext(), "등록되지 않은 지문입니다.", Toast.LENGTH_SHORT).show();
                            //틀렸을 시 텍스트 좌우로 흔들어주기
                            Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                            shakeText = findViewById(R.id.dialog_text);
                            shakeText.startAnimation(shake);
                        }
                        @Override //장치에 등록 된 지문이 스캔되었을 때 호출
                        public void onAuthenticationSucceeded( FingerprintManager.AuthenticationResult result) {
                            TextView textView = findViewById(R.id.dialog_text);
                            textView.setText("로그인 되었습니다");
//                            clientSocketThread = new ClientSocketThread(handler, "quitUser", userVO);
//                            clientSocketThread.start();
                            dismiss();
//                            context.startActivity(new Intent(getContext(), CheckDate.class));
                            context.startActivity(new Intent(getContext(), CheckDate.class));
                        }
                    });
                }else{
                    context.startActivity(new Intent(Settings.ACTION_SECURITY_SETTINGS));
                    dismiss();
                }
            }
        }
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듦
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애줌
        setContentView(layoutResID);     //다이얼로그에서 사용할 레이아웃

        //취소버튼 => 지문인식에 있음
        ((Button)findViewById(R.id.dialog_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //확인버튼 => 비밀번호변경에 있음
//        ((Button)findViewById(R.id.dialog_submit)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editPass = (EditText)findViewById(R.id.dialog_edit_password);
//
//                userVO.setPassword(editPass.getText().toString());
//                clientSocketThread = new ClientSocketThread(handler, "updateUser", userVO);
//                clientSocketThread.start();
//                Toast.makeText(getContext(), "정상적으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
//                dismiss();
//            }
//        });
    }
} //e.o.CustomDialog
