package com.ryanmoelter.magellanx.core.coroutines

import android.app.Application
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.ryanmoelter.magellanx.core.lifecycle.LifecycleState.Created
import com.ryanmoelter.magellanx.core.lifecycle.LifecycleState.Destroyed
import com.ryanmoelter.magellanx.core.lifecycle.LifecycleState.Resumed
import com.ryanmoelter.magellanx.core.lifecycle.LifecycleState.Shown
import com.ryanmoelter.magellanx.core.lifecycle.transition
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class, InternalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
internal class CreatedLifecycleScopeTest {

  private lateinit var createdScope: CreatedLifecycleScope
  private val context = getApplicationContext<Application>()

  @Before
  fun setUp() {
    createdScope = CreatedLifecycleScope()
    Dispatchers.setMain(Dispatchers.Unconfined)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun cancelBeforeCreated() {
    runTest {

      val async = createdScope.async { delay(5000) }
      async.isCancelled shouldBe true
      async.getCancellationException().message shouldContain "Not created yet"

      createdScope.transition(Destroyed, Resumed(context))

      async.isCancelled shouldBe true

      createdScope.transition(Resumed(context), Created(context))

      async.isCancelled shouldBe true
      async.getCancellationException().message shouldContain "Not created yet"
    }
  }

  @Test
  fun cancelAfterCreated() {
    runTest {
      createdScope.transition(Destroyed, Created(context))

      val async = createdScope.async(Dispatchers.Default) { delay(5000) }
      async.isCancelled shouldBe false

      createdScope.transition(Created(context), Resumed(context))

      async.isCancelled shouldBe false

      createdScope.transition(Resumed(context), Created(context))

      async.isCancelled shouldBe false

      createdScope.transition(Created(context), Destroyed)

      async.isCancelled shouldBe true
      async.getCancellationException().message shouldContain "Destroyed"
    }
  }

  @Test
  fun cancelAfterShown() {
    runTest {
      createdScope.transition(Destroyed, Shown(context))

      val async = createdScope.async(Dispatchers.Default) { delay(5000) }
      async.isCancelled shouldBe false

      createdScope.transition(Shown(context), Resumed(context))

      async.isCancelled shouldBe false

      createdScope.transition(Resumed(context), Created(context))

      async.isCancelled shouldBe false

      createdScope.transition(Created(context), Destroyed)

      async.isCancelled shouldBe true
      async.getCancellationException().message shouldContain "Destroyed"
    }
  }

  @Test
  fun cancelAfterResumed() {
    runTest {
      createdScope.transition(Destroyed, Resumed(context))

      val async = createdScope.async(Dispatchers.Default) { delay(5000) }
      async.isCancelled shouldBe false

      createdScope.transition(Resumed(context), Created(context))

      async.isCancelled shouldBe false

      createdScope.transition(Created(context), Destroyed)
      async.isCancelled shouldBe true
      async.getCancellationException().message shouldContain "Destroyed"
    }
  }
}