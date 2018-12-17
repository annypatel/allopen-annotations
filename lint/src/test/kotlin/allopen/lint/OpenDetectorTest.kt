package allopen.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class OpenDetectorTest : LintDetectorTest() {

    override fun getDetector() = OpenDetector()
    override fun getIssues() = listOf(OpenDetector.ISSUE)

    @Test
    fun testErrors() {
        val result = lintFiles(
            open(),
            lion()
        )

        assertThat(
            result,
            equalTo(
                """
                |java/Lion.kt:3: Error: Using @Open meta annotation [OpenType]
                |@Open
                |~~~~~
                |1 errors, 0 warnings
                |
                """.trimMargin()
            )
        )
    }

    @Test
    fun testNoWarnings() {
        val result = lintFiles(
            open(),
            leopard()
        )

        assertThat(result, equalTo("No warnings."))
    }
}