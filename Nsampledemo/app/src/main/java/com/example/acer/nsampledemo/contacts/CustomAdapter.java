package com.example.acer.nsampledemo.contacts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.acer.nsampledemo.R;
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
        String st=contact.getStatus();
        String st2="online";
         if(st.equals(st2))
         {
                 tvOnline.setText("online");
                 imgOnline.setImageResource(R.drawable.ic_online);

         }
         else
         {
                 tvOnline.setText("offline");
                 imgOnline.setImageResource(R.drawable.ic_offline);
         }
        im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     String name = Name.getText().toString();
                     String phone1 = phone.getText().toString();
                     String  uid2 = uid1.getText().toString();

                }
        });

        return contactsView;
        }
}