package com.petrovdevelopment.common.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.petrovdevelopment.common.R;

import java.util.List;

public class MainMenuAdapter extends GenericAdapter<String> {
    private int cellMainMenuLayoutId;

	public MainMenuAdapter(List<String> data, Context context, int cellMainMenuLayoutId) {
		super(data, context);
        this.cellMainMenuLayoutId = cellMainMenuLayoutId;
	}

	@Override
	public void update(View view, int position) {
		TextView textView = (TextView) view.findViewById(R.id.text);
		textView.setText(getItem(position));
	}

	@Override
	public int getCellResourceId() {
		return cellMainMenuLayoutId;
	}

}
