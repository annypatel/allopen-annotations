package allopen.annotations

/**
 * Denotes that the class is open so that it can be inherited or mocked in tests.
 */
@Open
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TestOnlyOpen