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

private const val CIRCULAR_REVEAL_DURATION = 500
private const val CIRCULAR_REVEAL_RADIUS = 1024
private const val CIRCULAR_REVEAL_DELAY = 1250
private const val FAILURE_ICON_DISPLAYING_TIME = 800
private const val SLIDE_DURATION = 300
private const val SLIDE_DELAY = 300
private const val SLIDE_IN_Y_DELTA = 500
private const val SLIDE_OUT_Y_DELTA = -500
private const val ERROR_DISPLAY_DURATION = 1500
private const val DEFAULT_INACTIVE_ALPHA = .75f
private const val START_NO_DELAY = 0

class ActionButton @JvmOverloads constructor(
        val context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var successColor: Int = 0
    private var textColor: Int = 0
    private var failureColor: Int = 0
    private var defaultColor: Int = 0
    private var inactiveAlpha: Float = 0.toFloat()
    private var buttonActionDescription: String? = null
    private var iconSuccess: Drawable? = null
    private var iconFailure: Drawable? = null
    private var layoutInflater: LayoutInflater? = null
    private var isEnabled: Boolean = false

    private lateinit var successFrameLayout: FrameLayout
    private lateinit var failureFrameLayout: FrameLayout
    private lateinit var buttonActionDescriptionTextView: TextView
    private lateinit var buttonErrorDescTextView: TextView
    private lateinit var progressView: AVLoadingIndicatorView
    private lateinit var successImageView: ImageView
    private lateinit var failureImageView: ImageView

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
        buttonActionDescription = typedAttrArray.getString(R.styleable.ActionButton_text)
        typedAttrArray.recycle()
    }

    private fun bindViews() {
        successFrameLayout = findViewById((R.id.frame_layout_button_success))
        failureFrameLayout = findViewById((R.id.frame_layout_button_failure))
        buttonActionDescriptionTextView = findViewById((R.id.text_view_button_action_desc))
        buttonErrorDescTextView = findViewById((R.id.text_view_error_desc))
        progressView = findViewById((R.id.progress_indicator))
        successImageView = findViewById((R.id.icon_success))
        failureImageView = findViewById((R.id.icon_failure))
    }

    private fun initAfterViewBinding() {
        setEnabled(false)
        setWidgetBackground(this)
        (background as GradientDrawable).setColor(defaultColor)
        buttonActionDescriptionTextView.text = buttonActionDescription
        buttonActionDescriptionTextView.setTextColor(textColor)
        successFrameLayout.background.setColorFilter(successColor, PorterDuff.Mode.SRC_ATOP)
        failureFrameLayout.background.setColorFilter(failureColor, PorterDuff.Mode.SRC_ATOP)
        successImageView.setImageDrawable(iconSuccess)
        iconSuccess!!.setColorFilter(textColor, PorterDuff.Mode.SRC_IN)
        failureImageView.setImageDrawable(iconFailure)
        iconFailure!!.setColorFilter(textColor, PorterDuff.Mode.SRC_IN)
        progressView.setIndicatorColor(textColor)
    }

    private fun setWidgetBackground(viewGroup: ViewGroup) = viewGroup
            .setBackgroundResource(R.drawable.pill_button)

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
            progressView!!.visibility = View.INVISIBLE
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
            performSlideOutAnimation(progressView, CIRCULAR_REVEAL_DELAY, { progressView!!.visibility = View.INVISIBLE })
        }
        showErrorMessage(message, FAILURE_ICON_DISPLAYING_TIME)
    }

    private fun setLayoutsBackground() {
        setWidgetBackground(failureFrameLayout)
        setWidgetBackground(successFrameLayout)
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
        val backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.pill_button)
        backgroundDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        background = backgroundDrawable
        setEnabled(isEnabled)
    }

    private fun performSlideInAnimation(view: View?, startOffset: Int) {
        view!!.visibility = View.VISIBLE

        val slideInAnimation = TranslateAnimation(0f, 0f, SLIDE_IN_Y_DELTA.toFloat(), 0f).apply {
            slideInAnimation.duration = SLIDE_DURATION.toLong()
            slideInAnimation.interpolator = LinearOutSlowInInterpolator()
            slideInAnimation.startOffset = startOffset.toLong()
        }
        view.startAnimation(slideInAnimation)
    }

    private fun performSlideOutAnimation(view: View?, startOffset: Int, onEndAction: () -> Unit) {
        val slideOutAnimation = TranslateAnimation(0f, 0f, 0f, SLIDE_OUT_Y_DELTA.toFloat())
        slideOutAnimation.duration = SLIDE_DURATION.toLong()
        slideOutAnimation.startOffset = startOffset.toLong()
        slideOutAnimation.interpolator = FastOutLinearInInterpolator()
        slideOutAnimation.setAnimationListener(object : AnimationListenerAdapter() {
            fun onAnimationEnd(animation: Animation) {
                onEndAction()
            }
        })
        view!!.startAnimation(slideOutAnimation)
    }

    private fun performScaleUpAndFadeOutAnimation(view: View?, startOffset: Int, onEndAction: () -> Unit) {
        val scaleUpFadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_up_fade_out)
        scaleUpFadeOutAnimation.startOffset = startOffset.toLong()
        scaleUpFadeOutAnimation.setAnimationListener(object : AnimationListenerAdapter() {
            fun onAnimationEnd(animation: Animation) {
                onEndAction()
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

    private inner open class AnimationListenerAdapter : Animation.AnimationListener {

        override fun onAnimationStart(animation: Animation) = Unit

        override fun onAnimationEnd(animation: Animation) = Unit

        override fun onAnimationRepeat(animation: Animation) = Unit
    }
}
