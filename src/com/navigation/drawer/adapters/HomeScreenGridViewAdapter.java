package com.navigation.drawer.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.navigation.drawer.models.Items;
import com.erp.collection.Globals;

/**
 * @author dipenp
 *
 */
public class HomeScreenGridViewAdapter extends BaseAdapter {

	private Context _context;
	private ArrayList<Items> _items;
	Globals g;

	public HomeScreenGridViewAdapter(Context _context, ArrayList<Items> _items) 
	{
		this._context = _context;
		this._items = _items;
		g=Globals.getInstance(_context);
	}

	private class ViewHolder {
		TextView itemName;
		ImageView img;
		
	}

	@Override
	public int getCount() {

		return _items.size();
	}

	@Override
	public Object getItem(int position) {

		return _items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) _context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
		{
			convertView = mInflater.inflate(com.erp.collection.R.layout.gridview_item_layout, null);
			holder = new ViewHolder();
			holder.itemName = (TextView) convertView.findViewById(com.erp.collection.R.id.item_name_txtview);			
			holder.img=(ImageView) convertView.findViewById(com.erp.collection.R.id.item_icon_imgview);						 
			convertView.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) convertView.getTag();
		}

		Items item = (Items) getItem(position);
		holder.itemName.setText(item.getItemName());
		 /*if(g.arrImg[position].equalsIgnoreCase("newsrorym"))
			 holder.img.setImageResource(R.drawable.newsrorym);					  
		   else if(g.arrImg[position].equalsIgnoreCase("imageuploadm"))
			   holder.img.setImageResource(R.drawable.clickphoto);	
		   else if(g.arrImg[position].equalsIgnoreCase("mutipleimguploadm"))
			   holder.img.setImageResource(R.drawable.mutipleimguploadm);	
		   else if(g.arrImg[position].equalsIgnoreCase("storylistm"))
			   holder.img.setImageResource(R.drawable.storylistm);	
		   else if(g.arrImg[position].equalsIgnoreCase("storyassignm"))
			   holder.img.setImageResource(R.drawable.storyassignm);	
		   else if(g.arrImg[position].equalsIgnoreCase("deletedata"))
			   holder.img.setImageResource(R.drawable.deletedata);	
		   else if(g.arrImg[position].equalsIgnoreCase("exit1"))
			   holder.img.setImageResource(R.drawable.exit1);	
		   else if(g.arrImg[position].equalsIgnoreCase("video"))
			   holder.img.setImageResource(R.drawable.videor);	
		   else if(g.arrImg[position].equalsIgnoreCase("audio"))
			   holder.img.setImageResource(R.drawable.audio);	
		   else if(g.arrImg[position].equalsIgnoreCase("event"))
			   holder.img.setImageResource(R.drawable.event);*/	
				
		return convertView;
	}
}
