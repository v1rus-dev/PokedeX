package yegor.cheprasov.pokedex.core.common.mapper

interface Mapper<in Input, out Output> {
    fun map(input: Input): Output
}
