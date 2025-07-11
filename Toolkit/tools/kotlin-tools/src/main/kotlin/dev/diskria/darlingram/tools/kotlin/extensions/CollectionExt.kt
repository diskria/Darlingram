package dev.diskria.darlingram.tools.kotlin.extensions

fun <E> MutableCollection<E>.addAll(vararg elements: E) {
    addAll(elements)
}

fun <T> List<T>.takeIfExceeds(limit: Int): List<T>? =
    if (this.size > limit) this.take(this.size - limit)
    else null

fun <T> MutableList<T>.modifyElement(index: Int, transform: (T) -> T) {
    this[index] = transform(this[index])
}

inline fun <E, T : Iterable<E>> T?.ifNullOrEmpty(
    crossinline fallback: () -> T
): T =
    if (this == null || !this.iterator().hasNext()) fallback()
    else this

inline fun <E> Array<E>?.ifNullOrEmpty(
    crossinline fallback: () -> Array<E>
): Array<E> =
    if (this == null || this.isEmpty()) fallback()
    else this

inline fun <K, V> Map<K, V>?.ifNullOrEmpty(
    crossinline fallback: () -> Map<K, V>
): Map<K, V> =
    if (this == null || this.isEmpty()) fallback()
    else this
