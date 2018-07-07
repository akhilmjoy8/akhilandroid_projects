package com.example.acer.currentlocation.contacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.acer.currentlocation.Location.LocationEntryActivity;
import com.example.acer.currentlocation.Location.LocationHeadActivity;
import com.example.acer.currentlocation.Navigation.MapsNavigationActivity;
import com.example.acer.currentlocation.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter  extends ArrayAdapter<ContactModel> {

        int resource;
        String response;
        Context context;
        private List<ContactModel> items;
        private CustomAdapter adapter;

public CustomAdapter(Context context, int resource, List<ContactModel> items) {
        super(context, resource, items);
        this.resource=resource;
        this.context = context;
        this.items=items;
        this.adapter = this;
        // Progress dialog

        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout contactsView;
        final ContactModel contact = getItem(position);
        if (convertView == null) {
        contactsView = new LinearLayout(getContext());
        String inflater = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater vi;
        vi = (LayoutInflater) getContext().getSystemService(inflater);
        vi.inflate(resource, contactsView, true);
        } else {
        contactsView = (LinearLayout) convertView;
        }

        final LinearLayout ll = contactsView.findViewById(R.id.llContact);
        final TextView Name = (TextView) contactsView.findViewById(R.id.tvCtName);
        final TextView uid1 = (TextView) contactsView.findViewById(R.id.uid);
        final TextView phone = (TextView) contactsView.findViewById(R.id.tvCtNumber);
        final TextView tvOnline = (TextView) contactsView.findViewById(R.id.tvOnline);
        final  ImageView im = (ImageView) contactsView.findViewById(R.id.imgProfile);
        final  ImageView imgOnline = (ImageView) contactsView.findViewById(R.id.imgOnline);
        uid1.setText(contact.getUid());
        if (Name != null) {
        Name.setText(contact.getName());
        }
        if (phone != null) {
        phone.setText(contact.getNumber());
        }

        String imageUri=contact.getPic();
        Picasso.get().load(imageUri).fit().centerCrop()
                .placeholder(R.drawable.ic_user_demo)
                .error(R.drawable.ic_user_demo)
                .into(im);
        String st = contact.getStatus();
        String st2 = "online";
         if(st.equals(st2))
         {
                 tvOnline.setText("online");
                 imgOnline.setImageResource(R.drawable.ic_online);

         }
        else {
                 tvOnline.setText("Offline");
                 imgOnline.setImageResource(R.drawable.ic_offline);
         }
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= Name.getText().toString();
                String phone1 = phone.getText().toString();
                String uid = uid1.getText().toString();
                Intent intent = new Intent(getContext(), ContactLocationActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("phone",phone1);
                intent.putExtra("uid",uid);
                context.startActivity(intent);
            }
        });
        Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= Name.getText().toString();
                String phone1 = phone.getText().toString();
                String uid = uid1.getText().toString();
                Intent intent = new Intent(getContext(), ContactLocationActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("phone",phone1);
                intent.putExtra("uid",uid);
                context.startActivity(intent);
            }
        });
        uid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= Name.getText().toString();
                String phone1 = phone.getText().toString();
                String uid = uid1.getText().toString();
                Intent intent = new Intent(getContext(), ContactLocationActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("phone",phone1);
                intent.putExtra("uid",uid);
                context.startActivity(intent);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name= Name.getText().toString();
            String phone1 = phone.getText().toString();
            String uid = uid1.getText().toString();
            Intent intent = new Intent(getContext(), ContactLocationActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("phone",phone1);
            intent.putExtra("uid",uid);
            context.startActivity(intent);
        }
        });
    imgOnline.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name= Name.getText().toString();
            String phone1 = phone.getText().toString();
            String uid = uid1.getText().toString();
            Intent intent = new Intent(getContext(), ContactLocationActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("phone",phone1);
            intent.putExtra("uid",uid);
            context.startActivity(intent);
        }
    });
    tvOnline.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name= Name.getText().toString();
            String phone1 = phone.getText().toString();
            String uid = uid1.getText().toString();
            Intent intent = new Intent(getContext(), ContactLocationActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("phone",phone1);
            intent.putExtra("uid",uid);
            context.startActivity(intent);
        }
    });

    ll.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name= Name.getText().toString();
            String phone1 = phone.getText().toString();
            String uid = uid1.getText().toString();
            Intent intent = new Intent(getContext(), ContactLocationActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("phone",phone1);
            intent.putExtra("uid",uid);
            context.startActivity(intent);
        }
    });


    return contactsView;
        }
}