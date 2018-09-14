package com.example.administrator.appfoody.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.appfoody.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import dmax.dialog.SpotsDialog;

public class DangNhapActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener,FirebaseAuth.AuthStateListener{
    SharedPreferences sharedPreferences;
    TextView txtquenmatkhau;
    AlertDialog progressDialog;
    EditText edtemailDn;
    EditText edtpasswordDn;
    Button btndangnhap;
    TextView txtdangkimoi;
    FirebaseAuth firebaseAuth;
    public static int kiemtraproviderdangnhap=0;
    GoogleApiClient apiClient;
    public static int requestcodedangnhapgoogle=99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        progressDialog = new SpotsDialog(DangNhapActivity.this, R.style.Custom);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        anhXa();
        sharedPreferences=getSharedPreferences("Luudangnhap",MODE_PRIVATE);
        txtdangkimoi.setOnClickListener(this);
        txtquenmatkhau.setOnClickListener(this);
        btndangnhap.setOnClickListener(this);
        taoClientDangNhapGoogle();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    private void anhXa() {
        txtquenmatkhau=findViewById(R.id.txt_quenmatkhau);
        edtemailDn=findViewById(R.id.edt_emaildangnhap);
        edtpasswordDn=findViewById(R.id.edt_passworddn);
        btndangnhap=findViewById(R.id.btn_dangnhap);
        txtdangkimoi=findViewById(R.id.txt_dangkimoi);
    }

    public void taoClientDangNhapGoogle(){
        GoogleSignInOptions signInOptions=new GoogleSignInOptions.Builder().requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        apiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();
    }
    public void dangNhapGoogle(GoogleApiClient apiClient){
        kiemtraproviderdangnhap=1;
        Intent idnGoogle=Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(idnGoogle,requestcodedangnhapgoogle);
    }
    public void chungThucDangNhapFirebase(String tokenId){
        if(kiemtraproviderdangnhap==1){
            AuthCredential authCredential= GoogleAuthProvider.getCredential(tokenId,null);
            firebaseAuth.signInWithCredential(authCredential);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==requestcodedangnhapgoogle&&resultCode==RESULT_OK){
            GoogleSignInResult signInResult=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount account=signInResult.getSignInAccount();
            String tokenid=account.getIdToken();
            chungThucDangNhapFirebase(tokenid);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.txt_dangkimoi){
            Intent idangky=new Intent(DangNhapActivity.this,DangKiActivity.class);
            startActivity(idangky);
        }
        if(id==R.id.btn_dangnhap){
            progressDialog.show();
            DangNhap();
        }
        if(id==R.id.txt_quenmatkhau){
            Intent ikhoiphucmatkhau=new Intent(DangNhapActivity.this,KhoiPhucMatKhauActivity.class);
            startActivity(ikhoiphucmatkhau);
        }
    }
    public void DangNhap(){
        String email=edtemailDn.getText().toString();
        String password=edtpasswordDn.getText().toString();
        Log.d("kiemtra",email+"    "+password);
        if(email.length()>0&&password.length()>0){
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(DangNhapActivity.this, "Tài khoản không hợp lệ! Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
        else{
            Toast.makeText(this, "Vui lòng nhập đầy đủ email và mật khẩu", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("mauser",user.getUid());
            editor.commit();
            Intent intent=new Intent(DangNhapActivity.this,TrangChuActivity.class);
            startActivity(intent);
        }
    }
}
