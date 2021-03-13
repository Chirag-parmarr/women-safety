package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.womensafety.Classes.RecyclerItemClickListener;
import com.example.womensafety.Classes.prefConfig;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.womensafety.Adapters.CardAdapter;
import com.example.womensafety.Adapters.ContactAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.LimitColumn;
import com.wafflecopter.multicontactpicker.MultiContactPicker;


import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class emergencyContact extends AppCompatActivity implements ContactAdapter.ContactAdapterEvents {
    private static final int CONTACT_PICKER_REQUEST = 202;
    private Button addContactsBtn;
    private ArrayList<ContactResult> list;

    public static final String SHARE_PREFS = "sheredPrefs";

    ImageView backBtn;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);




//        list = prefConfig.readListFromPref(this);
//        if(list.isEmpty())  {
//        ArrayList<ContactResult> list= new ArrayList<>();
//        }
//        recyclerView = findViewById(R.id.contact_rv);
//        LinearLayoutManager LayoutManager= new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(LayoutManager);

        addContactsBtn = findViewById(R.id.add_contacts_btn);
        backBtn = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.contact_rv);

        loadData();
        buildRecycleView();

        addContactsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new MultiContactPicker.Builder(emergencyContact.this) //Activity/fragment context
                        .theme(R.style.MyCustomPickerTheme) //Optional - default: MultiContactPicker.Azure
                        .hideScrollbar(false) //Optional - default: false
                        .showTrack(true) //Optional - default: true
                        .searchIconColor(Color.WHITE) //Option - default: White
                        .setChoiceMode(MultiContactPicker.CHOICE_MODE_MULTIPLE) //Optional - default: CHOICE_MODE_MULTIPLE
                        .handleColor(ContextCompat.getColor(emergencyContact.this, R.color.azureColorPrimary)) //Optional - default: Azure Blue
                        .bubbleColor(ContextCompat.getColor(emergencyContact.this, R.color.azureColorPrimary)) //Optional - default: Azure Blue
                        .bubbleTextColor(Color.WHITE) //Optional - default: White
                        .setTitleText("Select Contacts") //Optional - default: Select Contacts
//                        .setSelectedContacts("10", "5" / myList) //Optional - will pre-select contacts of your choice. String... or List<ContactResult>
                        .setLoadingType(MultiContactPicker.LOAD_ASYNC) //Optional - default LOAD_ASYNC (wait till all loaded vs stream results)
                        .limitToColumn(LimitColumn.NONE) //Optional - default NONE (Include phone + email, limiting to one can improve loading time)
                        .setActivityAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                android.R.anim.fade_in,
                                android.R.anim.fade_out) //Optional - default: No animation overrides
                        .showPickerForResult(CONTACT_PICKER_REQUEST);

            }
        });

        saveData();



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(emergencyContact.this,MainActivity.class);
                startActivity(intent);
                finish();;
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CONTACT_PICKER_REQUEST){
            if(resultCode == RESULT_OK) {

                ArrayList<ContactResult> list1 =MultiContactPicker.obtainResult(data);
//                prefConfig.writeListInPref(getApplicationContext(),list);
//                ContactAdapter adapter = new ContactAdapter(list,this);
//                recyclerView.setAdapter(adapter);
//               adapter.notifyDataSetChanged();
                 list.addAll(list1);

                saveData();
                buildRecycleView();

            } else if(resultCode == RESULT_CANCELED){
                System.out.println("User closed the picker without selecting items.");
            }
        }
    }


    private void saveData() {
        SharedPreferences pref = getSharedPreferences(SHARE_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        editor.putString("List Key",jsonString);
        Log.e("STAG","size="+ list.size());
        editor.apply();
    }


    private  void loadData(){

            SharedPreferences pref = getSharedPreferences(SHARE_PREFS, MODE_PRIVATE);
            Gson gson = new Gson();
            String jsonString = pref.getString("List Key", null);
            Type type = new TypeToken<ArrayList<ContactResult>>() {}.getType();
            list = gson.fromJson(jsonString, type);
            Log.e("LLTAG", "Size=" + list.size());

             if(list == null){
                  list = new ArrayList<>();
                  Log.e("LTAG","Size="+list.size());
              }
    }


    private  void buildRecycleView(){


        LinearLayoutManager LayoutManager= new LinearLayoutManager(this);
        ContactAdapter adapter = new ContactAdapter(list,this);

        Collections.sort(list, new Comparator<ContactResult>() {
            @Override
            public int compare(ContactResult o1, ContactResult o2) {
                return o1.getDisplayName().compareTo(o2.getDisplayName());
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(LayoutManager);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void contactClicked(ContactResult contactResult) {

        Toast.makeText(this,":"+contactResult.getDisplayName(),Toast.LENGTH_SHORT).show();
        saveData();


    }
}