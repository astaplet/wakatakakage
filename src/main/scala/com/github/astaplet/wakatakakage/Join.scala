/*
   Copyright 2022 Aleksandra Stapleton

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.astaplet.wakatakakage

/**
 * A small utility library of generic joins between Scala maps. Written after a search for an existing
 * outer join implementation yielded only suggestions to use Spark which is just massively overkill for
 * the small in-memory use cases I was working on. The outer join is the only remotely complex piece of
 * code here but I've needed these things often enough that it's probably just better to have a library
 * out there rather than having people need to waste brainpower reinventing an ancient wheel every time
 * they need to join some maps. Slàinte mhath!
 */
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

  /**
   * Join two maps keeping the whole LHS and adding values from LHS keys also present on the RHS
   * Note that there is no performance or functional difference between a left and a right join
   * since these functions operate on maps in memory, so the selection of join should come down to
   * which will make your code the most semantically clear.
   * @param left The left side of the join, all values are kept
   * @param right The right side of the join, only values for keys also on the left are kept
   * @tparam K Key type
   * @tparam V Value type
   * @return A map from keys to LHS values and Options of RHS values
   */
  def leftJoin[K, V](left: Map[K, V], right: Map[K, V]): Map[K, (V, Option[V])] = {
    left.map(item => item._1 -> (item._2, right.get(item._1)))
  }

  /**
   * Join two maps keeping the whole RHS and adding values from LHS keys also present on the RHS.
   * Note that there is no performance or functional difference between a left and a right join
   * since these functions operate on maps in memory, so the selection of join should come down to
   * which will make your code the most semantically clear.
   * @param left Left side of the join
   * @param right Right side of the join
   * @tparam K Key type
   * @tparam V Value type
   * @return A map from keys to Options of LHS values and all RHS values
   */
  def rightJoin[K, V](left: Map[K, V], right: Map[K, V]): Map[K, (Option[V], V)] = {
    right.map(item => item._1 -> (left.get(item._1), item._2))
  }

  /**
   * Join two maps keeping only keys where a value is present on both sides
   * @param left The left side of the join
   * @param right The right side of the join
   * @tparam K Key type
   * @tparam V Value type
   * @return A map from keys present in both maps to the values from both maps
   */
  def innerJoin[K, V](left: Map[K, V], right: Map[K, V]): Map[K, (V, V)] = {
    left.filterKeys(right.contains(_))
      .map(item => item._1 -> (item._2, right.get(item._1).getOrElse(throw new IllegalStateException("RHS Contains key pointing to an empty Option"))))
  }
}