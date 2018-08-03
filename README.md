# CircularImageView
Rounded ImageView with additional border. Works fine with Recycler views.
Compatible with Glide, Picasso and Fresco. Tested with all three of them.
Image zooming and alpha value changes have been tested as well.
Can work with a custom drawable, Picasso Drawable or a Glide Target drawable.

# Usage
```<com.laatonWalaBhoot.CircleImageView
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/image"
    android:layout_width="48dp"
    android:layout_height="48dp"
    android:src="@drawable/image"
    app:border_width="3dp"
    app:border_color="#D3D3D3"/>
```

# Limitations
Cannot add touch inside border.
Still have to add bitmap shader capabilities
adjustViewBounds is not supported because of Scale Type limitations.
Only ScaleType.CENTER_CROP is supported.

