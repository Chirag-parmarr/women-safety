package com.example.womensafety.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.womensafety.Models.CardModel;
import com.example.womensafety.R;
import com.wafflecopter.multicontactpicker.ContactResult;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.viewHolder> {
    ArrayList<ContactResult> list;
    ContactAdapterEvents contactAdapterEvents;
//    Context context;

    public ContactAdapter(ArrayList<ContactResult> list,ContactAdapterEvents contactAdapterEvents) {
        this.list = list;
        this.contactAdapterEvents=contactAdapterEvents;

//        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_contact,parent,false);
        return new ContactAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ContactResult model = list.get(position);
        holder.contactName.setText(model.getDisplayName());

        holder.contactNumber.setText(String.valueOf((model.getPhoneNumbers().get(0).getNumber())));



    }

    @Override
    public int getItemCount() {

        return list.size();

    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        View view;
        TextView contactName;
        TextView contactNumber;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            view= itemView.findViewById(R.id.sample_Contact_layout);
            contactName= itemView.findViewById(R.id.contact_name);
            contactNumber= itemView.findViewById(R.id.mobile_no);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            int position = getAdapterPosition();



        }

        @Override
        public boolean onLongClick(View v) {

            int position = getAdapterPosition();
            list.remove(position);
            notifyItemRemoved(position);
            contactAdapterEvents.contactClicked(list.get(position));
//            Toast.makeText(view.getContext(),"Deleted:" +list.get(position).getDisplayName(), Toast.LENGTH_SHORT).show();

            return true;
        }
    }
    public interface ContactAdapterEvents{
        void contactClicked(ContactResult contactResult);
    }

}
