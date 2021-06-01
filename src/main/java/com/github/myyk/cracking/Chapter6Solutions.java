package com.github.myyk.cracking;

/**
 * Math and Logic Puzzles
 */
public class Chapter6Solutions {

  /**
   * The Heavy Pill: Given 20 pill bottles, 19 with 1.0 gram pills and 1 with 1.1
   *   gram pills, how would you find which bottle using the scale once?
   *
   * Answer: Put 1 pill from bottle one, 2 from bottle 2, ... n from bottle n.
   *   You would expect (1 + 2 + ... + n) grams if they all were 1.0, but they aren't.
   *   There will be an extra (n * 0.1) grams and that will give you the bottle.
   */
  
  /**
   * Basketball: Can play 2 different games with a basketball hoop.
   *   1. Get a shot to make the hoop
   *   2. Get 3 shots to make 2 hoops
   *   If you have a probability of p of making a hoop, for what values of p should you
   *   pick the first game vs the second.
   *
   * Answer:
   *   chance to win 1 = p
   *   chance to win 2 = 3*p*p - 2*p*p*p
   *   When (3*p*p - 2*p*p*p) > p, then play game 2, otherwise game 1.
   *   If p = 0.5, then it's even odds on both. For all p, the 3*p*p < 2*p*p*p
   *   So we can see that it will be a monotonic function of p. And by graphing it
   *   we can see that as p increases past 0.5 then you should play game 2 as the odds
   *   get better.
   *
   *   Here's how the math works, since I found p > 0.5 by accident.
   *   (3*p*p - 2*p*p*p) > p
   *   3*p - 2*p*p > 1
   *   0 > 1 - 3*p + 2*p*p
   *   0 > (1 - 2p)(1 - p)
   *   we know 0 <= p <= 1
   *   0 > (1 - 2p)
   *   2p > 1
   *   p > 0.5 is when we should play game 2.
   */

  /**
   * Dominos: You have a 8x8 chessboard with the diagonal corners cut off. You have 31
   *   dominos that cover 2 squares. Can you cover the entire board?
   *
   * Answer:
   *   Dominos cover a black and white square always. There are 8x8 squares on a full
   *   chessboard, half are black and half are white. That gives us 32 of each, but our
   *   board has no corners, so either we have 2 less black or 2 less white. Let's say
   *   white. So we have 32 black, 30 white. Our dominos can only cover 31 black and
   *   31 white tiles, so it will not work.
   */

  /**
   * Ants on a Triangle: An ant is on each corner. They have equal chance of choosing to
   *   walk in either direction towards the other corners at the same speed. What are the
   *   chances that one or more ants collide? If there were on an n-cornered polygon and
   *   n-ants what does the answer look like then?
   *
   * Answer:
   *   For the ants to not collide, all need to choose the same direction. The chance they
   *   all go right is 0.5^n. Same for left, so the chance they go the same direction is
   *   2*0.5^n = 0.5^(n-1). We were looking for the opposite though, so it's:
   *     1 - 0.5^(n-1)
   */

  /**
   * Jugs of Water: You have 5qt, 3qt, and all the water. How would you find exactly 4 qts.
   *   The jobs are misshapen so filling them to half would not be possible.
   *
   * Answer:
   *   1. Fill 5
   *   2. 5 => 3, still have 2qt in 5
   *   3. Dump 3
   *   4. 5 => 3, have 2qt in 3
   *   5. Fill 5
   *   6. 5 => 3, still have 4qt in 5
   */

  /**
   * Blue-Eyed Island: People on an island. A visitor comes and says all blue-eyed people
   *   must leave ASAP. Flights out at 8pm every day. Nobody knows or can know their own
   *   eye color, but can see others. Also, nobody knows how many people have blue eyes,
   *   but they do know there is at least one. How many days will it take for the blue-
   *   eyed people to leave.
   *
   * Answer:
   *   There's not a lot of ways to save state here, but we can keep track of number of days.
   *   The people can signal to each other by leaving or not leaving.
   *   There are N blue-eyed people. N > 1.
   *   If N == 1:
   *     The blue-eyed person wouldn't see other blue-eyed people, so they should leave
   *     on day 1.
   *   If N == 2:
   *     The blue-eyed people would see 1 person, so they don't leave day since they don't
   *     know they are blue-eyed. On day 2, they know no one left yesterday, so they
   *     should leave since everyone else saw 2 people.
   *   If N > 2:
   *     We just keep going the same way. If people haven't seen at least as many blue-
   *     eyed people as the day number since the visitor arrived, then they should leave
   *     because they are a blue-eyed person. Everyone else would have seen that many.
   */

  /**
   * The Apocalypse: Post-apocalyptic world queen is concerned about birth-rates. Every
   *   family must have 1 girl or face massive fines. If all families have children until
   *   they have a girl and then they stop with probability of having a girl and a boy being
   *   the same, what would their expected gender ratios be?
   *
   * Answer: The gender ratio is equal. The ratio of females is:
   *   for(i <- 1 until infinity) { 1 / i * (Math.pow(0.5, i))} which is
   *   0.5 + 0.25 + 0.125 + ... = 1
   */

  /**
   * The Egg Drop Problem: 100 story building. If the egg drops from the Nth floor or
   *   above it will break. You have two eggs. Minimize the number of drops in the
   *   worst case. What floor is N?
   *
   * Answer: 14 drops
   *   If we start dropping at a floor with the first egg, it will take at worst case
   *   that floor's amount of drops to find the floor. This is because if it breaks,
   *   it's below, maybe 1 below.
   *   If we go up 1 less floor than we started at, we still have a worst case of
   *   as we did for the first floor. We can continue up this way.
   *   If use this strategy starting at floor 14, we can get an answer in 14 drops.
   *   If we try it starting at floor 13, we can't continue keeping the worst case
   *   at 13 for the upper floors.
   */

  /**
   * 100 Lockers: There are 100 closed lockers. For i from 1 to 100, toggle the every
   *   i lockers starting at locker 1. To toggle a locker, change it state from closed
   *   to open or open to closed. What lockers are open in the end?
   *
   * Answer: 10, The only open lockers are those where their number is a square. So
   *   1, 4, 9, 16, 25, 36, 49, 64, 81, 10. The mathematical reasoning behind this is
   *   that the lockers will be toggled once for each distinct factor they have.
   *   Thus only lockers with an odd number of factors will be left in open.
   *
   *   If a number is prime, it obviously can be factored by itself and 1, because all
   *     numbers can.
   *   Non-squares by definition, would have factors that could be paired to give that
   *     product when multiplied by each other, otherwise it would be a square.
   *   A square will similarly have pairs of factors that could be multiplied to get that
   *     product, but it will also have one pair that the values are not distinct since
   *     they are the square root of the number. This give an odd number of distinct
   *     factors to the numbers.
   */

  /**
   * Poison: You have 1000 bottles of soda, 1 has been poisoned. You have 10 poison
   *   strips that can be used multiple times with a single drop of soda to detect
   *   the poison. They can be reused multiple times as long as they don't come
   *   up positive for poison, which permanently changes them. The poison strips take
   *   7 days to give a result and you can only test once a day. How would you find
   *   the poisoned bottle in the least amount of time.
   *
   * Answer: 7 days.
   *   This answer wasn't obvious to me and had to look it up. I got to 26 days, but
   *   didn't figure out any of the better solutions on my own. The 7 day solution is
   *   quite simple though. If we number each bottle 0 to 999 and represent those as
   *   binary numbers, then we can number the test strips from 0 to 9. Since each
   *   bottle can be represented by a binary number with 10 digits. So for each bottle
   *   if we put a drop on each test strip which corresponds to it's 1s in it's binary
   *   form, then after 7 days, we can just read the binary number for the strips
   *   and it will be the bottle's number.
   */
}
