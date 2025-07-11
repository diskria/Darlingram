package dev.diskria.darlingram.toolkit.utils.kotlin.extensions

fun <E> MutableCollection<E>.addAll(vararg elements: E) {
    addAll(elements)
}

fun <T> List<T>.takeIfExceeds(limit: Int): List<T>? =
    if (this.size > limit) this.take(this.size - limit)
    else null

fun <T> MutableList<T>.modifyFirst(transformator: (T) -> T) =
    modifyElementAt(0, transformator)

fun <T> MutableList<T>.modifyLast(transformator: (T) -> T) =
    modifyElementAt(lastIndex, transformator)

fun <T> MutableList<T>.modifyElementAt(index: Int, transformator: (T) -> T) {
    this[index] = transformator(this[index])
}

fun <T> Iterable<T>?.isNullOrEmpty(): Boolean =
    this == null || !this.iterator().hasNext()

inline fun <E> Array<E>?.ifNullOrEmpty(
    crossinline fallback: () -> Array<E>,
): Array<E> =
    if (this == null || this.isEmpty()) fallback()
    else this

inline fun <K, V> Map<K, V>?.ifNullOrEmpty(
    crossinline fallback: () -> Map<K, V>,
): Map<K, V> =
    if (this == null || this.isEmpty()) fallback()
    else this
