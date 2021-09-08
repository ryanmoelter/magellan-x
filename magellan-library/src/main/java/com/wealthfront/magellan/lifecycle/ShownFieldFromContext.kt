package com.wealthfront.magellan.lifecycle

import android.content.Context

public class ShownFieldFromContext<Field>(
  public val fieldSupplier: (Context) -> Field
) : LifecycleAware {

  public var field: Field? = null
    protected set

  override fun show(context: Context) {
    field = fieldSupplier(context)
  }

  override fun hide(context: Context) {
    field = null
  }
}

public fun <Field> LifecycleOwner.shownFieldFromContext(fieldSupplier: (Context) -> Field): LifecycleChild<ShownFieldFromContext<Field>, Field?> =
  attachFieldToLifecycle(ShownFieldFromContext(fieldSupplier), { it.field })
