package com.ryanmoelter.magellanx.core.coroutines

import com.ryanmoelter.magellanx.core.lifecycle.LifecycleAware
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

public class StartedLifecycleScope : LifecycleAware, CoroutineScope {
  private var job = SupervisorJob().apply { cancel(CancellationException("Not started yet")) }
    set(value) {
      field = value
      coroutineContext = value + Dispatchers.Main
    }

  override var coroutineContext: CoroutineContext = job + Dispatchers.Main
    private set

  override fun start() {
    job = SupervisorJob()
  }

  override fun stop() {
    job.cancel(CancellationException("Stopped"))
  }
}
