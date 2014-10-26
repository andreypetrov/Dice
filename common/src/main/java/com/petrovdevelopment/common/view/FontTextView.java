package com.petrovdevelopment.common.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.petrovdevelopment.common.R;
import com.petrovdevelopment.common.util.U;
import com.petrovdevelopment.common.util.ViewAttrsUtil;

/**
 * A text view which uses the special icon flight network font located at assets/fonts/fn.ttf
 * 
 * @author Andrey
 * 
 */
public class FontTextView extends TextView {
	// public static final String ASSETS_FONT_LOCATION = "fonts/flightnetwork.ttf";

	private String mAssetFontLocation;

	public FontTextView(Context context) {
		super(context);
		initialize(context);
	}

	public FontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		ViewAttrsUtil viewAttrsUtil = new ViewAttrsUtil(context, attrs, R.styleable.FontTextView);
		mAssetFontLocation = viewAttrsUtil.getStringFromXml(R.styleable.FontTextView_font);
		U.log(this, "font location: " + mAssetFontLocation);
		if (mAssetFontLocation != null && !mAssetFontLocation.equals("")) {
			if (!isInEditMode()) {
				setTypeface(Typeface.createFromAsset(context.getAssets(), mAssetFontLocation));
			}
		}
		initialize(context);
	}

	private void initialize(Context context) {

	}

}