package allopen.lint

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItem
import org.junit.Assert.assertThat
import org.junit.Test

class AllOpenIssueRegistryTest {

    @Test
    fun apiReturnsCurrentApi() {
        val registry = AllOpenIssueRegistry()

        val api = registry.api

        assertThat(api, equalTo(3))
    }

    @Test
    fun issuesContainsAllIssues() {
        val registry = AllOpenIssueRegistry()

        val issues = registry.issues

        assertThat(issues, hasItem(TestOnlyOpenDetector.TYPE_USAGE))
        assertThat(issues, hasItem(TestOnlyOpenDetector.INHERITANCE_USAGE))
        assertThat(issues, hasItem(TestOnlyOpenGradleDetector.ISSUE))
    }
}