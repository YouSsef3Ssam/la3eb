object TestDependencies {
    /** Unit */
    const val junit = "junit:junit:${Versions.jUnit}"
    const val mockitoFramework = "org.mockito:mockito-core:${Versions.mockitoFramework}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val coreTest = "androidx.arch.core:core-testing:${Versions.coreTest}"

    /**
     * UI
     */
    const val junitTest = "androidx.test.ext:junit:${Versions.jUnitTest}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}