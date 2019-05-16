package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
	trait TestTrees {
		val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
		val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
	}


  test("dropping a char") {
    val l1: List[(Char, Int)] = List(('a', 3), ('b', 1), ('c', 2))
    val l2: List[(Char, Int)] = dropChar('b', l1)
    assert(l2.head._1 === 'a')
    assert(l2.tail.head._1 === 'c')
    assert(l2.tail.tail.isEmpty)
    val l3: List[(Char, Int)] = dropChar('c', l1)
    assert(l3.head._1 === 'a')
    assert(l3.tail.head._1 === 'b')
    assert(l3.tail.tail.isEmpty)
  }


  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }


  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }


  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }


  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }


  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
  }


  test("decode and encode a very short text should be identity") {
    new TestTrees {
      val ab = List('a', 'b')
      val bits: List[Bit] = encode(t1)(ab)
      val chars: List[Char] = decode(t1, bits)
      assert(chars === ab)
    }
  }

  test("decode and encode a longer text should be identity") {
    new TestTrees {
      val dab = List('d', 'a', 'b')
      val bits: List[Bit] = encode(t2)(dab)
      val chars: List[Char] = decode(t2, bits)
      assert(chars === dab)
    }
  }

  test("times should count the number of each character") {
    val str: List[Char] = "very nice ice".toList
    val counts: List[(Char, Int)] = times(str)
    assert(counts === List(('v',1), ('e',3), ('r',1), ('y',1), (' ',2), ('n',1), ('i',2), ('c',2)))
  }

  test("convert should work") {
    new TestTrees {
      assert(convert(t2) === List(('a',List(0, 0)), ('b',List(0, 1)), ('d',List(1))))
    }
  }

  test("decode and quick encode a longer text should be identity") {
    new TestTrees {
      val dab = List('d', 'a', 'b')
      val bits: List[Bit] = quickEncode(t2)(dab)
      println("bits:" + bits)
      val chars: List[Char] = decode(t2, bits)
      println("chars:" + chars)
      assert(chars === dab)
    }
  }

}
