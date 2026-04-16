package yegor.cheprasov.pockedex

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform