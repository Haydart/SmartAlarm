package pl.rmakowiecki.smartalarm.ui.screens.customView

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.wang.avi.AVLoadingIndicatorView
import pl.rmakowiecki.smartalarm.R

class ActionButton @JvmOverloads constructor(
        val context: Context, val attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var centerX: Int = 0
    private var centerY: Int = 0
    private var successColor: Int = 0
    private var textColor: Int = 0
    private var failureColor: Int = 0
    private var defaultColor: Int = 0
    private var inactiveAlpha: Float = 0.toFloat()
    private var buttonActionDescription: String? = null
    private var iconSuccess: Drawable? = null
    private var iconFailure: Drawable? = null
    private var layoutInflater: LayoutInflater? = null
    private var isEnabled = false
    private var shape = ButtonShape.PILL

    @BindView(R.id.frame_layout_button_success) internal var successFrameLayout: FrameLayout? = null
    @BindView(R.id.frame_layout_button_failure) internal var failureFrameLayout: FrameLayout? = null
    @BindView(R.id.text_view_button_action_desc) internal var buttonActionDescriptionTextView: TextView? = null
    @BindView(R.id.text_view_error_desc) internal var buttonErrorDescTextView: TextView? = null
    @BindView(R.id.progress_indicator) internal var progressView: AVLoadingIndicatorView? = null
    @BindView(R.id.icon_success) internal var successImageView: ImageView? = null
    @BindView(R.id.icon_failure) internal var failureImageView: ImageView? = null

    private enum class ButtonShape {
        PILL,
        RECTANGLE
    }

    init {
        initView()
        setStyledAttributes(context, attrs)
        bindViews()
        initAfterViewBinding()
    }

    private fun initView() {
        this.layoutInflater = LayoutInflater.from(context)
        layoutInflater!!.inflate(R.layout.action_button, this, true)
    }

    private fun setStyledAttributes(context: Context, attrs: AttributeSet?) {
        val typedAttrArray = context.obtainStyledAttributes(attrs, R.styleable.ActionButton)
        textColor = typedAttrArray.getColor(R.styleable.ActionButton_text_color, Color.BLACK)
        successColor = typedAttrArray.getColor(R.styleable.ActionButton_success_color, Color.TRANSPARENT)
        defaultColor = typedAttrArray.getColor(R.styleable.ActionButton_default_color, Color.TRANSPARENT)
        failureColor = typedAttrArray.getColor(R.styleable.ActionButton_failure_color, Color.TRANSPARENT)
        iconSuccess = typedAttrArray.getDrawable(R.styleable.ActionButton_success_icon)
        iconFailure = typedAttrArray.getDrawable(R.styleable.ActionButton_failure_icon)
        inactiveAlpha = typedAttrArray.getFloat(R.styleable.ActionButton_inactive_button_alpha, DEFAULT_INACTIVE_ALPHA)
        shape = if (typedAttrArray.getInt(R.styleable.ActionButton_button_shape, 1) == 0) ButtonShape.PILL else ButtonShape.RECTANGLE
        buttonActionDescription = typedAttrArray.getString(R.styleable.ActionButton_text)
        typedAttrArray.recycle()
    }

    private fun bindViews() {
    }

    private fun initAfterViewBinding() {
        setEnabled(false)
        setBackgroundDependingOnButtonShape(this)
        (background as GradientDrawable).setColor(defaultColor)
        buttonActionDescriptionTextView!!.text = buttonActionDescription
        buttonActionDescriptionTextView!!.setTextColor(textColor)
        successFrameLayout!!.background.setColorFilter(successColor, PorterDuff.Mode.SRC_ATOP)
        failureFrameLayout!!.background.setColorFilter(failureColor, PorterDuff.Mode.SRC_ATOP)
        successImageView!!.setImageDrawable(iconSuccess)
        iconSuccess!!.setColorFilter(textColor, PorterDuff.Mode.SRC_IN)
        failureImageView!!.setImageDrawable(iconFailure)
        iconFailure!!.setColorFilter(textColor, PorterDuff.Mode.SRC_IN)
        progressView!!.setIndicatorColor(textColor)
    }

    private fun setBackgroundDependingOnButtonShape(viewGroup: ViewGroup?) {
        viewGroup!!.setBackgroundResource(if (shape == ButtonShape.PILL)
            R.drawable.pill_button
        else
            R.drawable.rectangle_action_button
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        calculateButtonCenter()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        isEnabled = enabled
        isClickable = enabled
        alpha = if (isEnabled) 1f else inactiveAlpha
    }

    fun showSuccess() {
        performSlideOutAnimation(progressView, START_NO_DELAY, {
            progressView!!.setVisibility(View.INVISIBLE)
            successFrameLayout!!.visibility = View.VISIBLE
        })
        performSlideInAnimation(successImageView, SLIDE_DELAY)
        performScaleUpAndFadeOutAnimation(successImageView, SLIDE_DELAY + SLIDE_DURATION, { successImageView!!.visibility = View.INVISIBLE })
    }

    fun showFailure(message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setLayoutsBackground()
            createAndStartCircularRevealAnimation(failureFrameLayout, CIRCULAR_REVEAL_DELAY)
        } else {
            performFadeInAnimation(failureFrameLayout)
            performSlideOutAnimation(progressView, CIRCULAR_REVEAL_DELAY, { progressView!!.setVisibility(View.INVISIBLE) })
        }
        showErrorMessage(message, FAILURE_ICON_DISPLAYING_TIME)
    }

    private fun setLayoutsBackground() {
        setBackgroundDependingOnButtonShape(failureFrameLayout)
        setBackgroundDependingOnButtonShape(successFrameLayout)
        failureFrameLayout!!.background.setColorFilter(failureColor, PorterDuff.Mode.SRC_ATOP)
    }

    fun showProcessing() {
        isClickable = false
        performSlideOutAnimation(buttonActionDescriptionTextView, 0, { buttonActionDescriptionTextView!!.visibility = View.INVISIBLE })
        performSlideInAnimation(progressView, SLIDE_DELAY)
    }

    fun setTextColor(color: Int) {
        buttonActionDescriptionTextView!!.setTextColor(color)
    }

    fun setLoaderColor(color: Int) {
        progressView!!.setIndicatorColor(color)
    }

    fun setColor(color: Int) {
        val backgroundDrawable = ContextCompat.getDrawable(context!!, R.drawable.pill_button)
        backgroundDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        background = backgroundDrawable
        setEnabled(isEnabled)
    }

    private fun performSlideInAnimation(view: View?, startOffset: Int) {
        view!!.visibility = View.VISIBLE

        val slideInAnimation = TranslateAnimation(0f, 0f, SLIDE_IN_Y_DELTA.toFloat(), 0f)
        slideInAnimation.duration = SLIDE_DURATION.toLong()
        slideInAnimation.interpolator = LinearOutSlowInInterpolator()
        slideInAnimation.startOffset = startOffset.toLong()
        view.startAnimation(slideInAnimation)
    }

    private fun performSlideOutAnimation(view: View?, startOffset: Int, onEndAction: Runnable) {
        val slideOutAnimation = TranslateAnimation(0f, 0f, 0f, SLIDE_OUT_Y_DELTA.toFloat())
        slideOutAnimation.duration = SLIDE_DURATION.toLong()
        slideOutAnimation.startOffset = startOffset.toLong()
        slideOutAnimation.interpolator = FastOutLinearInInterpolator()
        slideOutAnimation.setAnimationListener(object : AnimationListenerAdapter() {
            fun onAnimationEnd(animation: Animation) {
                onEndAction.run()
            }
        })
        view!!.startAnimation(slideOutAnimation)
    }

    private fun performScaleUpAndFadeOutAnimation(view: View?, startOffset: Int, onEndAction: Runnable) {
        val scaleUpFadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_up_fade_out)
        scaleUpFadeOutAnimation.startOffset = startOffset.toLong()
        scaleUpFadeOutAnimation.setAnimationListener(object : AnimationListenerAdapter() {
            fun onAnimationEnd(animation: Animation) {
                onEndAction.run()
            }
        })
        view!!.startAnimation(scaleUpFadeOutAnimation)
    }

    private fun performFadeInAnimation(view: View?) {
        view!!.visibility = View.VISIBLE
        val fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        view.startAnimation(fadeInAnimation)
    }

    private fun performFadeOutAnimation(view: View) {
        val fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        fadeOutAnimation.setAnimationListener(object : AnimationListenerAdapter() {
            fun onAnimationEnd(animation: Animation) {
                view.visibility = View.INVISIBLE
            }
        })
        view.startAnimation(fadeOutAnimation)
    }

    private fun resetButtonState(buttonResetTime: Int) {
        postDelayed({
            resetViewsVisibilityBeforeAnimation()
            this@ActionButton.performResetButtonState()
        }, buttonResetTime.toLong())
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createAndStartCircularRevealAnimation(view: View?, startOffset: Int) {
        val circularRevealAnimation = ViewAnimationUtils.createCircularReveal(view,
                centerX, centerY, 0f, CIRCULAR_REVEAL_RADIUS.toFloat())
                .setDuration(CIRCULAR_REVEAL_DURATION.toLong())
        circularRevealAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                view!!.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator) {
                resetButtonState(ERROR_DISPLAY_DURATION)
            }
        })
        circularRevealAnimation.startDelay = startOffset.toLong()
        circularRevealAnimation.start()
    }

    private fun performResetButtonState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createAndStartCircularShrinkAnimation()
        } else {
            resetButtonStatePreLollipop()
        }
    }

    private fun resetButtonStatePreLollipop() {
        postDelayed({
            resetViewsVisibilityAfterAnimation()
            setEnabled(true)
        }, CIRCULAR_REVEAL_DELAY.toLong())
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createAndStartCircularShrinkAnimation() {
        val circularShrinkAnimation = ViewAnimationUtils.createCircularReveal(
                failureFrameLayout,
                centerX, centerY, CIRCULAR_REVEAL_RADIUS.toFloat(), 0f)
        circularShrinkAnimation.duration = CIRCULAR_REVEAL_DURATION.toLong()
        circularShrinkAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                resetViewsVisibilityAfterAnimation()
                setEnabled(true)
            }
        })
        circularShrinkAnimation.start()
    }

    private fun resetViewsVisibilityAfterAnimation() {
        failureFrameLayout!!.visibility = View.INVISIBLE
        buttonErrorDescTextView!!.visibility = View.INVISIBLE
        failureImageView!!.visibility = View.VISIBLE
    }

    private fun resetViewsVisibilityBeforeAnimation() {
        progressView!!.setVisibility(View.INVISIBLE)
        buttonActionDescriptionTextView!!.visibility = View.VISIBLE
    }

    private fun showErrorMessage(message: String, failureIconDisplayingTime: Int) {
        buttonErrorDescTextView!!.text = message
        performSlideInAnimation(buttonErrorDescTextView, failureIconDisplayingTime)
        performSlideOutAnimation(failureImageView, failureIconDisplayingTime, { failureImageView!!.visibility = View.INVISIBLE })
    }

    private fun calculateButtonCenter() {
        centerX = width / 2
        centerY = height / 2
    }

    companion object {
        private val CIRCULAR_REVEAL_DURATION = 500
        private val CIRCULAR_REVEAL_RADIUS = 1024
        private val CIRCULAR_REVEAL_DELAY = 1250
        private val FAILURE_ICON_DISPLAYING_TIME = 800
        private val SLIDE_DURATION = 300
        private val SLIDE_DELAY = 300
        private val SLIDE_IN_Y_DELTA = 500
        private val SLIDE_OUT_Y_DELTA = -500
        private val ERROR_DISPLAY_DURATION = 1500
        private val DEFAULT_INACTIVE_ALPHA = .75f
        val START_NO_DELAY = 0
    }
}
