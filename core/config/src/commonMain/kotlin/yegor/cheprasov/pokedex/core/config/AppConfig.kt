package yegor.cheprasov.pokedex.core.config

object AppConfig {
    val appName: String
        get() = BuildConfig.APP_NAME

    val applicationId: String
        get() = BuildConfig.APPLICATION_ID

    val versionName: String
        get() = BuildConfig.VERSION_NAME
}
