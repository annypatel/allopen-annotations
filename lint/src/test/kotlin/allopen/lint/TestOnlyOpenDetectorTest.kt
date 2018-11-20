package allopen.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class TestOnlyOpenDetectorTest : LintDetectorTest() {

    override fun getDetector() = TestOnlyOpenDetector()
    override fun getIssues() = listOf(
        TestOnlyOpenDetector.TYPE_USAGE,
        TestOnlyOpenDetector.INHERITANCE_USAGE
    )

    @Test
    fun testNoWarnings() {
        val result = lintFiles(
            testOnlyOpen(),
            pet()
        )

        assertThat(result, equalTo("No warnings."))
    }

    @Test
    fun testErrorsInKotlin() {
        val result = lintFiles(
            testOnlyOpen(),
            pet(),
            tiger()
        )

        assertThat(
            result,
            equalTo(
                """
                |java/Tiger.kt:1: Error: This type is open for tests only [InheritingTestOnlyOpenType]
                |class Tiger : Pet()
                |              ~~~
                |java/Pet.kt:4: Error: This type is open for tests only, still being inherited from [TestOnlyOpenType]
                |class Pet
                |      ~~~
                |2 errors, 0 warnings
                |
                """.trimMargin()
            )
        )
    }

    @Test
    fun testErrorsInJava() {
        val result = lintFiles(
            testOnlyOpen(),
            pet(),
            wolf()
        )

        assertThat(
            result,
            equalTo(
                """
                |java/Wolf.java:1: Error: This type is open for tests only [InheritingTestOnlyOpenType]
                |public class Wolf extends Pet {
                |                          ~~~
                |java/Pet.kt:4: Error: This type is open for tests only, still being inherited from [TestOnlyOpenType]
                |class Pet
                |      ~~~
                |2 errors, 0 warnings
                |
                """.trimMargin()
            )
        )
    }

    @Test
    fun testMultipleErrors() {
        val result = lintFiles(
            testOnlyOpen(),
            pet(),
            tiger(),
            wolf()
        )

        assertThat(
            result,
            equalTo(
                """
                |java/Tiger.kt:1: Error: This type is open for tests only [InheritingTestOnlyOpenType]
                |class Tiger : Pet()
                |              ~~~
                |java/Wolf.java:1: Error: This type is open for tests only [InheritingTestOnlyOpenType]
                |public class Wolf extends Pet {
                |                          ~~~
                |java/Pet.kt:4: Error: This type is open for tests only, still being inherited from [TestOnlyOpenType]
                |class Pet
                |      ~~~
                |3 errors, 0 warnings
                |
                """.trimMargin()
            )
        )
    }
}