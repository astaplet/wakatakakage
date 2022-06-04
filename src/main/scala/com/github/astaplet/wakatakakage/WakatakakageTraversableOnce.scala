/*
 *    Copyright 2022 Aleksandra Stapleton
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package com.github.astaplet.wakatakakage

object WakatakakageTraversableOnce {
  implicit class TraversableOnceExtensions[T](
      collection: TraversableOnce[T]
  ) {

    /** Just like maxBy, but it returns the value returned by F when applied
      * to the max element instead of returning the whole max element. Handy shortcut
      * when the value you're ordering by is also the only one you care about.
      * @param f Function to derive values for ordering the collection
      * @param ordering An ordering for type N
      * @tparam N The type of value to be ordered by
      * @return The value of type N from the maximum element.
      */
    def maxByValue[N](f: T => N)(implicit ordering: Ordering[N]): N = {
      f(collection.maxBy(f))
    }

    /** Just like minBy, but it returns the value returned by F when applied
      * to the min element instead of returning the whole min element. Handy shortcut
      * when the value you're ordering by is also the only one you care about.
      * @param f Function to derive values for ordering the collection
      * @param ordering An ordering for type N
      * @tparam N The type of value to be ordered by
      * @return The value of type N from the minimum element.
      */
    def minByValue[N](f: T => N)(implicit ordering: Ordering[N]): N = {
      f(collection.minBy(f))
    }

    /** returns true if f returns true for any element in the list
      * @param f A function that returns a boolean value for an input of type T
      * @return true if any element satisfies f, false otherwise
      */
    def forAny(f: T => Boolean): Boolean = {
      collection.foldLeft(false)((l: Boolean, r: T) => l || f(r))
    }
  }
}
