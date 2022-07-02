package com.example.dictionary

import android.animation.*
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView

object Fn {
    fun open_appInfo(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.setData(Uri.parse("package:" + context.packageName))
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            context.startActivity(intent)
        }
    }

    fun show_dialog(context: Context, title: String, msg: String, is_cancelable: Boolean = true){
        try {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(msg)
            builder.setCancelable(is_cancelable)
            builder.setPositiveButton("Close", null)
            builder.show()
            //dialog.findViewById<TextView>(android.R.id.title).gravity = Gravity.CENTER
            //dialog.findViewById<TextView>(android.R.id.message).gravity = Gravity.CENTER
        }
        catch (e: Exception){
            Toast.makeText(context, title + "\n" + msg, Toast.LENGTH_LONG).show()
        }
    }

    fun about_message(context: Context) {
        show_dialog(
            context,
            context.getString(R.string.app_name),
            "Version: " + BuildConfig.VERSION_NAME
        )
    }

    //fun getTextFromArrayList(arr: ArrayList<String>)
}

fun Activity.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun View.animate_fader() {
    val animator = ObjectAnimator.ofFloat(this, View.ALPHA, 0f)
    animator.repeatCount = 1
    animator.repeatMode = ObjectAnimator.REVERSE
    animator.start()
}

fun View.animate_rotater(duration: Long = 1000) {
    val animator = ObjectAnimator.ofFloat(this, View.ROTATION, -360F, 0F)
    animator.duration = duration
    animator.start()
}

fun View.animate_translater(repeateCount: Int = 1) {
    val animator = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, 200F)
    animator.repeatMode = ObjectAnimator.REVERSE
    animator.repeatCount = repeateCount
    animator.start()
}

fun View.animate_show(isVisible: Boolean, duration: Long = 500L) {
    if (!isVisible) {
        this.animate().translationY(height.toFloat()).setDuration(25L)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    visibility = View.GONE
                }
            })
    } else {
        this.animate().translationY(0.toFloat()).setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    visibility = View.VISIBLE
                }
            })
    }
}

fun View.animate_toggle(duration: Long = 500L) {
    this.animate_show(this.visibility != View.VISIBLE, duration)
}

fun View.animate_scaler() {
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0F)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0F)
    val animator = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
    animator.repeatCount = 1
    animator.repeatMode = ObjectAnimator.REVERSE
    animator.start()
}

@SuppressLint("ObjectAnimatorBinding")
fun View.animate_colorizer() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        var startColor = Color.TRANSPARENT
        val background: Drawable = this.rootView.getBackground()
        if (background is ColorDrawable)
            startColor = background.color

        val animator = ObjectAnimator.ofArgb(this.rootView, "backgroundColor", startColor, Color.RED)
        animator.setDuration(500)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }
}

fun View.animate_shower(drawableResourceId: Int) {
    val container = this.parent as ViewGroup
    val containerH = container.height
    var starW: Float = this.width.toFloat()
    var starH: Float = this.height.toFloat()

    val newStar = AppCompatImageView(this.context)
    newStar.setImageResource(drawableResourceId)
    newStar.layoutParams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT)
    container.addView(newStar)

    newStar.scaleX = Math.random().toFloat() * 1.5f + .1f
    newStar.scaleY = newStar.scaleX
    starW *= newStar.scaleX
    starH *= newStar.scaleY

    val mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y,
        -starH, containerH + starH)
    mover.interpolator = AccelerateInterpolator(1f)
    val rotator = ObjectAnimator.ofFloat(newStar, View.ROTATION,
        (Math.random() * 1080).toFloat())
    rotator.interpolator = LinearInterpolator()

    val set = AnimatorSet()
    set.playTogether(mover, rotator)
    set.duration = (Math.random() * 1500 + 500).toLong()

    set.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            container.removeView(newStar)
        }
    })
    set.start()
}
