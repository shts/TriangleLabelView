TriangleLabelView
====================
Show triangle view.

<img src="/img/capture.png">

How to Use
=====


```
<jp.shts.android.library.TriangleLabelView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_alignParentLeft="true"
    android:layout_alignParentTop="true"
    app:backgroundColor="@color/yellow_900"
    app:corner="leftTop"
    app:labelBottomPadding="5dp"
    app:labelCenterPadding="0dp"
    app:labelTopPadding="10dp"
    app:primaryText="New"
    app:primaryTextColor="@color/yellow_500"
    app:primaryTextSize="16sp"
    app:secondaryText="01"
    app:secondaryTextColor="@color/yellow_100"
    app:secondaryTextSize="11sp" />

```

Install
=====
Just add the dependency to your `build.gradle`:

```groovy
dependencies {
    compile 'XXXXXXXXXXXXXXXXXXXXXXX'
}
```

To see how the TriangleLabelView are added to your xml layouts, check the sample project.

Credits
=======
I used [LabelView](https://github.com/corerzhang/LabelView) library by [Corer](https://github.com/corerzhang) as a base for development.

License
=======

    Copyright (C) 2016 Shota Saito

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
