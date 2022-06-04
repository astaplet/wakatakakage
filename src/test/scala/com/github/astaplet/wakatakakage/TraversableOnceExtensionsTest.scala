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

import com.github.astaplet.wakatakakage.WakatakakageTraversableOnce.TraversableOnceExtensions
import org.scalatest.flatspec.AnyFlatSpec

case class NumberInABox(value: Int)

class TraversableOnceExtensionsTest extends AnyFlatSpec {
  "maxByValue" should "return 17" in {
    val travOnce = Seq(NumberInABox(17), NumberInABox(2), NumberInABox(5))
    val max = travOnce.maxByValue(_.value)
    assert(max == 17)
  }

  "minByValue" should "return 2" in {
    val travOnce = Seq(NumberInABox(17), NumberInABox(2), NumberInABox(5))
    val min = travOnce.minByValue(_.value)
    assert(min == 2)
  }

  "forAny" should "return true" in {
    val travOnce = Seq(NumberInABox(17), NumberInABox(2), NumberInABox(5))
    val ret = travOnce.forAny(_.value % 5 == 0)
    assert(ret == true)
  }

  "forAny" should "return false" in {
    val travOnce = Seq(NumberInABox(17), NumberInABox(2), NumberInABox(5))
    val ret = travOnce.forAny(_.value % 3 == 0)
    assert(ret == true)
  }
}
