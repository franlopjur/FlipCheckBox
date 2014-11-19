package com.franlopez.demoflipcheckbox.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.franlopez.demoflipcheckbox.R;
import com.franlopez.demoflipcheckbox.model.ModelElement;
import com.franlopez.demoflipcheckbox.util.DemoConstants;
import com.franlopez.flipcheckbox.FlipCheckBox;
import com.franlopez.flipcheckbox.OnFlipCheckedChangeListener;
import com.squareup.picasso.Picasso;

public class CustomElementsAdapter extends BaseAdapter {
	
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private List<ModelElement> mDataSet;
	
	public CustomElementsAdapter(Context context, List<ModelElement> dataset) {
		
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
		
		final ModelElement item = getItem(position);
		
		final ViewHolder holder;
		if(convertView == null) {
	      convertView = mLayoutInflater.inflate(R.layout.element_custom_item_view, null);
	 
	      holder = new ViewHolder();
	      holder.flipCard = (FlipCheckBox) convertView.findViewById(R.id.flipCard);
	      holder.title = (TextView) convertView.findViewById(R.id.text);
	      convertView.setTag(holder);
	 
	    } else {
	 
	      holder = (ViewHolder)convertView.getTag();
	    }
	 
		holder.flipCard.setCheckedInmediate(item.isChecked());
		holder.flipCard
		.setOnFlipCheckedChangeListener(new OnCheckedChangeListener(
				item));
		
		if(holder.flipCard.getFrontView() != null && holder.flipCard != null)
			Picasso.with(mContext).load(DemoConstants.URL_IMAGE).into((ImageView) holder.flipCard.getFrontView().findViewById(R.id.image));
		
		holder.flipCard.setFlipAnimationDuration(150l);
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
	
	private class OnCheckedChangeListener implements OnFlipCheckedChangeListener {
	
		
		final ModelElement item;
	
		public OnCheckedChangeListener(ModelElement item) {
			this.item = item;
		}
		
		@Override
		public void onCheckedChanged(FlipCheckBox flipCardView,
				boolean isChecked) {
			item.setChecked(isChecked);
		}
	}
}