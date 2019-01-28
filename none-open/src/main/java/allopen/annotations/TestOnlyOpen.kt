package allopen.annotations

/**
 * Alternate annotation for release builds. This annotation won't make classes open for tests.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class TestOnlyOpen