package com.androidfactory.fakestore.home.explore.epoxy

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import coil.load
import com.androidfactory.fakestore.R
import com.androidfactory.fakestore.databinding.EpoxyModelCirlceImageBinding
import com.androidfactory.fakestore.epoxy.ViewBindingKotlinModel

data class CircleImageEpoxyModel(
    val imageUrl: String,
    val isSelected: Boolean,
    val onClick: () -> Unit
): ViewBindingKotlinModel<EpoxyModelCirlceImageBinding>(R.layout.epoxy_model_cirlce_image) {

    override fun EpoxyModelCirlceImageBinding.bind() {
        imageView.load(imageUrl)
        root.setOnClickListener { onClick() }

        val strokeColor = if (isSelected) {
            ContextCompat.getColor(root.context, R.color.purple_700)
        } else {
            ContextCompat.getColor(root.context, R.color.purple_100)
        }
        imageView.strokeColor = ColorStateList.valueOf(strokeColor)

        if (isSelected) {
            AnimatorSet().apply {
                val animator = ValueAnimator.ofFloat(1f, 0.85f, 1.05f, 1f).apply {
                    addUpdateListener {
                        imageView.scaleX = it.animatedValue as Float
                        imageView.scaleY = it.animatedValue as Float
                    }
                    duration = 250L
                    start()
                }
                play(animator)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as CircleImageEpoxyModel

        if (imageUrl != other.imageUrl) return false
        if (isSelected != other.isSelected) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + isSelected.hashCode()
        return result
    }
}
