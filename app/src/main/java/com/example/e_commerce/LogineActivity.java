package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.Model.Users;
import com.example.e_commerce.Privalent.Privalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import io.paperdb.Paper;

public class LogineActivity extends AppCompatActivity {
EditText phone,passwoed;
Button login;
ProgressDialog  loaddingbar;
String parentdbname ="Users";
CheckBox chekboxremamberme;
TextView adminlink,notadminlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logine);
        phone=(EditText)findViewById(R.id.phone_number_login);
        passwoed=(EditText)findViewById(R.id.password_login);
        login= (Button)findViewById(R.id.joinlogin_button);
        chekboxremamberme=(CheckBox)findViewById(R.id.chekbox_login) ;
        adminlink=(TextView)findViewById(R.id.am_admin) ;
        notadminlink =(TextView)findViewById(R.id.not_admin) ;
        loaddingbar =new ProgressDialog(this);
        Paper.init(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginuser();
            }
        });
        adminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("دخول ادمن");
                adminlink.setVisibility(View.INVISIBLE);
                notadminlink.setVisibility(View.VISIBLE);
                parentdbname="Admins";

            }
        });
        notadminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText(" دخول");
                adminlink.setVisibility(View.VISIBLE);
                notadminlink.setVisibility(View.INVISIBLE);
                parentdbname="Users";
            }
        });

    }

    private void loginuser() {
        String portable =phone.getText().toString();
        String motdepasse =passwoed.getText().toString();
        if(TextUtils.isEmpty(portable)){
            Toast.makeText(this, "الرجاء ادخال الرقم ...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(motdepasse)){
            Toast.makeText(this, "الرجاء ادخال كلمة السر...", Toast.LENGTH_SHORT).show();
        }
        else{
            loaddingbar.setTitle("دخول للحساب...");
            loaddingbar.setMessage("الرجاء الانتظار...");
            loaddingbar.setCanceledOnTouchOutside(false);
            loaddingbar.show();
            AloowAccesttoAccount(portable,motdepasse);
        }
    }

    private void AloowAccesttoAccount(final String portable, final String motdepasse) {

        if(chekboxremamberme.isChecked()){
            Paper.book().write(Privalent.Userphonekey,portable);
            Paper.book().write(Privalent.Userpasswordkey,motdepasse);
        }
        final DatabaseReference rootref;
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child( parentdbname ).child(portable).exists()){
                    Users userdata = new Users();
                             userdata.setPhone(dataSnapshot.child(parentdbname).child(portable).child("Phone").getValue().toString());
                             userdata.setPassword(dataSnapshot.child(parentdbname).child(portable).child("Passworde").getValue().toString());
                   if(userdata.getPhone().equals(portable)){
                       if(userdata.getPassword().equals(motdepasse)){
                           if(parentdbname.equals("Admins")){
                               Toast.makeText(LogineActivity.this, "اهلا ادمن دخول ناجح.....", Toast.LENGTH_SHORT).show();
                               loaddingbar.dismiss();
                               Intent i = new Intent(LogineActivity.this,AdminCategory.class);
                               startActivity(i);
                           }
                           else if(parentdbname.equals("Users")){
                               Toast.makeText(LogineActivity.this, "دخول ناجح.....", Toast.LENGTH_SHORT).show();
                               loaddingbar.dismiss();
                               Intent i = new Intent(LogineActivity.this,HomeActivity.class);
                               startActivity(i);
                           }
                       }
                       else {
                           loaddingbar.dismiss();
                           Toast.makeText(LogineActivity.this, "كلمة سر خاطوة...", Toast.LENGTH_SHORT).show();
                       }
                   }
                }
                else{
                    Toast.makeText(LogineActivity.this, "غير موجود.... "+portable+"حساب بهذا الرقم ", Toast.LENGTH_SHORT).show();
               loaddingbar.dismiss();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
