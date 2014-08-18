FlipCheckBox
============

## Description

Is a Animated CheckBox with custom views

## Demo App

In this link you can download the demo app.

## Configurations

1. frontView: define the layout for Front View
2. isChecked: define if is selected or not
3. resourceAccept: define the image accept for BackView
4. backgroundBackView: define the background color for Back View

## Basic Usage

1. Create the xml
```xml
    <com.franlopez.flipcheckbox.FlipCheckBox
        android:id="@+id/flipCard"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:frontView="@layout/custom_flipcheckbox_front"
        app:isChecked="false"
        app:resourceAccept="@drawable/ic_launcher" />
```

2. Initialize component
```java

	      FlipCheckBox flipCheckbox = (FlipCheckBox) convertView.findViewById(R.id.flipCheckbox);

	      // When the item is selected:
	      flipCheckbox.setCheckedWithAnimation(!flipCheckbox.isChecked());
```
## License

Copyright 2014 Francisco Manuel López Jurado

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
