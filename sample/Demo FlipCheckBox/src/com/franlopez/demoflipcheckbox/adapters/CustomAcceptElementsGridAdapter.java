package com.franlopez.demoflipcheckbox.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.franlopez.demoflipcheckbox.R;
import com.franlopez.demoflipcheckbox.model.ModelElement;
import com.franlopez.demoflipcheckbox.util.DemoConstants;
import com.franlopez.flipcheckbox.FlipCheckBox;
import com.squareup.picasso.Picasso;


public class CustomAcceptElementsGridAdapter extends BaseAdapter {
	
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private List<ModelElement> mDataSet;
	
	public CustomAcceptElementsGridAdapter(Context context, List<ModelElement> dataset) {
		
		mContext = context;
		mDataSet = dataset;
	    mLayoutInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return mDataSet.size();
	}

	@Override
	public ModelElement getItem(int position) {
		return mDataSet.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView == null) {
	      convertView = mLayoutInflater.inflate(R.layout.element_custom_accept_item_grid_view, null);
	 
	      holder = new ViewHolder();
	      holder.flipCard = (FlipCheckBox) convertView.findViewById(R.id.flipCard);
	      convertView.setTag(holder);
	 
	    } else {
	 
	      holder = (ViewHolder)convertView.getTag();
	    }
	 
		holder.flipCard.setChecked(getItem(position).isChecked());
		
		if(holder.flipCard.getFrontView() != null)
			Picasso.with(mContext).load(DemoConstants.URL_IMAGE).into((ImageView) holder.flipCard.getFrontView().findViewById(R.id.image));
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				holder.flipCard.setCheckedWithAnimation(!getItem(position).isChecked());
				getItem(position).setChecked(!getItem(position).isChecked());
				notifyDataSetChanged();
			}
		});
		
		holder.flipCard.setDurationAnimation(2000);
	    
	    return convertView;
	}

	public class ViewHolder {
		
		FlipCheckBox flipCard;
	}

	public void setDataset(List<ModelElement> posts) {

		mDataSet = posts;
		notifyDataSetChanged();
	}
}
