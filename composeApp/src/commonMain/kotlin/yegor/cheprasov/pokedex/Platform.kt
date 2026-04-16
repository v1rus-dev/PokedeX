package yegor.cheprasov.pokedex

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform