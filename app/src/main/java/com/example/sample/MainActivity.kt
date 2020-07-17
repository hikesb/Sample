package com.example.sample

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val preferences = getPreferences(Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val textView = findViewById<TextView>(R.id.textView)
        val delayTimeSec = 1000L
        disposable = Observable.interval(delayTimeSec, TimeUnit.MILLISECONDS).
        observeOn(AndroidSchedulers.mainThread()).
        subscribe({
            val value = preferences.getInt("value", 0)
            textView.text = it.toString()
            editor.putInt("value", value + 1)
            editor.apply()
        } ) { obj: Throwable -> obj.printStackTrace() }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}