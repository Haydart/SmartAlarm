package pl.rmakowiecki.smartalarm

import junit.framework.Assert

abstract class MviViewRobot<VS> {

    protected val renderEvents = mutableListOf<VS>()

    fun assertViewStatesRendered(vararg viewstates: VS) {
        Assert.assertEquals("View states list not equal", viewstates.asList(), renderEvents)
    }

    abstract fun destroyView()
}