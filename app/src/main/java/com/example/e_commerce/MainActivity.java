package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_commerce.Model.Users;
import com.example.e_commerce.Privalent.Privalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    Button logine,signeup;
    ProgressDialog loaddingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logine = findViewById(R.id.join_button);
        signeup =findViewById(R.id.login_button);
        loaddingbar =new ProgressDialog(this);
        Paper.init(this);

        logine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,LogineActivity.class);
                startActivity(i);
            }
        });
        signeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Register_Activity.class);
                startActivity(i);
            }
        });

        String Phonekey = Paper.book().read(Privalent.Userphonekey);
        String Passwordkey = Paper.book().read(Privalent.Userpasswordkey);
        if( Phonekey !="" && Passwordkey!="" ){
            if(!TextUtils.isEmpty(Phonekey) && !TextUtils.isEmpty(Passwordkey )){
                AllowAceess(Phonekey,Passwordkey);
                loaddingbar.setTitle("لقد قمت بالدخول سابقا... ");
                loaddingbar.setMessage("الرجاء الانتظار...");
                loaddingbar.setCanceledOnTouchOutside(false);
                loaddingbar.show();
            }

        }

    }

    private void AllowAceess(final String portable, final String motdepasse) {
        final DatabaseReference rootref;
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child( "Users" ).child(portable).exists()){
                    Users userdata = new Users();
                    userdata.setPhone(dataSnapshot.child("Users").child(portable).child("Phone").getValue().toString());
                    userdata.setPassword(dataSnapshot.child("Users").child(portable).child("Passworde").getValue().toString());
                    if(userdata.getPhone().equals(portable)){
                        if(motdepasse.equals(userdata.getPassword())){
                            Toast.makeText(MainActivity.this, "الرجاء الانتظار....", Toast.LENGTH_SHORT).show();
                            loaddingbar.dismiss();
                            Intent i = new Intent(MainActivity.this,HomeActivity.class);
                            startActivity(i);
                        }
                        else {
                            loaddingbar.dismiss();
                            Toast.makeText(MainActivity.this, "كلمة سر خاطوة...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "غير موجود "+portable+" حساب بهذا الرقم ", Toast.LENGTH_SHORT).show();
                    loaddingbar.dismiss();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
