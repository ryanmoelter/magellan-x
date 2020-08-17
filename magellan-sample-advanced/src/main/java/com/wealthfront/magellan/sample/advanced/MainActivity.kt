package com.wealthfront.magellan.sample.advanced

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.Menu
import android.view.Window
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.wealthfront.magellan.lifecycle.setContentScreen
import com.wealthfront.magellan.navigation.ActionBarConfigListener
import com.wealthfront.magellan.navigation.DEFAULT_ACTION_BAR_COLOR_RES
import com.wealthfront.magellan.sample.advanced.SampleApplication.Companion.app
import com.wealthfront.magellan.view.ActionBarConfig
import javax.inject.Inject

private const val DEFAULT_ANIM_DURATION_MS = 300L

class MainActivity : AppCompatActivity(), ActionBarConfigListener {

  @Inject lateinit var expedition: Expedition

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    app(this).injector().inject(this)
    setContentScreen(expedition)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu, menu)
    expedition.setMenu(menu)
    return true
  }

  override fun onPrepareOptionsMenu(menu: Menu): Boolean {
    expedition.setMenu(menu)
    return true
  }

  override fun onBackPressed() {
    if (!expedition.backPressed()) {
      super.onBackPressed()
    }
  }

  override fun onNavigate(actionBarConfig: ActionBarConfig) {
    updateStatusBar(actionBarConfig.colorRes())
  }

  private fun updateStatusBar(@ColorRes actionBarColorRes: Int) {
    if (actionBarColorRes == DEFAULT_ACTION_BAR_COLOR_RES) {
      animateStatusBarColor(window, window.statusBarColor, R.color.colorAccent)
    } else {
      val statusBarColor = ContextCompat.getColor(this, actionBarColorRes)
      animateStatusBarColor(window, window.statusBarColor, statusBarColor)
    }
  }

  private fun animateStatusBarColor(window: Window, startColor: Int, endColor: Int) {
    if (startColor == endColor) {
      return
    }
    val statusColorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor)
    statusColorAnimator.duration = DEFAULT_ANIM_DURATION_MS
    statusColorAnimator.addUpdateListener { window.statusBarColor = it.animatedValue as Int }
    statusColorAnimator.start()
  }
}
