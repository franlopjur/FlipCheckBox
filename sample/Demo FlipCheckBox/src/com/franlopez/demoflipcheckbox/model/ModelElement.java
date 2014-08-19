package com.franlopez.demoflipcheckbox.model;

public class ModelElement {

	private String title;
	private boolean checked;

	public ModelElement(String title, boolean checked) {
		super();
		this.title = title;
		this.checked = checked;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
