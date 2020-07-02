package com.erp.collection;

import java.util.ArrayList;
import java.util.HashMap;

import com.erp.collection.R;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomSpinnerEditorAdapter extends BaseAdapter //implements SpinnerAdapter
{	 
    private final Context activity;
    private  ArrayList<HashMap> asr=new ArrayList<HashMap>();
    LayoutInflater inflter;
    String Displaykey;
 
    int textsize=10;
    public CustomSpinnerEditorAdapter(Context context,ArrayList<HashMap> asr,int textsize,String Displaykey)
    {
    	this.asr=asr;
        this.Displaykey=Displaykey;
        this.textsize=textsize;
        activity = context;
        inflter = (LayoutInflater.from(context));
    } 
    public int getCount()
    {
        return asr.size();
    } 
    public Object getItem(int i)
    {
        return asr.get(i);
    }
 
    public long getItemId(int i)
    {
        return (long)i;
    }
 
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) 
    {
    	convertView = inflter.inflate(R.layout.custom_spinner_items, null);        
        TextView txt = (TextView) convertView.findViewById(R.id.textView);
        txt.setPadding(10, 10, 10, 10);
        txt.setTextSize(textsize);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(asr.get(position).get(Displaykey).toString());
        txt.setTextColor(Color.parseColor("#000000"));
        ImageView img=(ImageView)convertView.findViewById(R.id.imageView);
        img.setVisibility(View.GONE);
        return  convertView;
    }
 
    public View getView(int i, View view, ViewGroup viewgroup) 
    {
    	 view = inflter.inflate(R.layout.custom_spinner_items, null);        
         TextView txt = (TextView) view.findViewById(R.id.textView);      
         txt.setGravity(Gravity.LEFT);        
         txt.setTextSize(textsize);            
         txt.setText(asr.get(i).get(Displaykey).toString());
         txt.setTextColor(Color.parseColor("#000000"));
       
        return  view;
    }   
}