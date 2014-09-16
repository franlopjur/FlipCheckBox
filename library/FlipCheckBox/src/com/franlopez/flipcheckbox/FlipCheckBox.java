package com.franlopez.flipcheckbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

/*
 * Copyright 2014 Francisco Manuel Lopez Jurado
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/**
 * FlipCheckBox is a View that holds a boolean state ("not checked" and
 * "checked"). Although it's called <i>FlipCheckBox</i>, the View has no
 * similarity nor implement in any way the Android's CheckBox component.
 * <p/>
 * Usage is very simple. You just need to add the view to any layout (like you
 * would do with any other View) and you are good to go. Of course, if you want
 * further customizations, you can assign values to the needed properties in the
 * layout or programmatically. Please, refer to those attributes documentation.
 * <p/>
 * By default, when the View is clicked, it will switch its state, and an event
 * to the assigned
 * {@link OnFlipCheckedChangeListener#onCheckedChanged(FlipCheckBox, boolean)}
 * will be fired. Subscribe to that listener (using
 * {@link #setOnFlipCheckedChangeListener(OnFlipCheckedChangeListener)}) method.
 * 
 * @author Francisco Manuel Lopez Jurado
 */
public class FlipCheckBox extends ViewFlipper implements OnClickListener {

	/*
	 * TODO list: - Image "accept" animation customizable - Rear face
	 * customization (not just color)
	 */

	/** Dummy listener to prevent NPE's. */
	private static final OnFlipCheckedChangeListener DUMMY_LISTENER = new OnFlipCheckedChangeListener() {
		@Override
		public void onCheckedChanged(FlipCheckBox flipCardView,
				boolean isChecked) {
			// Log.d(FlipCheckBox.class.getSimpleName(), flipCardView.toString()
			// + ".onCheckedChanged: isChecked=" + isChecked);
		}
	};
	/** Child index to access the <i>front</i> view. */
	private static final int FRONT_VIEW_CHILD_INDEX = 0;
	/** Child index to access the <i>rear</i> view. */
	private static final int REAR_VIEW_CHILD_INDEX = 1;

	/** The item is not checked (and is displaying the <i>front</i> view). */
	public static final int STATUS_NOT_CHECKED = FRONT_VIEW_CHILD_INDEX;
	/** The item is checked (and is displaying the <i>rear</i> view). */
	public static final int STATUS_CHECKED = REAR_VIEW_CHILD_INDEX;
	/** Use this to apply a default resource value. */
	public static final int DEFAULT_RESOURCE = 0;

	/** Accept Animation. */
	private Animation acceptAnimation = AnimationUtils.loadAnimation(
			getContext(), R.anim.scale);
	/** View listener. */
	private OnFlipCheckedChangeListener mOnCheckedChangeListener = DUMMY_LISTENER;

	/* UI element references */

	/** Reference to the ViewFlipper. */
	private ViewFlipper mViewFlipper;
	/** Reference to the <i>rear</i> view's ImageView. */
	private ImageView mIVAccept;

	/* Attributes */

	/** Checked status. */
	private boolean mChecked;
	/** Show animations on checked status changes. */
	private boolean mShowAnimations;
	/** The <i>in</i> flip animation (not check -> check) resource identifier. */
	private int mInAnimationResId;
	/** The <i>out</i> flip animation (not check -> check) resource identifier. */
	private int mOutAnimationResId;
	/** The duration of the flip animation. */
	private long mFlipAnimationDuration;
	/** Show the "accept" image. */
	private boolean mShowAcceptImage;

	/* Constructors */

	/**
	 * Constructor.
	 * 
	 * @param context
	 *            The Activity Context.
	 */
	public FlipCheckBox(Context context) {
		super(context);
		initComponent(context, null, 0, 0);
	}

	/**
	 * Constructor.
	 * 
	 * @param context
	 *            The Activity Context.
	 * @param attrs
	 *            The view's attributes.
	 */
	public FlipCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		initComponent(context, attrs, 0, 0);
	}

	// // Uncomment if some day this components gets to API level 11 or above
	// /**
	// * Constructor.
	// *
	// * @param context
	// * The Activity Context.
	// * @param attrs
	// * The view's attributes.
	// * @param defStyle
	// * The default style to apply, if no one was provided.
	// */
	// public FlipCheckBox(Context context, AttributeSet attrs, int defStyle) {
	// super(context, attrs, defStyle);
	// initComponent(context, attrs, defStyle, 0);
	// }

	/* Initializers */

	/**
	 * Initialize this view, by inflating it, finding its UI element references,
	 * and applying the custom attributes provided by the programmer.
	 * 
	 * @param context
	 *            The Activity Context.
	 * @param attrs
	 *            The view's attributes.
	 * @param defStyle
	 *            The default style to apply, if no one was provided.
	 */
	private void initComponent(Context context, AttributeSet attrs,
			int defStyle, int defStyleRes) {
		// Inflate the view and find its UI elements references
		LayoutInflater.from(getContext()).inflate(R.layout.flipcheckbox_view,
				this, true);
		findViewReferences();

		// Read and apply provided attributes
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.FlipCheckBox, defStyle, defStyleRes);
		try {
			setFrontView(a.getResourceId(R.styleable.FlipCheckBox_frontLayout,
					0));
			setRearColorResource(a.getResourceId(
					R.styleable.FlipCheckBox_rearColor, 0));
			setAcceptImageResource(a.getResourceId(
					R.styleable.FlipCheckBox_rearAcceptImage, 0));
			setCheckedInmediate(a.getBoolean(R.styleable.FlipCheckBox_checked,
					false));
			setInAnimation(a.getResourceId(
					R.styleable.FlipCheckBox_inAnimation, 0));
			setOutAnimation(a.getResourceId(
					R.styleable.FlipCheckBox_outAnimation, 0));
			setShowAnimations(a.getBoolean(
					R.styleable.FlipCheckBox_showAnimations, true));
			setFlipAnimationDuration((long) a.getInteger(
					R.styleable.FlipCheckBox_flipAnimationDuration, 150));
			setShowAcceptImage(a.getBoolean(
					R.styleable.FlipCheckBox_showAcceptImage, true));
		} catch (Exception ex) {
			Log.e(FlipCheckBox.class.getSimpleName(),
					"Error applying provided attributes");
			throw new RuntimeException(ex);
		} finally {
			a.recycle();
		}

		// Apply default OnClickListener
		setOnClickListener(this);
	}

	/**
	 * Initialize "in" animation (check state from "not checked" to "checked"),
	 * if needed. Will do nothing if the animation was already prepared.
	 * <p/>
	 * <b>Important:</b> Call this method only after calling
	 * {@link #setInAnimation(int)}. Otherwise an exception will be thrown.
	 */
	private void initInAnimation() {
		if (isShowingAnimations()) {
			mViewFlipper.setInAnimation(getContext(), getInAnimationResId());
			mViewFlipper.getInAnimation().setDuration(mFlipAnimationDuration);
		}
	}

	/**
	 * Initialize "out" animation (check state from "checked" to "not checked"),
	 * if needed. Will do nothing if the animation was already prepared.
	 * <p/>
	 * <b>Important:</b> Call this method only after calling
	 * {@link #setOutAnimation(int)}. Otherwise an exception will be thrown.
	 */
	private void initOutAnimation() {
		if (isShowingAnimations()) {
			mViewFlipper.setOutAnimation(getContext(), getOutAnimationResId());
			mViewFlipper.getOutAnimation().setDuration(mFlipAnimationDuration);
		}
	}

	/**
	 * Find this component's view references.
	 */
	private void findViewReferences() {
		mViewFlipper = (ViewFlipper) this;
		mIVAccept = (ImageView) mViewFlipper.findViewById(R.id.include_back)
				.findViewById(R.id.iv__card_back__accept);
	}

	/* Attribute access */

	/**
	 * Set the front view to be displayed when this component is in a <i>not
	 * checked</i> state. If an invalid resource or {@link #DEFAULT_RESOURCE} is
	 * passed, then the default view will be applied.
	 * 
	 * @param layoutResId
	 *            The layout resource identifier.
	 */
	public void setFrontView(int layoutResId) {
		setFrontView(LayoutInflater.from(getContext()).inflate(
				layoutResId > 0 ? layoutResId : R.layout.simple_card_front,
				null));
	}

	/**
	 * Set the front view to be displayed when this component is in a <i>not
	 * checked</i> state. The provided <i>view</i> must not be {@code null}, or
	 * an exception will be thrown.
	 * 
	 * @param view
	 *            The view. Must not be {@code null}.
	 */
	public void setFrontView(View view) {
		if (view == null)
			throw new IllegalArgumentException(
					"The provided view must not be null");

		mViewFlipper.removeViewAt(FRONT_VIEW_CHILD_INDEX);
		mViewFlipper.addView(view, FRONT_VIEW_CHILD_INDEX);
	}

	/**
	 * Get the View being displayed on the <i>front</i>. The front view is
	 * displayed when the component is in a "not checked" state.
	 * 
	 * @return The <i>front</i> view.
	 */
	public View getFrontView() {
		return mViewFlipper.getChildAt(FRONT_VIEW_CHILD_INDEX);
	}

	/**
	 * Set the color to be used on the rear face of this component.
	 * 
	 * @param color
	 *            The color.
	 */
	public void setRearColor(int color) {
		mViewFlipper.findViewById(R.id.include_back).setBackgroundColor(color);
	}

	/**
	 * Set the color to be used on the rear face of this component. Passing an
	 * invalid resource or {@link #DEFAULT_RESOURCE}, will apply the default
	 * color.
	 * 
	 * @param resIdColor
	 *            The color resource.
	 */
	public void setRearColorResource(int resIdColor) {
		mViewFlipper.findViewById(R.id.include_back).setBackgroundResource(
				resIdColor > 0 ? resIdColor : R.drawable.fcb__rear_color);
	}

	/**
	 * Get the View being displayed on the <i>rear</i>. The front view is
	 * displayed when the component is in a "not checked" state.
	 * 
	 * @return The <i>rear</i> view.
	 */
	public View getRearView() {
		return mViewFlipper.getChildAt(REAR_VIEW_CHILD_INDEX);
	}

	/**
	 * Convenience method to replace the "accept" image being displayed on the
	 * rear of the component. Passing an invalid resource or
	 * {@link #DEFAULT_RESOURCE} will apply the default image.
	 * <p/>
	 * Check the method {@link #getAcceptImage()} if you need to apply further
	 * customizations to that view.
	 * 
	 * @param imageResId
	 *            The image resource identifier.
	 * @see #getAcceptImage()
	 */
	public void setAcceptImageResource(int imageResId) {
		mIVAccept.setImageResource(imageResId > 0 ? imageResId
				: R.drawable.ic_action_accept);
	}

	/**
	 * Get the ImageView reference which is being used to display the "accept"
	 * mark onto the rear view, so can apply further customizations if needed.
	 * 
	 * @return The "accept" ImageView.
	 */
	public ImageView getAcceptImage() {
		return mIVAccept;
	}

	/**
	 * Set whether or not the "accept" image on rear face should be displayed
	 * when the user checks this component.
	 * 
	 * @param showAcceptImage
	 *            Show or hide the "accept" image.
	 * @see #getAcceptImage()
	 */
	public void setShowAcceptImage(boolean showAcceptImage) {
		mShowAcceptImage = showAcceptImage;
		mIVAccept.setVisibility(showAcceptImage ? View.VISIBLE : View.GONE);
	}

	/**
	 * Returns whether or not the "accept" image on rear face is being
	 * displayed.
	 * 
	 * @return Will return {@code true} if showing, {@code false} otherwise.
	 */
	public boolean isShowingAcceptImage() {
		return mShowAcceptImage;
	}

	/**
	 * Set the state of this component to the given value, applying the
	 * corresponding animation, if possible.
	 * 
	 * @param checked
	 *            The component state.
	 */
	public void setChecked(boolean checked) {
		mChecked = checked;
		mViewFlipper.setDisplayedChild(checked ? STATUS_CHECKED
				: STATUS_NOT_CHECKED);
		if (isChecked())
			mIVAccept.startAnimation(acceptAnimation);
		mOnCheckedChangeListener.onCheckedChanged(this, isChecked());
	}

	/**
	 * Set the state of this component to the given value, without applying the
	 * corresponding animation, and without firing an event.
	 * 
	 * @param checked
	 *            The component state.
	 */
	public void setCheckedInmediate(boolean checked) {
		mChecked = checked;
		if (mViewFlipper.getInAnimation() != null)
			mViewFlipper.setInAnimation(null);
		if (mViewFlipper.getOutAnimation() != null)
			mViewFlipper.setOutAnimation(null);
		mViewFlipper.setDisplayedChild(checked ? STATUS_CHECKED
				: STATUS_NOT_CHECKED);
		initInAnimation();
		initOutAnimation();
	}

	/**
	 * Returns whether or not this component is in a checked state.
	 * 
	 * @return Whether or not this component is checked.
	 */
	public boolean isChecked() {
		return mChecked;
	}

	/**
	 * Set whether or not animations should be played on checked state changes.
	 * 
	 * @param showAnimations
	 *            Show animations on checked state changes.
	 */
	public void setShowAnimations(boolean showAnimations) {
		mShowAnimations = showAnimations;
		initInAnimation();
		initOutAnimation();
	}

	/**
	 * Return whether or not this component is displaying animations upon state
	 * changes.
	 * 
	 * @return Whether or not the component is animation state changes.
	 */
	public boolean isShowingAnimations() {
		return mShowAnimations;
	}

	/**
	 * Set the animation to be used when the component goes from "not checked"
	 * to "checked" state. If an invalid resource or {@link #DEFAULT_RESOURCE}
	 * is provided, then the default "in" animation will be set.
	 * 
	 * @param inAnimationResId
	 *            The animation resource identifier.
	 */
	public void setInAnimation(int inAnimationResId) {
		mInAnimationResId = inAnimationResId > 0 ? inAnimationResId
				: R.anim.grow_from_middle;
		initInAnimation();
	}

	/**
	 * Get the animation resource being used as "in" animation.
	 * 
	 * @return The "in" animation resource.
	 */
	public int getInAnimationResId() {
		return mInAnimationResId;
	}

	/**
	 * Set the animation to be used when the component goes from "checked" to
	 * "not checked" state. If an invalid resource or {@link #DEFAULT_RESOURCE}
	 * is provided, then the default "out" animation will be set.
	 * 
	 * @param outAnimationResId
	 *            The animation resource identifier
	 */
	public void setOutAnimation(int outAnimationResId) {
		mOutAnimationResId = outAnimationResId > 0 ? outAnimationResId
				: R.anim.shrink_to_middle;
		initOutAnimation();
	}

	/**
	 * Get the animation resource being used as "out" animation.
	 * 
	 * @return The "out" animation resource.
	 */
	public int getOutAnimationResId() {
		return mOutAnimationResId;
	}

	/**
	 * Set the duration of the state change animation.
	 * 
	 * @param flipAnimationDuration
	 *            The animation duration in milliseconds.
	 */
	public void setFlipAnimationDuration(long flipAnimationDuration) {
		mFlipAnimationDuration = flipAnimationDuration;
		initInAnimation();
		initOutAnimation();
	}

	/**
	 * Get the duration of the state change animation.
	 * 
	 * @return The animation duration in milliseconds.
	 */
	public long getFlipAnimationDuration() {
		return mFlipAnimationDuration;
	}

	/* Logic */

	/**
	 * Switch the state of this component.
	 */
	public void switchChecked() {
		setChecked(!isChecked());
	}

	/* Listeners */

	/**
	 * Set the listener that will receive call backs upon state changes. Set to
	 * null to remove it.
	 * 
	 * @param listener
	 *            The listener.
	 */
	public void setOnFlipCheckedChangeListener(
			OnFlipCheckedChangeListener listener) {
		mOnCheckedChangeListener = listener != null ? listener : DUMMY_LISTENER;
	}

	/**
	 * Get the listener receiving call backs upon state changes.
	 * 
	 * @return
	 */
	public OnFlipCheckedChangeListener getOnFlipCheckedChangeListener() {
		return mOnCheckedChangeListener;
	}

	@Override
	public void onClick(View view) {
		switchChecked();
	}

	/* State management */

	@Override
	public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);
		ss.checked = isChecked();
		ss.showAnimations = isShowingAnimations();
		ss.inAnimationResId = getInAnimationResId();
		ss.outAnimationResId = getOutAnimationResId();
		ss.flipAnimationDuration = getFlipAnimationDuration();
		ss.showAcceptImage = isShowingAcceptImage();
		return ss;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());
		setCheckedInmediate(ss.checked);
		setInAnimation(ss.inAnimationResId);
		setOutAnimation(ss.outAnimationResId);
		setShowAnimations(ss.showAnimations);
		setFlipAnimationDuration(ss.flipAnimationDuration);
		setShowAcceptImage(ss.showAcceptImage);
		requestLayout();
	}

	static class SavedState extends BaseSavedState {
		boolean checked;
		boolean showAnimations;
		int inAnimationResId;
		int outAnimationResId;
		long flipAnimationDuration;
		boolean showAcceptImage;

		SavedState(Parcelable superState) {
			super(superState);
		}

		private SavedState(Parcel in) {
			super(in);
			checked = in.readInt() == 1;
			showAnimations = in.readInt() == 1;
			inAnimationResId = in.readInt();
			outAnimationResId = in.readInt();
			flipAnimationDuration = in.readLong();
			showAcceptImage = in.readInt() == 1;
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeInt(checked ? 1 : 0);
			out.writeInt(showAnimations ? 1 : 0);
			out.writeInt(inAnimationResId);
			out.writeInt(outAnimationResId);
			out.writeLong(flipAnimationDuration);
			out.writeInt(showAcceptImage ? 1 : 0);
		}

		@Override
		public String toString() {
			return "CompoundButton.SavedState{"
					+ Integer.toHexString(System.identityHashCode(this))
					+ " checked=" + checked + " showAnimations="
					+ showAnimations + " inAnimationResId=" + inAnimationResId
					+ " outAnimationResId=" + outAnimationResId
					+ " flipAnimationDuration=" + flipAnimationDuration
					+ " showAcceptImage=" + showAcceptImage + "}";
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
}