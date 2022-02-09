/*
   Copyright 2022 Aleksandra Stapleton

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.astaplet.wakatakakage

import org.scalatest.flatspec.AnyFlatSpec

class JoinTest extends AnyFlatSpec {

  "Outer Join" should "Keep all keys" in {
    val lhs = Map(1 -> "one", 2 -> "two")
    val rhs = Map(2 -> "zwei", 3 -> "drei")

    val joined = Join.outerJoin(lhs, rhs)

    assert(joined.contains(1))
    assert(joined.contains(2))
    assert(joined.contains(3))

    joined.get(1).map(vals => {
      assert(vals._1.isDefined && vals._1.get == "one")
      assert(vals._2.isEmpty)
    })

    joined.get(2).map(vals => {
      assert(vals._1.isDefined && vals._1.get == "two")
      assert(vals._2.isDefined && vals._2.get == "zwei")
    })

    joined.get(3).map(vals => {
      assert(vals._1.isEmpty)
      assert(vals._2.isDefined && vals._2.get == "drei")
    })
  }

  "inner join" should "keep only key 2" in {
    val lhs = Map(1 -> "one", 2 -> "two")
    val rhs = Map(2 -> "zwei", 3 -> "drei")

    val joined = Join.innerJoin(lhs, rhs)

    assert(joined.size == 1)
    assert(joined.contains(2))
    joined.get(2).map(value => assert(value == ("two", "zwei")))
  }

  "Left Join" should "Keep only keys 1 and 2" in {
    val lhs = Map(1 -> "one", 2 -> "two")
    val rhs = Map(2 -> "zwei", 3 -> "drei")

    val joined = Join.leftJoin(lhs, rhs)

    assert(joined.size == 2)
    assert(joined.contains(1))
    assert(joined.contains(2))
    assert(!joined.contains(3))

    joined.get(1).map(vals => {
      assert(vals._1 == "one")
      assert(vals._2.isEmpty)
    })

    joined.get(2).map(vals => {
      assert(vals._1 == "two")
      assert(vals._2.isDefined && vals._2.get == "zwei")
    })
  }

  "Right Join" should "Keep only keys 2 and 3" in {
    val lhs = Map(1 -> "one", 2 -> "two")
    val rhs = Map(2 -> "zwei", 3 -> "drei")

    val joined = Join.rightJoin(lhs, rhs)

    assert(joined.size == 2)
    assert(!joined.contains(1))
    assert(joined.contains(2))
    assert(joined.contains(3))

    joined.get(2).map(vals => {
      assert(vals._1.isDefined && vals._1.get == "two")
      assert(vals._2 == "zwei")
    })

    joined.get(3).map(vals => {
      assert(vals._1.isEmpty)
      assert(vals._2 == "drei")
    })
  }
}
