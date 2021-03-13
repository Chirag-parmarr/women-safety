package com.example.womensafety;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.womensafety.Adapters.CardAdapter;
import com.example.womensafety.Classes.RecyclerItemClickListener;
import com.example.womensafety.Models.CardModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button emergencyBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emergencyBtn = findViewById(R.id.apply_emergency_btn);
        recyclerView = findViewById(R.id.recycleView);



        ArrayList<CardModel> list = new ArrayList<>();

        list.add(new CardModel(R.drawable.police_icon, "Find Nearest Police Station"));
        list.add(new CardModel(R.drawable.contact_icon, "Emergency Contacts"));
        list.add(new CardModel(R.drawable.setting_icon, "Settings"));
        list.add(new CardModel(R.drawable.law_icon, "Women Safety Laws"));
        list.add(new CardModel(R.drawable.tips_icon, "Tips"));
        list.add(new CardModel(R.drawable.landline_icon, "Emergency Landlines"));

        CardAdapter adapter = new CardAdapter(list,this);
        recyclerView.setAdapter(adapter);

        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

       recyclerView.addOnItemTouchListener(new RecyclerItemClickListener
               (this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                   @Override
                   public void onItemClick(View view, int position) {
                       switch(position){
                           case 0:
                               Intent intent = new Intent(Intent.ACTION_VIEW);
                               intent.setData(Uri.parse("geo:28.7041° N, 77.1025° E"));
                               Intent chooser = Intent.createChooser(intent,"launch map");
                               startActivity(chooser);
                               break;
                           case 1:   Intent contactintent = new Intent(MainActivity.this,emergencyContact.class);
                               startActivity(contactintent);
                               finish();
                               break;
                           case 2:
                               break;
                           case 3:
                               break;
                           case 4:
                               Intent mapintent = new Intent(MainActivity.this,TipsActivity.class);
                               startActivity(mapintent);
                               finish();
                               break;
                           case 5:
                               break;
                           case 6:
                               break;


                           default:
                       }
                   }

                   @Override
                   public void onLongItemClick(View view, int position) {

                   }
               }


               ));


       emergencyBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
               builder.setTitle("You really want to apply emergency?")
                       .setMessage("Your current Location will be sent to your chosen colleague")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                           }
                       })
                       .setNegativeButton("Cencel",null);
               AlertDialog alert =builder.create();
               alert.show();
           }
       });



    }

}