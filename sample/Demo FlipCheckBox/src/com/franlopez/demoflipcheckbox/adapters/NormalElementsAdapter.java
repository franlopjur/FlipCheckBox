package com.franlopez.demoflipcheckbox.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.franlopez.demoflipcheckbox.R;
import com.franlopez.demoflipcheckbox.model.ModelElement;
import com.franlopez.flipcheckbox.FlipCheckBox;


public class NormalElementsAdapter extends BaseAdapter {
	
	@SuppressWarnings("unused")
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private List<ModelElement> mDataSet;
	
	public NormalElementsAdapter(Context context, List<ModelElement> dataset) {
		
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
	      convertView = mLayoutInflater.inflate(R.layout.element_normal_item_view, null);
	 
	      holder = new ViewHolder();
	      holder.flipCard = (FlipCheckBox) convertView.findViewById(R.id.flipCard);
	      holder.title = (TextView) convertView.findViewById(R.id.text);
	      convertView.setTag(holder);
	 
	    } else {
	 
	      holder = (ViewHolder)convertView.getTag();
	    }
	 
		holder.flipCard.setChecked(getItem(position).isChecked());
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				holder.flipCard.setChecked(!getItem(position).isChecked());
				getItem(position).setChecked(!getItem(position).isChecked());
				notifyDataSetChanged();
			}
		});
		
		holder.flipCard.setFlipAnimationDuration(100l);
	    holder.title.setText(getItem(position).getTitle());
	    
	    return convertView;
	}

	public class ViewHolder {
		
		FlipCheckBox flipCard;
		TextView title;
	}

	public void setDataset(List<ModelElement> posts) {

		mDataSet = posts;
		notifyDataSetChanged();
	}
}
