package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategory extends AppCompatActivity {
    private ImageView techerts,techerts_custums,techerts_sports,techerts_femal;
    private ImageView glas,sac,chap,chos;
    private ImageView cascs,laptop,mobil,watche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        techerts=(ImageView)findViewById(R.id.tshirt);
        techerts_custums=(ImageView)findViewById(R.id.costum);
       techerts_sports=(ImageView)findViewById(R.id.sport_techert);
        techerts_femal=(ImageView)findViewById(R.id.femal_techert);
       glas=(ImageView)findViewById(R.id.glas);
       sac=(ImageView)findViewById(R.id.sacs);
        chap=(ImageView)findViewById(R.id.chapeux);
        chos=(ImageView)findViewById(R.id.adidas);
       cascs=(ImageView)findViewById(R.id.kitmans);
        laptop=(ImageView)findViewById(R.id.pss);
       mobil=(ImageView)findViewById(R.id.mobiles);
        watche=(ImageView)findViewById(R.id.watches);

        techerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminCategory.this,AdminaddNewProdecte.class);
                i.putExtra("Category","T_shirt");
                startActivity(i);
            }
        });
        techerts_custums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminCategory.this,AdminaddNewProdecte.class);
                i.putExtra("Category","techerts_custums");
                startActivity(i);
            }
        });
        techerts_sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminCategory.this,AdminaddNewProdecte.class);
                i.putExtra("Category","techerts_sports");
                startActivity(i);
            }
        });
        techerts_femal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminCategory.this,AdminaddNewProdecte.class);
                i.putExtra("Category","techerts_femal");
                startActivity(i);
            }
        });
        glas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminCategory.this,AdminaddNewProdecte.class);
                i.putExtra("Category"," glas");
                startActivity(i);
            }
        });
        sac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminCategory.this,AdminaddNewProdecte.class);
                i.putExtra("Category"," sac");
                startActivity(i);
            }
        });
        chap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminCategory.this,AdminaddNewProdecte.class);
                i.putExtra("Category"," chap");
                startActivity(i);
            }
        });
        chos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminCategory.this,AdminaddNewProdecte.class);
                i.putExtra("Category"," chos");
                startActivity(i);
            }
        });
        cascs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminCategory.this,AdminaddNewProdecte.class);
                i.putExtra("Category","  cascs");
                startActivity(i);
            }
        });
       laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminCategory.this,AdminaddNewProdecte.class);
                i.putExtra("Category"," laptop");
                startActivity(i);
            }
        });
        mobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminCategory.this,AdminaddNewProdecte.class);
                i.putExtra("Category","mobil");
                startActivity(i);
            }
        });
        watche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminCategory.this,AdminaddNewProdecte.class);
                i.putExtra("Category","watche");
                startActivity(i);
            }
        });


    }
}
