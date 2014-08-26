package com.franlopez.demoflipcheckbox.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.franlopez.demoflipcheckbox.R;
import com.franlopez.demoflipcheckbox.adapters.NormalElementsAdapter;
import com.franlopez.demoflipcheckbox.model.ModelElement;

public class NormalStyleActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null)
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new NormalStyleFragment())
					.commit();
	}

	/**
	 * A fragment representing the back of the card.
	 */
	public static class NormalStyleFragment extends Fragment {

		private List<ModelElement> items;

		public NormalStyleFragment() {
			setRetainInstance(true);
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			items = new ArrayList<ModelElement>();
			for (int i = 0; i < 50; i++)
				items.add(new ModelElement("Item " + i, false));
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_normal_style, container,
					false);
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			((ListView) getView().findViewById(R.id.listView))
					.setAdapter(new NormalElementsAdapter(getActivity(), items));
		}
	}
}
