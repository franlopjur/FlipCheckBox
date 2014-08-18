package com.franlopez.flipcheckbox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

/**
 * FlipCheckBox is a checkbox that can tip over when an item has been selected.
 * @author Francisco Manuel López Jurado
 *
 *  Copyright 2014 Francisco Manuel López Jurado
	Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
	You may obtain a copy of the License at:

		http://www.apache.org/licenses/LICENSE-2.0
	Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
	and limitations under the License.
 */
public class FlipCheckBox extends LinearLayout {

	/**
	 * Status available
	 */
	public static final int STATUS_NONE = 0;
	public static final int STATUS_ACCEPT = 1;
	
	/**
	 * Only the first view can be edited
	 */
	private ViewFlipper mViewFlipper;
	private View mFrontView;
	private ImageView mIVOk;

	
	/**
	 * Values ​​that can be preloaded using parameters
	 */
	private boolean mChecked;
	private int mResIdFrontView;
	private int mResIdBackgroundBackColor;
	private int mResIdBackAccpetImage;
	
	/**
	 * Custom listener
	 */
	private OnFlipCheckedChangeListener mOnCheckedChangeListener;

	public FlipCheckBox(Context context) {
		this(context, null);
	}
	
	public FlipCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		TypedArray a = context.getTheme().obtainStyledAttributes(
		        attrs,
		        R.styleable.FlipCheckBox,
		        0, 0);

		   try {
		       mChecked = a.getBoolean(R.styleable.FlipCheckBox_isChecked, false);
		       mResIdFrontView = a.getResourceId(R.styleable.FlipCheckBox_frontView, 0);
		       mResIdBackgroundBackColor = a.getResourceId(R.styleable.FlipCheckBox_backgroundBackView, 0);
		       mResIdBackAccpetImage = a.getResourceId(R.styleable.FlipCheckBox_resourceAccept, 0);
		   } finally {
		       a.recycle();
		   }
		
        View view = LayoutInflater.from(getContext()).inflate(R.layout.flipcheckbox_view, this, true);

		getView(view);
		init();
	}

	// - ################################################
	// - ###########	Initializers		#############
	// - ################################################
	
	private void getView(View view) {
		
		mViewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);
		mIVOk = (ImageView) mViewFlipper.findViewById(R.id.inclide_back).findViewById(R.id.iv__card_back__ok);
	}
	
	private void init() {
		
		mViewFlipper.setInAnimation(getContext(), R.anim.grow_from_middle);
		mViewFlipper.setOutAnimation(getContext(), R.anim.shrink_to_middle);
		
		// - Configure with params
		setChecked(mChecked);
		replaceFrontView(mResIdFrontView);
		replaceBackgroundBackColor(mResIdBackgroundBackColor);
		replaceImageAccept(mResIdBackAccpetImage);
	}
	
	// - #####################################################################
	// - ###########	Configuration	##################
	// - #####################################################################
	
	private void replaceFrontView(int resViewId) {
		
		if(resViewId != 0 && mFrontView == null && mViewFlipper != null) {
			mFrontView = LayoutInflater.from(getContext()).inflate(resViewId, null);
			mViewFlipper.removeViewAt(0);
			mViewFlipper.addView(mFrontView, 0);
		}
	}
	
	private void replaceImageAccept(int resIdImage) {
		
		if(resIdImage != 0) 
			mIVOk.setImageResource(resIdImage);
	}

	private void replaceBackgroundBackColor(int resIdColor) {

		if(resIdColor != 0) 
			mViewFlipper.findViewById(R.id.inclide_back).setBackgroundResource(resIdColor);
	}

	// - #############################################
	// - ###########	Getters		##################
	// - #############################################
	
	public View getFrontView() {
		if(mViewFlipper != null && mViewFlipper.getChildCount() > 0)
			return mViewFlipper.getChildAt(0);
		else
			return null;
	}
	
	public View getBackView() {
		if(mViewFlipper != null && mViewFlipper.getChildCount() > 1)
			return mViewFlipper.getChildAt(1);
		else
			return null;
	}
	
	public boolean isChecked() {
		return mChecked;
	}
	
	// - #############################################
	// - ###########	Setters		##################
	// - #############################################
	
	public void setChecked(boolean checked) {
		
		mChecked = checked;
		
		mViewFlipper.setInAnimation(null);
		mViewFlipper.setOutAnimation(null);
		if(checked) {
			mViewFlipper.setDisplayedChild(STATUS_ACCEPT);;
		} else {
			mViewFlipper.setDisplayedChild(STATUS_NONE);;
		}
		
		if (mOnCheckedChangeListener != null) {
			mOnCheckedChangeListener.onCheckedChanged(this, mChecked);
		}
	}
	
	public void setOnFlipCheckedChangeListener(OnFlipCheckedChangeListener listener) {
		mOnCheckedChangeListener = listener;
	}
	
	public void setCheckedWithAnimation(boolean checked) {
		
		mChecked = checked;
		
		configureAnimations(R.anim.grow_from_middle, R.anim.shrink_to_middle);
		
		if(checked) 
			translateCover(STATUS_ACCEPT);
		else 
			translateCover(STATUS_NONE);
		
	}
	public void setDurationAnimation(int durationInMillis) {
		if(mViewFlipper != null && mViewFlipper.getInAnimation() != null && mViewFlipper.getOutAnimation() != null) {
			mViewFlipper.getInAnimation().setDuration(durationInMillis);
			mViewFlipper.getOutAnimation().setDuration(durationInMillis);
		}
	}
	
	// - #############################################
	// - ###########	Others		##################
	// - #############################################
	
	@SuppressLint("ClickableViewAccessibility")
	public boolean performClick() {
		
		setChecked(!mChecked);
		
		return mChecked;
	}
	
	public boolean performClickWithAnimations() {
		setCheckedWithAnimation(!mChecked);
		
		return mChecked;
	}
	
	private void configureAnimations(int resIdAnimEnter, int resIdAnimExit) {
		
		if(resIdAnimEnter == 0 || resIdAnimExit == 0)
			return;
		
		mViewFlipper.setInAnimation(getContext(), resIdAnimEnter);
		mViewFlipper.setOutAnimation(getContext(), resIdAnimExit);
	}
	
	
	public void translateCover(int status) {
		
		if(mViewFlipper.getInAnimation() == null || mViewFlipper.getOutAnimation() == null) 
			configureAnimations(R.anim.grow_from_middle, R.anim.shrink_to_middle);
		
		
		switch (status) {
		case 0:
			
			mViewFlipper.setDisplayedChild(STATUS_NONE);;
			break;
		case 1:
			
			mViewFlipper.setDisplayedChild(STATUS_ACCEPT);;
			mIVOk.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.scale));
			break;
		default:
			break;
		}
	}
	
	// - #########################################################
	// - ###########	Save and Restore		##################
	// - #########################################################
	
	static class SavedState extends BaseSavedState {
		boolean checked;

		SavedState(Parcelable superState) {
			super(superState);
		}

		private SavedState(Parcel in) {
			super(in);
			checked = (Boolean) in.readValue(null);
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeValue(checked);
		}

		@Override
		public String toString() {
			return "CompoundButton.SavedState{"
					+ Integer.toHexString(System.identityHashCode(this))
					+ " checked=" + checked + "}";
		}

		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}

	@Override
	public Parcelable onSaveInstanceState() {

		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);

		ss.checked = isChecked();
		return ss;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		SavedState ss = (SavedState) state;

		super.onRestoreInstanceState(ss.getSuperState());
		setChecked(ss.checked);
		requestLayout();
	}
}