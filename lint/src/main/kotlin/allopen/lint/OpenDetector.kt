package allopen.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.UElement

/**
 * Detects usage of [allopen.annotations.Open] meta annotation.
 */
class OpenDetector : Detector(), SourceCodeScanner {

    companion object {

        private val IMPLEMENTATION = Implementation(
            OpenDetector::class.java,
            Scope.JAVA_FILE_SCOPE
        )

        val ISSUE = Issue.create(
            "OpenType",
            "Type annotated with @$OPEN",
            "Type should not be annotated with @$OPEN annotation, either use @$TEST_ONLY_OPEN or " +
                "explicitly mark type as open.",
            Category.CORRECTNESS,
            10,
            Severity.ERROR,
            IMPLEMENTATION
        )
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(UAnnotation::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitAnnotation(node: UAnnotation) = visitAnnotationImpl(context, node)
        }
    }

    private fun visitAnnotationImpl(context: JavaContext, node: UAnnotation) {
        if (FQCN_OPEN == node.qualifiedName) {
            val location = context.getLocation(node)
            val message = "Using @$OPEN meta annotation"

            context.report(ISSUE, node, location, message)
        }
    }
}
