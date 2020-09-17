package com.kotlin.cstmbtn

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import java.lang.String

class CustomButton(context: Context, attrs: AttributeSet?) :
    AppCompatButton(context, attrs) {
    var unPressedColor:Int
    var unPressedBorderColor:Int
    var unPressedTextColor:Int

    var pressedColor:Int
    var pressedBorderColor:Int
    var pressedTextColor:Int

    var borderWidth:Float
    var radius:Float
    var graDraw:GradientDrawable

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.background = resources.getDrawable(R.drawable.toggle_background, null)
        } else {
            this.background = resources.getDrawable(R.drawable.toggle_background)
        }
        val stateListDrawable = this.background as StateListDrawable
        val dcs = stateListDrawable.constantState as DrawableContainer.DrawableContainerState?
        var drawableItems = dcs?.getChildren()
        graDraw = (drawableItems?.get(0)) as GradientDrawable
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomButton)

        unPressedColor = typedArray.getColor(R.styleable.CustomButton_unpressedColor, getResources().getColor(R.color.colorPrimary))
        unPressedBorderColor = typedArray.getColor(R.styleable.CustomButton_unpressedBorderColor, unPressedColor)
        unPressedTextColor = typedArray.getColor(R.styleable.CustomButton_unpressedTextColor, Color.parseColor("#FFFFFF"))

        borderWidth = typedArray.getDimension(R.styleable.CustomButton_borderWidth, 4.0f)
        radius = typedArray.getDimension(R.styleable.CustomButton_radius, 15.0f)

        var strColor = String.format("#%06X", 0xFFFFFF and unPressedColor)
        strColor = strColor.replace("#","#BF")

        pressedColor = typedArray.getColor(R.styleable.CustomButton_pressedColor, Color.parseColor(strColor))
        pressedBorderColor = typedArray.getColor(R.styleable.CustomButton_pressedBorderColor, pressedColor)
        pressedTextColor = typedArray.getColor(R.styleable.CustomButton_pressedTextColor, Color.parseColor("#FFFFFF"))

        this.setTextColor(unPressedTextColor)
        graDraw.setStroke(Math.round(borderWidth), unPressedBorderColor)
        graDraw.setColor(unPressedColor)
        graDraw.cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, resources.displayMetrics)

        typedArray.recycle()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                graDraw.setStroke(Math.round(borderWidth), pressedBorderColor)
                graDraw.setColor(pressedColor)
                graDraw.cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, resources.displayMetrics)
                setTextColor(pressedTextColor)
            }

            MotionEvent.ACTION_UP -> {
                graDraw.setStroke(Math.round(borderWidth), unPressedBorderColor)
                graDraw.setColor(unPressedColor)
                graDraw.cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, resources.displayMetrics)
                setTextColor(unPressedTextColor)
            }
        }
        return super.onTouchEvent(event)
    }
}