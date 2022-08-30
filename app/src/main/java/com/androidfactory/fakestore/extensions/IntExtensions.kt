package com.androidfactory.fakestore.extensions

import android.content.res.Resources
import androidx.annotation.Dimension

@Dimension(unit = Dimension.DP)
fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

@Dimension(unit = Dimension.PX)
fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()