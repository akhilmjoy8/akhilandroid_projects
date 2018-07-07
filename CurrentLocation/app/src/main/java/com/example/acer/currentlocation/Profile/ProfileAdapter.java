package com.example.acer.currentlocation.Profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.acer.currentlocation.Navigation.MapsNavigationActivity;
import com.example.acer.currentlocation.R;
import com.example.acer.currentlocation.contacts.ContactModel;
import com.example.acer.currentlocation.contacts.CustomAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileAdapter extends ArrayAdapter<locationView> {

        int resource;
        String response;
        Context context;
        private List<locationView> items;
        private ProfileAdapter adapter;

public ProfileAdapter(Context context, int resource, List<locationView> items) {
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
       final locationView contact = getItem(position);
        if (convertView == null) {
        contactsView = new LinearLayout(getContext());
        String inflater = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater vi;
        vi = (LayoutInflater) getContext().getSystemService(inflater);
        vi.inflate(resource, contactsView, true);
        } else {
        contactsView = (LinearLayout) convertView;
        }

        final TextView uname = contactsView.findViewById(R.id.uname);
        final TextView lat = contactsView.findViewById(R.id.lat);
        final TextView lng = contactsView.findViewById(R.id.lng);
        final TextView name = contactsView.findViewById(R.id.name);
        final TextView Description = contactsView.findViewById(R.id.tvDescription);
        final ImageView uimg = contactsView.findViewById(R.id.imgProfile);
        final  ImageView photo = contactsView.findViewById(R.id.photo);
        final  ImageView star1 = contactsView.findViewById(R.id.imstar1);
        final  ImageView star2 = contactsView.findViewById(R.id.imstar2);
        final  ImageView star3 = contactsView.findViewById(R.id.imstar3);
        final  ImageView star4 = contactsView.findViewById(R.id.imstar4);
        final  ImageView star5 = contactsView.findViewById(R.id.imstar5);

        String r=contact.getRate();
        if(r.equals("1"))
        {
            star1.setImageResource(R.drawable.ic_star_yellow);
            star2.setImageResource(R.drawable.ic_white_star);
            star3.setImageResource(R.drawable.ic_white_star);
            star4.setImageResource(R.drawable.ic_white_star);
            star5.setImageResource(R.drawable.ic_white_star);
        }
        else if(r.equals("2"))
        {
            star1.setImageResource(R.drawable.ic_star_yellow);
            star2.setImageResource(R.drawable.ic_star_yellow);
            star3.setImageResource(R.drawable.ic_white_star);
            star4.setImageResource(R.drawable.ic_white_star);
            star5.setImageResource(R.drawable.ic_white_star);
        }
        else if(r.equals("3"))
        {
            star1.setImageResource(R.drawable.ic_star_yellow);
            star2.setImageResource(R.drawable.ic_star_yellow);
            star3.setImageResource(R.drawable.ic_star_yellow);
            star4.setImageResource(R.drawable.ic_white_star);
            star5.setImageResource(R.drawable.ic_white_star);
        }
        else if(r.equals("4"))
        {
            star1.setImageResource(R.drawable.ic_star_yellow);
            star2.setImageResource(R.drawable.ic_star_yellow);
            star3.setImageResource(R.drawable.ic_star_yellow);
            star4.setImageResource(R.drawable.ic_star_yellow);
            star5.setImageResource(R.drawable.ic_white_star);
        }
        else
        {
            star1.setImageResource(R.drawable.ic_star_yellow);
            star2.setImageResource(R.drawable.ic_star_yellow);
            star3.setImageResource(R.drawable.ic_star_yellow);
            star4.setImageResource(R.drawable.ic_star_yellow);
            star5.setImageResource(R.drawable.ic_star_yellow);
        }
            lat.setText(contact.getLat());
            lng.setText(contact.getLng());
            uname.setText(contact.getUname());
            name.setText(contact.getName());
            Description.setText(contact.getDesc());

           Picasso.get().load(contact.getUpic()).fit().centerCrop()
            .placeholder(R.drawable.ic_user_demo)
            .error(R.drawable.ic_user_demo)
            .into(uimg);
          Picasso.get().load(contact.getPhoto()).fit().centerCrop()
            .placeholder(R.drawable.ic_noimage)
            .error(R.drawable.ic_noimage)
            .into(photo);

          photo.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(getContext(), MapsNavigationActivity.class);
                  intent.putExtra("lat",contact.getLat());
                  intent.putExtra("lng",contact.getLng());
                  intent.putExtra("name",contact.getUname());
                  intent.putExtra("phone",contact.getPhone());
                  context.startActivity(intent);
              }
          });

        return contactsView;
        }
        }