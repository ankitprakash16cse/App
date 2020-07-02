package com.erp.collection;

import android.content.Context;


import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public class ShiftAdapter extends  RecyclerView.Adapter<ShiftAdapter.ViewHolder>  {
    ArrayList<HashMap> TaskSheetData = new ArrayList<HashMap>();
    // private MyListData[] listdata;
    Context context;
    HashMap map;




    AutoCompleteDbAdapter dbHelper;
    DataBaseOP db = DataBaseOP.getInstance(context);
    // HashMap map;
    ContextCompat contextCompat;
    Globals g = Globals.getInstance(context);

    int quantity=0,rate=0,amount=0;
    List<Integer> arr = Arrays.asList(new Integer[10]);
    private String chkflag="";


    public ShiftAdapter(Context context, ArrayList<HashMap> TaskSheetData) {
        this.context= context;
        this.TaskSheetData=TaskSheetData;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_third, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        map=TaskSheetData.get(position);
        map.get("TASK_FEEDING_TYPE").toString();
        holder.tvLabel.setText(map.get("TASK_NAME").toString());


//       if(map.get())
//       {
//
//
//       }
        //holder.col_btn1.setText(map.get("NO_OF_COLUMN").toString());

        if( map.get("TASK_FEEDING_TYPE").toString().equals("BOOLEAN")) {
            holder.switch1.setVisibility(View.VISIBLE);
        }
       else {
            holder.etLabel.setVisibility(View.VISIBLE);
        }



//        holder.save_btn.setText(map.get("GROUP_NAME").toString());



    }




    @Override
    public int getItemCount() {
        return TaskSheetData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //public EditText amount;
        TextView tvLabel,tvLabel2;
        EditText etLabel;
        Switch switch1;



        public ViewHolder(View itemView) {
            super(itemView);

            this.tvLabel = (TextView) itemView.findViewById(R.id.eventsListEventRowText);
            this.etLabel=(EditText) itemView.findViewById(R.id.col1);
            this.tvLabel2=(TextView) itemView.findViewById(R.id.col2);
            //col_btn2.setVisibility(View.VISIBLE);

             this.switch1=(Switch) itemView.findViewById(R.id.switch_btn);
             switch1.setChecked(false);
            switch1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(switch1.isChecked()){
                        tvLabel2.setText("YES");
                        tvLabel2.setVisibility(View.VISIBLE);
                    }else{
                        tvLabel2.setText("NO");
                        tvLabel2.setVisibility(View.VISIBLE);
                    }
                }
            });

//            switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if(isChecked)
//                    {
//                        tvLabel2.setText("YES");
//                        tvLabel2.setVisibility(View.VISIBLE);
//                    } else
//                    {
//                        tvLabel2.setText("NO");
//                        tvLabel2.setVisibility(View.VISIBLE);
//                    }
//                }
//            });




        }
    }



}


