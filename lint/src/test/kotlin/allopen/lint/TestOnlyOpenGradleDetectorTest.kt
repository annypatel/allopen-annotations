package allopen.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class TestOnlyOpenGradleDetectorTest : LintDetectorTest() {

    override fun getDetector() = TestOnlyOpenGradleDetector()
    override fun getIssues() = listOf(
        TestOnlyOpenGradleDetector.ISSUE
    )

    @Test
    fun testErrors() {
        val result = lintFiles(allTestOnlyOpenBuildScript())

        assertThat(
            result,
            equalTo(
                """
                |build.gradle:4: Error: Type is not meta annotation [NonMetaAnnotation]
                |    annotation("allopen.annotations.TestOnlyOpen")
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |1 errors, 0 warnings
                |
                """.trimMargin()
            )
        )
    }

    @Test
    fun testNoWarnings() {
        val result = lintFiles(allOpenBuildScript())

        assertThat(result, equalTo("No warnings."))
    }
}