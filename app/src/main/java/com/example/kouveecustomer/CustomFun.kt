package com.example.kouveecustomer

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.createBalloon
import java.text.NumberFormat
import java.util.*

object CustomFun {

    fun successSnackBar(viewInput: View, baseContext: Context, textInput: String){
        val snackBar = Snackbar.make(viewInput,textInput, Snackbar.LENGTH_INDEFINITE)
        snackBar.setActionTextColor(
            ContextCompat.getColor(baseContext, R.color.colorGrey)
        )
        snackBar.setAction("Close"){
            snackBar.dismiss()
        }
        val view = snackBar.view
        view.setBackgroundResource(R.drawable.snack_bar_success)
        val textView = view.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(baseContext, android.R.color.white))
        setTextView(textView)
        snackBar.show()
    }

    fun failedSnackBar(input: View, baseContext: Context, textInput: String){
        val snackBar = Snackbar.make(input, textInput, Snackbar.LENGTH_INDEFINITE)
        snackBar.setActionTextColor(
            ContextCompat.getColor(baseContext, R.color.colorGrey)
        )
        snackBar.setAction("Close"){
            snackBar.dismiss()
        }
        val view = snackBar.view
        view.setBackgroundResource(R.drawable.snack_bar_failed)
        val textView = view.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(baseContext, android.R.color.white))
        setTextView(textView)
        snackBar.show()
    }

    fun warningSnackBar(input: View, baseContext: Context, textInput: String){
        val snackBar = Snackbar.make(input, textInput, Snackbar.LENGTH_INDEFINITE)
        snackBar.setActionTextColor(
            ContextCompat.getColor(baseContext, android.R.color.black)
        )
        snackBar.setAction("Ok"){
            snackBar.dismiss()
        }
        val view = snackBar.view
        view.setBackgroundResource(R.drawable.snack_bar_warning)
        val textView = view.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(baseContext, android.R.color.black))
        setTextView(textView)
        snackBar.show()
    }

    fun welcomeSnackBar(input: View, baseContext: Context, textInput: String){
        val snackBar = Snackbar.make(input, textInput, Snackbar.LENGTH_LONG)
        snackBar.setActionTextColor(
            ContextCompat.getColor(baseContext, android.R.color.white)
        )
        snackBar.setAction("Close"){
            snackBar.dismiss()
        }
        val view = snackBar.view
        view.setBackgroundResource(R.drawable.snack_bar_welcome)
        val textView = view.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(baseContext, android.R.color.white))
        setTextView(textView)
        snackBar.show()
    }

    private fun setTextView(text: TextView){
        text.maxLines = 1
        text.setTypeface(null, Typeface.ITALIC)
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
    }

    //CONVERTER
    fun changeToRp(number: Double): String{
        val id =  Locale("in", "ID")
        val formatter = NumberFormat.getCurrencyInstance(id)
        return formatter.format(number).toString()+",-"
    }

    //SWIPE LOADING
    fun setSwipe(swipe: SwipeRefreshLayout){
        swipe.setColorSchemeColors(Color.WHITE)
        swipe.setProgressBackgroundColorSchemeResource(R.color.colorAccent)
    }

    fun createToolTips(context: Context, type: String): Balloon {
        return createBalloon(context) {
            setArrowSize(5)
            setWidthRatio(0.5f)
            setHeight(40)
            setCornerRadius(4f)
            setAlpha(0.9f)
            setTextTypeface(Typeface.BOLD)
            setTextColorResource(android.R.color.white)
            setBackgroundColorResource(R.color.colorGreyDark)
            setBalloonAnimation(BalloonAnimation.CIRCULAR)
            setAutoDismissDuration(3000L)
            when (type) {
                "IG" -> {
                    setArrowPosition(0.25f)
                    setText("@kouveepetshop")
                }
                "LN" -> {
                    setArrowPosition(0.5f)
                    setText("@kouveepetshop")
                }
                "WA" -> {
                    setArrowPosition(0.5f)
                    setText("+62 812 3456 7890")
                }
                "FB" -> {
                    setArrowPosition(0.5f)
                    setText("Kouvee Pet Shop")
                }

            }
            setLifecycleOwner(lifecycleOwner)
        }
    }

}