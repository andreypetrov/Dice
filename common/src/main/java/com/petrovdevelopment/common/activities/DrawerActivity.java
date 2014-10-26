package com.petrovdevelopment.common.activities;

import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;
import android.widget.FrameLayout;

import com.petrovdevelopment.common.R;

import roboguice.activity.RoboActivity;

public class DrawerActivity extends RoboActivity implements DrawerListener {
	protected DrawerLayout mDrawerLayout;

	protected FrameLayout leftDrawer;
	protected FrameLayout rightDrawer;
	private boolean isDrawerOpen = false;

	/**
	 * Call this method from children classes, after the setContentView has been called, to init the drawers. The layout of the DrawerActivity should include
	 * the correct ids, if they are about to be used. All of those views are optional
	 */
	public void initDrawersHandlers() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (mDrawerLayout != null) {
			mDrawerLayout.setDrawerListener(this);
		    leftDrawer = (FrameLayout) findViewById(R.id.leftDrawer);
			rightDrawer = (FrameLayout) findViewById(R.id.rightDrawer);
		} // else there is no drawer

	}

    /**
     * Open the left drawer. This method can be assigned directly in xml as onClick action
     * @param v
     */
	public void openLeftDrawer(View v) {
		if (mDrawerLayout != null && getLeftDrawer() != null) mDrawerLayout.openDrawer(getLeftDrawer());
	}

	/**
	 * Open the right drawer. This method can be assigned directly in xml as onClick action
	 * 
	 * @param v
	 */
	public void openRightDrawer(View v) {
		if (mDrawerLayout != null && getRightDrawer() != null) mDrawerLayout.openDrawer(getRightDrawer());
	}

	/**
	 * Close the currently open drawer. At any time there can be only one open drawer
	 * 
	 * @param v
	 */
	public void closeDrawers(View v) {
		if (mDrawerLayout != null) {
			if (mDrawerLayout.isDrawerOpen(getRightDrawer())) mDrawerLayout.closeDrawer(getRightDrawer());
			if (mDrawerLayout.isDrawerOpen(getLeftDrawer())) mDrawerLayout.closeDrawer(getLeftDrawer());
		}
	}

	@Override
	public void onDrawerClosed(View v) {
		isDrawerOpen = false;
	}

	@Override
	public void onDrawerOpened(View v) {
		isDrawerOpen = true;
	}

	@Override
	public void onDrawerSlide(View arg0, float arg1) {}

	@Override
	public void onDrawerStateChanged(int arg0) {}

	@Override
	public void onBackPressed() {
		if (isDrawerOpen) closeDrawers(null);
		else super.onBackPressed();
	}

	public FrameLayout getLeftDrawer() {
		return leftDrawer;
	}

	public FrameLayout getRightDrawer() {
		return rightDrawer;
	}

}
