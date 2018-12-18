package allopen.lint

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.GradleContext
import com.android.tools.lint.detector.api.GradleContext.Companion.getStringLiteralValue
import com.android.tools.lint.detector.api.GradleScanner
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity

class TestOnlyOpenGradleDetector : Detector(), GradleScanner {

    companion object {

        private val IMPLEMENTATION = Implementation(
            TestOnlyOpenGradleDetector::class.java,
            Scope.GRADLE_SCOPE
        )

        val ISSUE = Issue.create(
            "NonMetaAnnotation",
            "Type is not meta annotation",
            "This type is not meta annotation, use $FQCN_OPEN annotation instead.",
            Category.CORRECTNESS,
            10,
            Severity.ERROR,
            IMPLEMENTATION
        )
    }

    override fun checkDslPropertyAssignment(
        context: GradleContext,
        property: String,
        value: String,
        parent: String,
        parentParent: String?,
        valueCookie: Any,
        statementCookie: Any
    ) {
        if ("allOpen" == parent && "annotation" == property) {
            val annotation = getStringLiteralValue(value)
            if (FQCN_TEST_ONLY_OPEN == annotation) {

                val location = context.getLocation(valueCookie)
                val message = "Type is not meta annotation"
                context.report(ISSUE, valueCookie, location, message)
            }
        }
    }
}