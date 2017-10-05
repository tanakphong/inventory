package com.wesoft.inventory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter {

    int[] result;
    Context context;
    int[] imageId;
    private static LayoutInflater inflater = null;

    public CustomAdapter(MainActivity mainActivity, int[] osNameList, int[] osImages) {
        // TODO Auto-generated constructor stub
        result = osNameList;
        context = mainActivity;
        imageId = osImages;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView os_text;
        ImageView os_img;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.sample_gridlayout, null);
        holder.os_text = (TextView) rowView.findViewById(R.id.os_texts);
        holder.os_img = (ImageView) rowView.findViewById(R.id.os_images);

        holder.os_text.setText(result[position]);
        holder.os_img.setImageResource(imageId[position]);

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i;
                switch (result[position]) {
                    case R.string.register:
                        i = new Intent(context, RegisterActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(i);
                        break;
                    case R.string.setting:
                        i = new Intent(context, SettingActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(i);
                        break;
                    case R.string.imports:
                        i = new Intent(context, ImportActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(i);
                        break;
                    case R.string.verify:
                        i = new Intent(context, SettingSystemActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(i);
                        break;

                }
                Toast.makeText(context, "You Clicked " + context.getResources().getString(result[position]), Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    }

}