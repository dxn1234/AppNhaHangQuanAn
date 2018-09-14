package com.example.administrator.appfoody.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.appfoody.R;
import com.example.administrator.appfoody.model.ThanhVienModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class DangKiActivity extends AppCompatActivity implements View.OnClickListener{
    AlertDialog progressDialog;
    EditText edtemailDk,edtpasswordDk,edtnhaplaipasswordDk;
    Button btndangky;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        progressDialog = new SpotsDialog(DangKiActivity.this, R.style.Custom);
        firebaseAuth=FirebaseAuth.getInstance();
        anhXa();
        btndangky.setOnClickListener(this);
    }

    private void anhXa() {
        btndangky=findViewById(R.id.btn_dangki);
        edtemailDk=findViewById(R.id.edt_emaildangki);
        edtpasswordDk=findViewById(R.id.edt_passworddk);
        edtnhaplaipasswordDk=findViewById(R.id.edt_nhaplaipassworddk);
    }

    @Override
    public void onClick(View v) {
        final String email=edtemailDk.getText().toString();
        String matkhau=edtpasswordDk.getText().toString();
        String nhaplaimatkhau=edtnhaplaipasswordDk.getText().toString();
        if(email.trim().length()==0){
            Toast.makeText(this, "Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
        }
        else if(matkhau.trim().length()==0){
            Toast.makeText(this, "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
        }
        else if(matkhau.equals(nhaplaimatkhau)==false){
            Toast.makeText(this, "Nhập sai mật khẩu nhập lại", Toast.LENGTH_SHORT).show();
        }
        else{
//            progressDialogthongbao.setMessage("Đang xử lý");
//            progressDialogthongbao.show();
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email,matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String uid=task.getResult().getUser().getUid();
//                        progressDialogthongbao.dismiss();
                        ThanhVienModel thanhVienModel=new ThanhVienModel();
                        thanhVienModel.setHinhanh("user.png");
                        thanhVienModel.setHoten(email);
                        thanhVienModel.themThongTinThanhVien(thanhVienModel,uid);
                        progressDialog.dismiss();
                        Toast.makeText(DangKiActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DangKiActivity.this,TrangChuActivity.class));
                    }
                    else{
                        Toast.makeText(DangKiActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }
}
