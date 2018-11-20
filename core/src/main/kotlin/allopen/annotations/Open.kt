package allopen.annotations

/**
 * all-open meta annotation.
 *
 * Use this annotation in your `build.gradle`  with
 * [all-open compiler plugin](https://kotlinlang.org/docs/reference/compiler-plugins.html#all-open-compiler-plugin)
 * following way.
 *
 * ```
 * allOpen {
 *     annotation("allopen.annotations.Open")
 * }
 * ```
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Open