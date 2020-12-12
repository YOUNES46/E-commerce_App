package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminaddNewProdecte extends AppCompatActivity {

    String category,description,price,Pname,savecurrentdat,savecurrenttime,prodectecterandomkey,dowlnoldimageurl;
    ImageView prodecteimage;
    Button addprodect;
    ProgressDialog loaddingbar;
    EditText prodectename,descriptionprodecte,prodecteprice;
    static final int gallerypeker =1;
    Uri imageuri;
    StorageReference prodectereimagerenceref;
    DatabaseReference prodectereref ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminadd_new_prodecte);
        category=getIntent().getExtras().get("Category").toString();
        prodectereimagerenceref = FirebaseStorage.getInstance().getReference().child("Prodect Images");
        prodectereref = FirebaseDatabase.getInstance().getReference().child("Prodects");
        Toast.makeText(this, category, Toast.LENGTH_SHORT).show();
        addprodect=(Button)findViewById(R.id.add_prodecte);
        prodectename=(EditText)findViewById(R.id.prodect_name);
        descriptionprodecte=(EditText)findViewById(R.id.prodecte_description);
        prodecteprice=(EditText)findViewById(R.id.prodecte_price);
        prodecteimage=(ImageView)findViewById(R.id.selecteimage);
        loaddingbar =new ProgressDialog(this);
        prodecteimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });
        addprodect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validateprodectedata();
            }
        });
    }



    private void OpenGallery() {

        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,gallerypeker);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==gallerypeker && resultCode==RESULT_OK && data!=null){
           imageuri = data.getData();
           prodecteimage.setImageURI(imageuri);
        }



    }
    private void Validateprodectedata() {
        description =descriptionprodecte.getText().toString();
        price =prodecteprice.getText().toString();
        Pname =prodectename.getText().toString();
        if(imageuri == null){
            Toast.makeText(this, "لقم تقم بادخال الصورة", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(description)){
            Toast.makeText(this, "لقم تقم بادخال وصف المنتج", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(price)){
            Toast.makeText(this, "لقم تقم بادخال سعر المنتج", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(Pname)){
            Toast.makeText(this, "لقم تقم بادخال اسم المنتج", Toast.LENGTH_SHORT).show();

        }
        else{
            storeimageinformation();
        }



    }

    private void storeimageinformation() {
        loaddingbar.setTitle("اضافة منتج جديد");
        loaddingbar.setMessage("الرجاء الانتظار...");
        loaddingbar.setCanceledOnTouchOutside(false);
        loaddingbar.show();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentdata = new SimpleDateFormat("MMM dd, yyyy");
        savecurrentdat =currentdata.format(calendar.getTime());
        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime=currenttime.format(calendar.getTime());
        prodectecterandomkey =  savecurrenttime + savecurrentdat;
        final StorageReference filepath = prodectereimagerenceref.child(imageuri.getLastPathSegment()+ prodectecterandomkey+".jpg");
        final UploadTask uploadtask =filepath.putFile(imageuri);
        uploadtask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String mmessage = e.toString();
                Toast.makeText(AdminaddNewProdecte.this, "Error: "+ mmessage, Toast.LENGTH_SHORT).show();
                loaddingbar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminaddNewProdecte.this, "IMAGE UPLOADED SUCCSESFALLY.... ", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadtask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                       if(!task.isSuccessful()){
                           throw task.getException();

                       }
                       dowlnoldimageurl = filepath.getDownloadUrl().toString();
                       return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                      if(task.isSuccessful()){
                          dowlnoldimageurl = task.getResult().toString();

                          Toast.makeText(AdminaddNewProdecte.this, "لقد تمت اضافة رابط الصورة بنجاح...", Toast.LENGTH_SHORT).show();
                      saveprodectetodatabase();

                      }
                    }
                });
            }
        });


    }

    private void saveprodectetodatabase() {
        HashMap<String,Object> prodectmap =new HashMap<>();
        prodectmap.put("key",prodectecterandomkey);
        prodectmap.put("date",savecurrentdat);
        prodectmap.put("time",savecurrenttime);
        prodectmap.put("description",description);
        prodectmap.put("image",dowlnoldimageurl);
        prodectmap.put("category",category);
        prodectmap.put("price",price);
        prodectmap.put("name",Pname);
        prodectereref.child(prodectecterandomkey).updateChildren(prodectmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            loaddingbar.dismiss();
                            Intent i = new Intent(AdminaddNewProdecte.this,AdminCategory.class);
                            startActivity(i);

                            Toast.makeText(AdminaddNewProdecte.this, "لقد تم اضافة المنتج بنجاح", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            loaddingbar.dismiss();
                            String s = task.getException().toString();
                            Toast.makeText(AdminaddNewProdecte.this, "Eroor"+s, Toast.LENGTH_SHORT).show();


                        }

                    }
                });

    }
}
