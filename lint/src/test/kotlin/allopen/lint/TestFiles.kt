package allopen.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.TestFile
import com.android.tools.lint.checks.infrastructure.LintDetectorTest.java
import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin

fun testOnlyOpen(): TestFile = kotlin(
    "java/allopen/annotations/TestOnlyOpen.kt",
    """
    |package allopen.annotations
    |
    |@Open
    |@Target(AnnotationTarget.CLASS)
    |@Retention(AnnotationRetention.RUNTIME)
    |annotation class TestOnlyOpen
    """.trimMargin()
)

fun pet(): TestFile = kotlin(
    "java/Pet.kt",
    """
    |import allopen.annotations.TestOnlyOpen
    |
    |@TestOnlyOpen
    |class Pet
    """.trimMargin()
)

fun tiger(): TestFile = kotlin(
    "java/Tiger.kt",
    """
    |class Tiger : Pet()
    """.trimMargin()
)

fun wolf(): TestFile = java(
    "java/Wolf.java",
    """
    |public class Wolf extends Pet {
    |}
    """.trimMargin()
)