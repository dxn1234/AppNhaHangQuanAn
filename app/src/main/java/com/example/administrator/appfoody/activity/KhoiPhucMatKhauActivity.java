package com.example.administrator.appfoody.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.appfoody.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class KhoiPhucMatKhauActivity extends AppCompatActivity implements View.OnClickListener{
    FirebaseAuth firebaseAuth;
    Button btnguiemail;
    EditText edtemailKp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoi_phuc_mat_khau);
        firebaseAuth=FirebaseAuth.getInstance();
        anhXa();
        btnguiemail.setOnClickListener(this);
    }

    private void anhXa() {
        btnguiemail=findViewById(R.id.btn_guiemailKp);
        edtemailKp=findViewById(R.id.edt_emailKp);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btn_guiemailKp){
            String email=edtemailKp.getText().toString();
            boolean kiemtraemail=kiemTraEmail(email);
            if(kiemtraemail==true){
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(KhoiPhucMatKhauActivity.this, "Gửi email thành công! Vui lòng kiểm tra hộp thư đến email", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            else{
                Toast.makeText(this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean kiemTraEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
