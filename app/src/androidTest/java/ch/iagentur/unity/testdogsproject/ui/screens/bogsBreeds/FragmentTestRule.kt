package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import androidx.fragment.app.Fragment
import androidx.test.rule.ActivityTestRule
import ch.iagentur.unity.testdogsproject.ui.screens.main.MainActivity
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import kotlin.jvm.Throws


class FragmentTestRule<A : MainActivity?, F : Fragment?>(
    activityClass: Class<A>, fragment: F
) : TestRule {

    val activityRule: ActivityTestRule<A> = ActivityTestRule(activityClass)
    val ruleChain: RuleChain

    @Throws(Throwable::class)
    fun runOnUiThread(runnable: Runnable?) {
        activityRule.runOnUiThread(runnable)
    }

    val activity: A
        get() = activityRule.activity

    override fun apply(statement: Statement?, description: Description?): Statement {
        return ruleChain.apply(statement, description)
    }

    init {
        ruleChain = RuleChain
            .outerRule(activityRule)
    }
}