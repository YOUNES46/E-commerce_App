package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register_Activity extends AppCompatActivity {
 Button creat;
 EditText name,password,phonenumber;
 ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        name =(EditText)findViewById(R.id.name_register);
        password=(EditText)findViewById(R.id.password_register);
        phonenumber=(EditText)findViewById(R.id.phone_number_register);
        creat= (Button)findViewById(R.id.register_login_button);
        loadingbar =new ProgressDialog(this);
        creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatAccount();
            }
        });
    }

    private void creatAccount() {
        String nom =name.getText().toString();
        String portable =phonenumber.getText().toString();
        String motdepasse =password.getText().toString();

        if(TextUtils.isEmpty(nom)){
            Toast.makeText(this, "ادخل اسمك اولا...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(portable)) {
            Toast.makeText(this, "ادخل رقم هاتفك من فضلك...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(motdepasse)){
            Toast.makeText(this, "ادخل كلمة السر...", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingbar.setTitle("انشاء حساب");
            loadingbar.setMessage("الرجاء الانتظار...");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();
            validatephonenumber(nom,portable,motdepasse);
        }
    }

    private void validatephonenumber(final String nom, final String portable, final String motdepasse) {
        final DatabaseReference rootref;
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if((!dataSnapshot.child("Users").child(portable).exists())){
                    HashMap<String,Object> userdatamap =new HashMap<>();
                    userdatamap.put("Phone",portable);
                    userdatamap.put("Passworde",motdepasse);
                    userdatamap.put("Name",nom);
                    rootref.child("Users").child(portable).updateChildren(userdatamap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register_Activity.this, "لقد تم انشاء حسابك بنجاح", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                        Intent i = new Intent(Register_Activity.this,LogineActivity.class);
                                        startActivity(i);
                                    }
                                    else{
                                        loadingbar.dismiss();
                                        Toast.makeText(Register_Activity.this, "مشكا في الانترنت ", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }
                else{
                    Toast.makeText(Register_Activity.this, "موجود سابقا  "+portable+"  حساب برقم ", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(Register_Activity.this, "حاول مجددا برقم اخر ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Register_Activity.this,MainActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
