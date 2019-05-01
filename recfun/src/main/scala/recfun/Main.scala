package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if (c < 0 || r < 0 || c > r) throw new IllegalArgumentException
      if (c == 0 || c == r) 1 else pascal(c-1, r-1) + pascal(c, r-1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {

      def balance(leftParens: Int, remain: List[Char]): Boolean = {
        if (leftParens < 0) false
        else if (remain.isEmpty) leftParens == 0
        else if (remain.head == '(') balance(leftParens + 1, remain.tail)
        else if (remain.head == ')') balance(leftParens - 1, remain.tail)
        else balance(leftParens, remain.tail)
      }

      balance(0, chars)
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {

      def countChangeUsingACoin(money:Int, otherCoins: List[Int], usedCoin: Int, usedTimes: Int): Int = {
        if (usedTimes < 0 || usedCoin*usedTimes > money) {
          0
        }
        else if (otherCoins.isEmpty) {
          if (usedCoin*usedTimes == money) 1 else 0
        }
        else {
          val usedExactly = if (usedCoin * usedTimes == money) 1 else countChange(money - usedCoin * usedTimes, otherCoins)
          val usedOneLess = countChangeUsingACoin(money, otherCoins, usedCoin, usedTimes - 1)
          usedExactly + usedOneLess
        }
      }

      if (money == 0) 1
      else if (coins.isEmpty || money < 0) 0
      else countChangeUsingACoin(money, coins.tail, coins.head, money/coins.head)

    }

  }
