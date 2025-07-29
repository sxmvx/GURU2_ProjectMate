package com.example.test1.ui_team

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.CalendarDay

class ScheduleDotDecorator(
    private val dates: Collection<CalendarDay>,
    private val color: Int
) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.setBackgroundDrawable(DotDrawable(color))
    }
}
