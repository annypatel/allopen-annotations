# allopen-annotations

[![CircleCI](https://circleci.com/gh/annypatel/allopen-annotations.svg?style=shield)](https://circleci.com/gh/annypatel/allopen-annotations)

## Why?
If you try to mock class in Kotlin you will get following error.

```
org.mockito.exceptions.base.MockitoException:
Cannot mock/spy class com.example.SomeClass
Mockito cannot mock/spy because :
- final class
```
This is because all classes are final by default in Kotlin. To fix this problem you can use one of the following approach.


**Option 1** - Make class and member function `open` explicitly.

**Option 2** - Use interface or abstract class as super type for class that you want to mock.

**Option 3** - Use Mockito's [inline mock maker](https://github.com/mockito/mockito/wiki/What's-new-in-Mockito-2#unmockable). This works well for unit tests running on local JVM, but for for instrumentation tests, it only works on Android API 28 or above. Remember that inline mock maker has to redefine classes at runtime, which is why it is slower.

**Option 4** - Use Kotlin [all-open](https://kotlinlang.org/docs/reference/compiler-plugins.html#all-open-compiler-plugin)  compiler plugin. `All-open` plugin makes class open not only for tests but also for production code as well. To detect misuse of test only open class via [lint](#lint), you can use `allopen-annotations` library with `all-open` plugin.

## How?
Add following dependency to your `build.gradle`.

```groovy
implementation 'com.github.annypatel.allopen-annotations:all-open:x.x.x'
```
If you want class to be opened only for debug build and not for release, then add following dependencies to your `build.gradle`.

```groovy
debugImplementation 'com.github.annypatel.allopen-annotations:all-open:x.x.x'
releaseImplementation 'com.github.annypatel.allopen-annotations:none-open:x.x.x'
```

Configure `all-open` compiler plugin in you project.

```
// project level build.gradle
buildscript {
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-allopen:$kotlin_version"
    }
}

// module level build.gradle
apply plugin: "kotlin-allopen"
```
This library provides just two annotations `Open` and `TestOnlyOpen`. `Open` is a meta annotation, use it with `all-open` compiler plugin. 

```
allOpen {
    annotation("allopen.annotations.Open")
}
```
Use `TestOnlyOpen` annotation on the class that you want to make open for tests.

```
@TestOnlyOpen
class Pet
```

## Lint

* **InheritingTestOnlyOpenType** - Detects type that inherits test only open type.

	```
	java/Tiger.kt:1: Error: This type is open for tests only [InheritingTestOnlyOpenType]
	class Tiger : Pet()
	              ~~~
	1 errors, 0 warnings
	```
* **TestOnlyOpenType** - Detects test only open type which is being inherited.
	
	```
	java/Pet.kt:4: Error: This type is open for tests only, still being inherited from [TestOnlyOpenType]
	class Pet
	      ~~~
	1 errors, 0 warnings
	```
* **NonMetaAnnotation** - Detects usage of non-meta annotation with `all-open` plugin in build script.

	```
	build.gradle:4: Error: Type is not meta annotation [NonMetaAnnotation]
	    annotation("allopen.annotations.TestOnlyOpen")
	    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	1 errors, 0 warnings
	```