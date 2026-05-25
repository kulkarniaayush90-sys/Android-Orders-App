package com.oceanx.myorders

import android.os.SystemClock
import android.view.View

private const val DEFAULT_DEBOUNCE_MS = 300L

fun View.setDebouncedClickListener(
    debounceMs: Long = DEFAULT_DEBOUNCE_MS,
    onClick: (View) -> Unit
) {
    setOnClickListener { view ->
        if (!view.isEnabled) return@setOnClickListener
        view.isEnabled = false
        view.postDelayed({ view.isEnabled = true }, debounceMs)
        onClick(view)
    }
}

fun isDebouncedTapAllowed(
    lastTapAt: Long,
    debounceMs: Long = DEFAULT_DEBOUNCE_MS
): Boolean {
    return SystemClock.elapsedRealtime() - lastTapAt >= debounceMs
}
