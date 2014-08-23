FlipCheckBox
====

###Description

Give your collections (on any `View` at all) a fancier look by adding a cool way to check elements. If your list can multi-select elements, you can add this component as a "status indicator". This is basically a customizable view flipper, with a "_front_" and "_rear_" faces, and an "accept" image in the "_rear_" that will animate changes in its state, mostly like the [GMail][1] app.

_Note: This is a fork from [Fran Lopez's][1] [FlipCheckBox][2] library, which mainly tries to document and fix a few issues, due to the library's shot lifespan._

###Usage

The component will remain decoupled from any other `View` at all time, needing only an Activity context in order to work properly (for inflation purposes basically). You can inflate this component, as well as apply attributes, from `xml` or programmatically.
<br/>
```xml
<com.franlopez.flipcheckbox.FlipCheckBox
  android:id="@+id/flipCard"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content" />
```
<br/>
Currently supported attributes are:

* `checked`: Set the starting state as "checked" or "not checked".
* `showAnimations`: Set whether or not to show animation upon state changes.
* `inAnimation`: Animation resource to apply when changing from "not checked" to "checked" state.
* `outAnimation`: Animation resource to apply when changing from "checked" to "not checked" state.
* `flipAnimationDuration`: How much time should the flip animation take.
* `frontLayout`: Layout to inflate on the "_front_" view ("not checked" state).
* `rearColor`: Color to apply on the "_rear_" view ("checked" state).
* `rearAcceptImage`: Representative image from the "checked" state.
* `showAcceptImage`: Whether or not the "accept" image should be shown.

The component also presents an interface (`OnFlipCheckedChangeListener`) which will receive events when the state change. Please, keep in mind that the library methods `setChecked(boolean)` and `switchChecked()` will fire an event when called, while `setCheckedInmediate(boolean)` **will not**.

###Demo App

You can download a demo app from [this][3] link.

###License

    Copyright 2014 Francisco Manuel López Jurado

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use  this file except in compliance with the License. You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

[1]: https://github.com/franlopjur
[2]: https://play.google.com/store/apps/details?id=com.google.android.gm&hl=es
[3]: https://play.google.com/store/apps/details?id=com.franlopez.demoflipcheckbox