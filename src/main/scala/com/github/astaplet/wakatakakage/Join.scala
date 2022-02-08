/*
 * Copyright (c) 2022.
 * Aleksandra Stapleton
 */
package com.github.astaplet.wakatakakage

object Join {
  /**
   * Perform a full outer join on two maps. Returns a map from all keys in either map to an option
   * containing either the value present on the left or on the right, or None if the value wasn't present
   * on that side of the join
   * @param left the left side of the join
   * @param right the right side of the join
   * @tparam K key type
   * @tparam V value type
   * @return a map from keys to the options containing the key's value on either side of the join.
   */
  def outerJoin[K, V](left: Map[K, V], right: Map[K, V]): Map[K, (Option[V], Option[V])] = {
    val map1 = left
      .map(item => {
        val key = item._1
        val r = right.get(key)
        key -> (Option(item._2), r)
      })

    val map2 = right
      .filterKeys(!left.contains(_))
      .map(item => {
        item._1 -> (None, Option(item._2))
      })

    map1 ++ map2
  }
}