package com.petrovdevelopment.common.view;

import android.animation.AnimatorSet;
import android.animation.AnimatorSet.Builder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.petrovdevelopment.common.R;
import com.petrovdevelopment.common.util.ViewAttrsUtil;

/**
 * A visual line made of segments. Each segment is representing a progress unit in the game.
 * The progress incrementation can be animated or instantaneous.
 * User has to pass in a step layout id. Which is the xml layout of the game progress bar
 */
public class GameProgressBar extends LinearLayout {
	public static final int DEFAULT_MAX = 10;
	public static final int DEFAULT_STEP_GAP_IN_PIXELS = 30;
	public static final int DEFAULT_STEP_WIDTH_IN_PIXELS = 30;
	public static final int DEFAULT_STEP_HEIGHT_IN_PIXELS = 30;
	public static final int ANIMATION_TIME_IN_MILLIS = 3000;

	int max;
	int value; // 0 based
	int stepLayoutId;
	int stepGap;
	int stepWidth;
	int stepHeight;

	int incrementDelta; // used for updating the animation when incrementing. A state kept by the listeners
	private AnimatorSet animatorSet;
	private Builder animatorSetBuilder;

	public GameProgressBar(Context context) {
		super(context);
		init();
	}

	public GameProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		ViewAttrsUtil viewAttrsUtil = new ViewAttrsUtil(context, attrs, R.styleable.GameProgressBar);
		max = viewAttrsUtil.getIntFromXml(R.styleable.GameProgressBar_max, DEFAULT_MAX);
		int progressValue = viewAttrsUtil.getIntFromXml(R.styleable.GameProgressBar_value, 0);
		stepLayoutId = viewAttrsUtil.getResourceIdFromXml(R.styleable.GameProgressBar_step_layout, -1);
		stepGap = viewAttrsUtil.getDimensionPixelSizeFromXml(R.styleable.GameProgressBar_step_gap, DEFAULT_STEP_GAP_IN_PIXELS);
		stepWidth = viewAttrsUtil.getDimensionPixelSizeFromXml(R.styleable.GameProgressBar_step_width, DEFAULT_STEP_GAP_IN_PIXELS);
		stepHeight = viewAttrsUtil.getDimensionPixelSizeFromXml(R.styleable.GameProgressBar_step_height, DEFAULT_STEP_GAP_IN_PIXELS);

		//U.log(this, "step gap: " + stepGap);
		setProgress(progressValue);
		init();
	}

	private void init() {

	}

	/**
	 * Add one to current progress value
	 * 
	 * @param isAnimated
	 *            whether we want the new step to show animating or immediately
	 */
	public void incrementProgress(boolean isAnimated) {
		if (getValue() >= getMax()) throw new IllegalStateException("Cannot increment progress above or equal to the bar size which is " + max);

		if (isAnimated) {
			View v = createStepView(0);
			addView(v);
			animateStepWidth(v);
		} else addView(createStepView(stepWidth));

		value++;
	}

	public void addViewAnimated() {
		final View v = createStepView(0);
		addView(v);
		animateStepWidth(v);
	}

	/**
	 * Remove one from current progress value
	 * 
	 */
	public void decrementProgress() {
		if (getValue() == 0) throw new IllegalStateException("Cannot decrement progress below 0");
		value--;
		removeViewAt(value);
	}

	/**
	 * Set progress from 0 to getSize()-1. Triggers corresponding animation, if any
	 * 
	 * @param progress
	 */
	private void setProgress(int progress) {
		//U.log(this, "progress: " + progress);
		if (progress >= getMax()) throw new IllegalStateException("Cannot set a progress above or equal to the bar size, which is " + max);
		if (progress < 0) throw new IllegalStateException("Cannot set a progress below 0");
		if (progress > getValue()) {
			for (int i = getValue(); i < progress; i++) {
				incrementProgress(false); // TODO chain animations if need be http://stackoverflow.com/questions/10092124/android-chain-animations
			}
		} else {
			for (int i = getValue(); i > progress; i--)
				decrementProgress();
		}

	}

	private void animateStepWidth(final View stepView) {
		ValueAnimator anim = ValueAnimator.ofInt(0, stepWidth).setDuration(ANIMATION_TIME_IN_MILLIS);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				int val = (Integer) animator.getAnimatedValue();
				LinearLayout.LayoutParams layoutParams = (LayoutParams) stepView.getLayoutParams();
				layoutParams.width = val;
				stepView.setLayoutParams(layoutParams);
			}
		});
		anim.start();
	}

	public boolean hasCompleted() {
		return getValue() >= getMax();
	}

	public int getValue() {
		return value;
	}

	public int getMax() {
		return max;
	}

	/**
	 * Create a new increment step view to add ot the layout
	 *
	 * @return
	 */
	private View createStepView(int width) {
		View v = inflate(getContext(), stepLayoutId, null);

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, stepHeight);
		layoutParams.gravity = Gravity.LEFT;

		if (getChildCount() > 0) {// if this is not the first view, then add margin (gap between increment steps)
			if (getOrientation() == HORIZONTAL) layoutParams.setMargins(stepGap, 0, 0, 0);
			else layoutParams.setMargins(0, stepGap, 0, 0);
		}
		v.setLayoutParams(layoutParams);
		return v;
	}
}
