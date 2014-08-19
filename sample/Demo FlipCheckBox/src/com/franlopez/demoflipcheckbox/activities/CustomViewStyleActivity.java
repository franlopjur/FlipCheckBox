package com.franlopez.demoflipcheckbox.activities;

import java.util.ArrayList;
import java.util.List;

import com.franlopez.demoflipcheckbox.R;
import com.franlopez.demoflipcheckbox.R.id;
import com.franlopez.demoflipcheckbox.R.layout;
import com.franlopez.demoflipcheckbox.R.menu;
import com.franlopez.demoflipcheckbox.adapters.CustomElementsAdapter;
import com.franlopez.demoflipcheckbox.model.ModelElement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CustomViewStyleActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new CustomViewStyleFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A fragment representing the back of the card.
	 */
	public static class CustomViewStyleFragment extends Fragment {
		public CustomViewStyleFragment() {
		}

		private List<ModelElement> items;
		private CustomElementsAdapter adapter;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.fragment_custom_view_style,
					container, false);

			ListView listVIewItems = (ListView) view
					.findViewById(R.id.listView);

			items = new ArrayList<ModelElement>();

			for (int i = 0; i < 50; i++)
				items.add(new ModelElement("Item " + i, false));

			adapter = new CustomElementsAdapter(getActivity(), items);
			listVIewItems.setAdapter(adapter);

			return view;
		}
	}

}
