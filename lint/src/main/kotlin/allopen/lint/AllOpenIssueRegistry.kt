package allopen.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue

/**
 * Lint [IssueRegistry] for allopen-annotations related lint checks.
 */
class AllOpenIssueRegistry : IssueRegistry() {

    override val api: Int
        get() = 3

    override val issues: List<Issue>
        get() = listOf(
            TestOnlyOpenDetector.TYPE_USAGE,
            TestOnlyOpenDetector.INHERITANCE_USAGE,
            TestOnlyOpenGradleDetector.ISSUE,
            OpenDetector.ISSUE
        )
}