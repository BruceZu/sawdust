//  Copyright 2016 The Sawdust Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package bitwise;

import org.junit.Assert;
import org.junit.Test;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;

public class BitMaskTest {
   /*
   sources of magic algorithms:
    http://aggregate.org/MAGIC/
    http://graphics.stanford.edu/~seander/bithacks.html
    http://homepage.cs.uiowa.edu/~jones/bcd/divide.html
    http://programming.sirrida.de/
    http://chessprogramming.wikispaces.com/Bit-Twiddling
    http://bitmath.blogspot.nl/
    http://haroldbot.nl/
    http://www.dspguru.com/comp.dsp/tricks
    http://bullet.googlecode.com/svn/trunk/Extras/CDTestFramework/Opcode/Ice/IceUtils.h
    https://chessprogramming.wikispaces.com/Population+Count
    http://bisqwit.iki.fi/source/misc/bitcounting/
   */

    // https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html
    static final int flagbit2 = 2;

    /**
     * 二进制逻辑运算公式问题
     * ① 0,1律
     * A•0=0
     * A•1=A
     * A＋0=A
     * A＋1=1
     * ② 交换律
     * A＋B=B＋A
     * A•B=B•A
     * ③ 结合律
     * A＋B＋C =（A＋B）＋C = A＋（B＋C）
     * A•B•C =（A•B）•C = A•（B•C）
     * ④ 分配律
     * A•（B＋C）= A•B ＋ A•C
     * ⑤ 重叠律
     * A＋A＋...＋A = A
     * A•A•...•A = A
     * ⑥ 互补律
     * A + A = 1 A•A = 0
     * ⑦ 吸收律
     * A＋A•B = A A•（A＋B） = A
     * A＋A•B = A＋B A•（A＋B） = A•B
     * ⑧ 对合律
     * 对一个逻辑变量两次取反仍是它本身
     * ⑨ 德•摩根定理
     * A+B = A•B
     * A•B = A＋B
     * 例如：F = A•B＋A•B＋A•B
     * =A•B＋A（B＋B） （利用分配律）
     * =A•B＋A （利用互补律以及0,1律）
     * = A＋B （利用吸收律）
     */
    @Test(timeout = 3000L, expected = Test.None.class)
    public void testShift() {
        Assert.assertEquals(1 << 65, 1 << 65 % 32);
        Assert.assertEquals(-1 << 65, -1 << 65 % 32);

        /**
         * https://en.wikipedia.org/wiki/Bitwise_operation#Arithmetic_shift
         */
        //attention x >> 1  not always equals x/2
        int x = Integer.MIN_VALUE + 1;
        Assert.assertNotEquals(x / 2, x >> 1); //attention
        x = -3;
        Assert.assertNotEquals(x / 2, x >> 1);

        x = -3;
        Assert.assertEquals((x >> 31) < 0 ?
                        -(((x ^ (x >> 31)) - (x >> 31)) >> 1)
                        : (((x ^ (x >> 31)) - (x >> 31)) >> 1),
                x / 2);
        x = 3;
        Assert.assertEquals((x >> 31) < 0 ?
                        -(((x ^ (x >> 31)) - (x >> 31)) >> 1)
                        : (((x ^ (x >> 31)) - (x >> 31)) >> 1),
                x / 2);

        //2 ^^ n == 1 << n    (0 =< n <= 30)
        Assert.assertEquals(pow(2, 0), 1 << 0, 0);
        Assert.assertEquals(pow(2, 30), 1 << 30, 0);
        Assert.assertEquals(Integer.MIN_VALUE, 1 << 31, 0); // attention

        Assert.assertEquals(2147483648L, pow(2, 31), 0);
        Assert.assertEquals(-2147483648L, Integer.MIN_VALUE, 0);

        Assert.assertEquals(2147483647, Integer.MAX_VALUE, 0);
        Assert.assertEquals(Integer.MIN_VALUE - 1, Integer.MAX_VALUE, 0);

        Assert.assertEquals(Integer.MIN_VALUE * (-1), Integer.MIN_VALUE);
        Assert.assertEquals(-1 >> 1, -1);

        // shift - distance = sift (32 - distance)
        Assert.assertEquals(2 << -30, 2 << (32 - 30));
        Assert.assertEquals(2 >> -30, 2 >> (32 - 30));
        Assert.assertEquals(2 >>> -30, 2 >>> (32 - 30));

        Assert.assertEquals(-2 << -30, -2 << (32 - 30));
        Assert.assertEquals(-2 >> -30, -2 >> (32 - 30));
        Assert.assertEquals(-2 >>> -30, -2 >>> (32 - 30));
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testBitOR() {
        /**
         *
         * 结合律: A or (B or C) = (A or B) or C
         交换律: A or  B = B or A
         分配律: (A or (B and C)) = ((A or B) and (A or C))
         (A and (B or C)) = ((A and B) or (A and C))
         (A or (B = C)) = ((A or B) = (A or C))
         幂等律: A or A = A
         单调性: (A -> B) -> ((C or A) -> (C or B))
         (A -> B) -> ((A or C) -> (B or C))
         保真性: 所有变量的真值皆为“真”的命题在逻辑或运算后的结果为真。
         保假性: 所有变量的真值皆为“假”的命题在逻辑或运算后的结果为假。
         * */
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testBitFLIP() {
        Assert.assertEquals(Integer.MIN_VALUE, -Integer.MIN_VALUE);
        Assert.assertEquals(~Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testBitXORandFlip() {
        // x ^ (-1) = ~x
        int x = Integer.MAX_VALUE; // Integer.MIN_VALUE;
        Assert.assertEquals(x ^ (-1), ~x);
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testBitXOR() {
        // 交换律：A ^ B = B ^ A
        Assert.assertEquals(1 ^ 4 ^ 3, 1 ^ 3 ^ 4);
        Assert.assertEquals(1 ^ 4 ^ 3, 4 ^ 3 ^ 1);
        Assert.assertEquals(1 ^ 4 ^ 3, 4 ^ 1 ^ 3);
        Assert.assertEquals(1 ^ 4 ^ 3, 3 ^ 1 ^ 4);
        Assert.assertEquals(1 ^ 4 ^ 3, 3 ^ 4 ^ 1);

        // 结合律：A ^ (B ^ C)=(A ^ B) ^ C

        // 恒等律：X ^ 0 = X

        // 归零律：X ^ X = 0

        // 自反：A ^B ^ B = A^ 0 = A
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testSwapWithoutTemporary() {
        int a = Integer.MIN_VALUE, b = -5;
        int backa = a, backb = b;
        a = a ^ b; // x' = (x ^ y)
        b = a ^ b; // y' = (y ^ (x ^ y)) = x
        a = a ^ b; // x' = (x ^ y) ^ x = y

        Assert.assertEquals(a, backb);
        Assert.assertEquals(b, backa);

        int x = Integer.MIN_VALUE, y = Integer.MAX_VALUE;
        int backx = x, backy = y;
        x += y; 	/* x' = (x + y) */
        y = x - y;	/* y' = (x + y) - y = x */
        x -= y;		/* x' = (x + y) - x = y */
        Assert.assertEquals(a, backb);
        Assert.assertEquals(b, backa);
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testBitAND() {
        /**
         * 结合律: A and (B and C) = (A and B) and C
         * 交换律: A and  B = B and A
         * 分配律: (A and (B or C)) = ((A and B) or (A and C))
         * (A or (B and C)) = ((A or B) and (A or C))
         * 幂等律: A and A = A
         * 单调性: (A -> B) -> ((C and A) -> (C and B))
         * (A -> B) -> ((A and C) -> (B and C))
         * 保真性: 所有变量的真值皆为“真”的命题在逻辑与运算后的结果为真。
         * 保假性: 所有变量的真值皆为“假”的命题在逻辑与运算后的结果为假。
         * 如果用二进制来表达真（1）和假（0），逻辑与运算与算术乘法运算一致。
         */
        // get the last '1' :   x & -x
        Assert.assertEquals(-40 & 40, 8); // -40 = 0 - 40 = 32 + 8

        // get the last '1' :   x & ~(x - 1)
        Assert.assertEquals(40 & ~(40 - 1), 8); // 40 - 1 = 32 + 7
        Assert.assertEquals(-40 & ~(-40 - 1), 8);
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void test2SComplement() {
        // -x = ~x + 1  two's complement = ones' complement +1
        Assert.assertEquals(-40, ~40 + 1);
        Assert.assertEquals(40, ~-40 + 1);

        // -x = ~(x - 1)
        Assert.assertEquals(-40, ~(40 - 1));
        Assert.assertEquals(40, ~(-40 - 1));

        // ~x +1 = ~(x - 1)
        Assert.assertEquals(~(40 - 1), ~40 + 1);
        Assert.assertEquals(~(-40 - 1), ~-40 + 1);

        // two's complement
        Assert.assertEquals(1, 0b00000000000000000000000000000001);
        Assert.assertEquals(-1, 0b11111111111111111111111111111111);
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testTheLeastSignificant1Bit() {
        // x - (x & (x - 1))
        Assert.assertEquals(40 - (40 & (40 - 1)), 8);

        // x ^ (x & (x - 1))
        Assert.assertEquals(40 ^ (40 & (40 - 1)), 8);
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testCutTheLast1Bit() {
        // cut the last '1':    x & (x-1)
        Assert.assertEquals(40 & (40 - 1), 32);
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testBranchlessMin() {
        int m = 3;
        int n = 2;
        Assert.assertEquals(n, n + ((m - n) & (m - n) >> 31) /* Attention (m - n) >> 31 is 0 or -1 */);
        Assert.assertEquals(n, m - ((m - n) & ~(m - n) >> 31));
        Assert.assertEquals(n, m + ((n - m) & (n - m) >> 31));
        Assert.assertEquals(n, n - ((n - m) & ~(n - m) >> 31));

        m = 2;
        n = 3;
        Assert.assertEquals(m, n + ((m - n) & (m - n) >> 31));
        Assert.assertEquals(m, m - ((m - n) & ~(m - n) >> 31));
        Assert.assertEquals(m, m + ((n - m) & (n - m) >> 31));
        Assert.assertEquals(m, n - ((n - m) & ~(n - m) >> 31));
        m = -3;
        n = -2;
        Assert.assertEquals(m, n + ((m - n) & (m - n) >> 31));
        Assert.assertEquals(m, m - ((m - n) & ~(m - n) >> 31));
        Assert.assertEquals(m, m + ((n - m) & (n - m) >> 31));
        Assert.assertEquals(m, n - ((n - m) & ~(n - m) >> 31));
        m = -2;
        n = -3;
        Assert.assertEquals(n, n + ((m - n) & (m - n) >> 31));
        Assert.assertEquals(n, m - ((m - n) & ~(m - n) >> 31));
        Assert.assertEquals(n, m + ((n - m) & (n - m) >> 31));
        Assert.assertEquals(n, n - ((n - m) & ~(n - m) >> 31));
        // special case n = 0
        m = 3;
        Assert.assertEquals(min(m, 0), m & m >> 31);
        m = -3;
        Assert.assertEquals(min(m, 0), m & m >> 31);
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testBranchlessMax() {
        int m = 2;
        int n = 3;
        Assert.assertEquals(n, m - ((m - n) & (m - n) >> 31) /* Attention (m - n) >> 31 is 0 or -1 */);
        Assert.assertEquals(n, n ^ ((m ^ n) & ((n - m) >> 31)));
        m = 3;
        n = 2;
        Assert.assertEquals(m, m - ((m - n) & (m - n) >> 31));
        Assert.assertEquals(m, n ^ ((m ^ n) & ((n - m) >> 31)));
        m = -3;
        n = -2;
        Assert.assertEquals(n, m - ((m - n) & (m - n) >> 31));
        Assert.assertEquals(n, n ^ ((m ^ n) & ((n - m) >> 31)));
        m = -2;
        n = -3;
        Assert.assertEquals(m, m - ((m - n) & (m - n) >> 31));
        Assert.assertEquals(m, n ^ ((m ^ n) & ((n - m) >> 31)));
        // special case, n = 0
        m = 3;
        n = 0;
        Assert.assertEquals(max(m, 0), m - (m & m >> 31));
        Assert.assertEquals(m, n ^ (m & -m >> 31));

        //m & ~(m >> 31)
        Assert.assertEquals(max(m, 0), -(-m & -m >> 31));
        Assert.assertEquals(max(m, 0), m & ~(m >> 31) /* here ~(m >> 31) is -1 or 0 */);
        m = -3;
        Assert.assertEquals(max(m, 0), m - (m & m >> 31));
        Assert.assertEquals(max(m, 0), -(-m & -m >> 31));
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testMod_1() {
        // Compute modulus division by 1 << s without a division operator
        // Note: only for no-negative
        // x % (1 << n) == x & ((1 << n) − 1)
        Assert.assertEquals(7 % 4, 7 & (4 - 1));
    }


    @Test(timeout = 3000L, expected = Test.None.class)
    public void testMod_2() {
        // Compute modulus division by (1 << s) - 1 without a division operator
        // Note: only for no-negative
        int n = 8;                // numerator
        int s = 2;                // s > 0
        int d = (1 << s) - 1;  // so d is either 1, 3, 7, 15, 31, ...).
        int m;                      // n % d goes here.

        for (m = n; n > d; n = m) {
            for (m = 0; n != 0; n >>= s) {
                m += n & d;
            }
        }
        // Now m is a value from 0 to d, but since with modulus division
        // we want m to be 0 when it is d.
        m = m == d ? 0 : m;
        Assert.assertEquals(8 % 3, m);

        // takes at most 5 + (4 + 5 * ceil(N / s)) * ceil(lg(N / s)) operations,
        // where N is the number of bits in the numerator. In other words, it takes
        // at most O(N * lg(N)) time.
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testLogBase2() {
        // Find the log base 2 of an integer with the MSB N set in O (N) operations(the obvious way)
        // 1.
        int v = 0b100; // 32-bit word to find the log base 2 of
        int r = 0; // r will be lg(v)

        while ((v >>= 1) != 0) { // unroll for more speed...
            r++;
        }
        Assert.assertEquals(r, 2);

        // 2.
        int x = v;
        x |= (x >> 1);
        x |= (x >> 2);
        x |= (x >> 4);
        x |= (x >> 8);
        x |= (x >> 16);
        //x = ones32(x >> 1);
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testBranchlessABS() {
        // Branchless abs(x):  = x ^ (x >> 31) - (x >> 31)
        // NOTE: Integer.MIN_VALUE is special
        int x = Integer.MIN_VALUE;
        Assert.assertEquals(x ^ (x >> 31) - (x >> 31), x); //special
        Assert.assertEquals(x, -x);  // special
        Assert.assertEquals(x, ~x + 1); // special

        int n = 5;
        Assert.assertEquals(5, (5 ^ 0) - 0);
        Assert.assertEquals(5, (5 ^ 5 >> 31) - (5 >> 31));
        n = -5;
        Assert.assertEquals(5, (-5 ^ -1) - (-1));
        Assert.assertEquals(5, (-5 ^ -5 >> 31) - (-5 >> 31));
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testAverageOfIntegers() {
        /*

        (x + y)      = ( 2 * (x & y) + (x ^ y))
          x                           0 0 1  1
          y                           0 1 0  1
          x+y                         1 0 0  0

          x ^ y                       0 1 1  0
          x & y                       0 0 0  1
          x & y << 1
        = 2 * x & y                   0 0 1  0
          ( 2 * (x & y) + (x ^ y))    1 0 0  0

          (x+y)/2 = (x & y) + ((x ^ y) >> 1)
          (x+y)/2                     0 1 0  0

          x ^ y                       0 1 1  0
          x & y                       0 0 0  1
          ((x ^ y) >> 1)              0 0 1  1
          (x & y) + ((x ^ y) >> 1)    0 1 0  0

         */
        // this code sequence cannot overflow.
        int x = Integer.MAX_VALUE - 1;
        int y = x;
        Assert.assertNotEquals((x + y) / 2, 2147483647);
        Assert.assertEquals((x & y) + ((x ^ y) >> 1), 2147483646);

        x = Integer.MIN_VALUE + 1;
        y = x;
        Assert.assertNotEquals((x + y) / 2, -2147483647);
        Assert.assertEquals((x & y) + ((x ^ y) >> 1), -2147483647);

        // why (x + y) = ((x&y)+(x|y)) ok but
        // the average (((x&y)>>1+((x|y)>>1)) can not work?
        // There are 2 '>>1', when a and b both have '1' on the right side,
        // The average will miss 1.
        //((x & y) + (x | y)) >> 1   can not work too, as it can over flow

        x = 0b0110;
        y = 0b1010;
        /* Integer.MAX_VALUE and Integer.MIN_VALUE
        Assert.assertEquals(x + y, (2 * (x & y) + (x ^ y)));
        Assert.assertEquals(x + y, ((x & y) + (x | y)));
        System.out.println(String.format("x:%d y:%d", x, y));
        System.out.println("x                              " + BitOperation.binaryFormat(x));
        System.out.println("y                              " + BitOperation.binaryFormat(y));
        System.out.println();
        System.out.println("x & y                          " + BitOperation.binaryFormat(x & y));
        System.out.println("x ^ y                          " + BitOperation.binaryFormat(x ^ y));
        System.out.println("((x ^ y) >> 1)                 " + BitOperation.binaryFormat(((x ^ y) >> 1)));
        System.out.println("(x & y) + ((x ^ y) >> 1)       " + BitOperation.binaryFormat((x & y) + ((x ^ y) >> 1)));
        System.out.println();
        System.out.println("x | y                          " + BitOperation.binaryFormat(x | y));
        System.out.println("x & y                          " + BitOperation.binaryFormat(x & y));
        System.out.println("(x | y) >> 1                   " + BitOperation.binaryFormat((x | y) >> 1));
        System.out.println("(x & y) >>1                    " + BitOperation.binaryFormat((x & y) >> 1));
        System.out.println("((x | y) >>1) +((x & y) >>1)   " + BitOperation.binaryFormat(((x | y) >> 1) + ((x & y) >> 1)));
        System.out.println("((x | y) >>1) +((x & y) >>1)+1 " + BitOperation.binaryFormat(((x | y) >> 1) + ((x & y) >> 1) + 1));
        */
        Assert.assertEquals((x & y) + ((x ^ y) >> 1),
                (((x & 1) == (y & 1)) && ((y & 1) == 1)) // x and y are both odd
                        ? ((x | y) >> 1) + ((x & y) >> 1) + 1
                        : ((x | y) >> 1) + ((x & y) >> 1));
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testIntegerSelection() {
        int a, b, c, d;
        //a = 5; b = 3; c = 6; d = 7;
        a = -5;
        b = -3;
        c = 6;
        d = 7;
        int x, y, z;

        if (a < b) {
            x = c;
        } else {
            x = d;
        }
        int sign = ((a - b) >> 31);
        y = ((sign & (c ^ d)) ^ d); // better
        z = (sign & c) + ((~sign) & d);
        Assert.assertEquals(x, y);
        Assert.assertEquals(x, z);
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testIsPowerOf2() {
        // non-negative binary integer value x,
        // if x > 0 and (x & (x - 1)) == 0, true.
        // if x > 0 and (x & (-x)) ==x, true.
        int x = 0;
        Assert.assertEquals((x >> 31) == 0 && (x & (x - 1)) == 0, true);
        Assert.assertEquals((x >> 31) == 0 && (x & (-x)) == x, true);
        x = 5;
        Assert.assertEquals((x >> 31) == 0 && (x & (x - 1)) == 0, false);
        Assert.assertEquals((x >> 31) == 0 && (x & (-x)) == x, false);
        x = -8;
        Assert.assertEquals((x >> 31) == -1 && (x & (x - 1)) == 0, false);
        Assert.assertEquals((x >> 31) == -1 && (x & (-x)) == x, false);
    }

    @Test(timeout = 3000L, expected = Test.None.class)
    public void testEvenOrOdd() {
        // even or odd : num & 1
        int even = -4;
        int odd = -3;
        Assert.assertEquals(false, (even & 1) == 1);
        Assert.assertEquals(true, (odd & 1) == 1);
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testLeadingZeroCount_1() {
        int x = 0b1000;
        x |= (x >> 1);
        x |= (x >> 2);
        x |= (x >> 4);
        x |= (x >> 8);
        x |= (x >> 16);
        Assert.assertEquals(28, 32 - Integer.bitCount(x));
    }

    @Test(timeout = 30l, expected = org.junit.Test.None.class)
    public void testLeadingZeroCount_2() {
        int i = -0b1000;
        int n = 1;
        if (i == 0) {
            n = 32;
        }
        if (i >>> 16 == 0) {
            n += 16;
            i <<= 16;
        }
        if (i >>> 24 == 0) {
            n += 8;
            i <<= 8;
        }
        if (i >>> 28 == 0) {
            n += 4;
            i <<= 4;
        }
        if (i >>> 30 == 0) {
            n += 2;
            i <<= 2;
        }
        n -= i >>> 31;
        Assert.assertEquals(0, n);
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testNextLargestPowerOf2() {
        // Note: only for positive integer
        int x = 0b100;
        x |= (x >> 1);
        x |= (x >> 2);
        x |= (x >> 4);
        x |= (x >> 8);
        x |= (x >> 16);
        Assert.assertEquals(0b1000, x + 1);
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testMostSignificant1Bit() {
        // Note: only for positive integer
        int x = 0b011010;
        x |= (x >> 1);
        x |= (x >> 2);
        x |= (x >> 4);
        x |= (x >> 8);
        x |= (x >> 16);
        Assert.assertEquals(0b010000, x ^ x >> 1);
        Assert.assertEquals(0b010000, (x + 1) >> 1);
        Assert.assertEquals(0b010000, x & ~x >> 1);
        Assert.assertEquals(0b010000, x & ~(x >> 1));
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testTrailingZeroCount_1() {
        int x = -0b10000;
        Assert.assertEquals(Integer.bitCount((x & -x) - 1), 4);
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testTrailingZeroCount_2() {
        int v = -0b10000;
        int c;
        if (v != 0) {
            v = (v ^ (v - 1)) >> 1;
            for (c = 0; v != 0; c++) {
                v >>= 1;
            }
        } else {
            c = 32;
        }
        Assert.assertEquals(c, 4);
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testTrailingZeroCount_3() {
        int v = -0b1100;
        int c = 32;
        v &= -v;
        if (v != 0) c = 31;
        if ((v & 0x0000FFFF) != 0) c -= 16;
        if ((v & 0x00FF00FF) != 0) c -= 8;
        if ((v & 0x0F0F0F0F) != 0) c -= 4;
        if ((v & 0x33333333) != 0) c -= 2;
        if ((v & 0x55555555) != 0) c -= 1;
        Assert.assertEquals(c, 2);
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testTrailingZeroCount_4() {
        int v = 0;
        int y;
        int c;
        if (v == 0) {
            c = 32;
            return;
        }
        c = 31;
        if (v << 16 != 0) {
            c = c - 16;
            v = v << 16;
        }
        if (v << 8 != 0) {
            c = c - 8;
            v = v << 8;
        }
        if (v << 4 != 0) {
            c = c - 4;
            v = v << 4;
        }
        if (v << 2 != 0) {
            c = c - 2;
            v = v << 2;
        }
        Assert.assertEquals(c - ((v << 1) >>> 31), 2);
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testTrailingZeroCount_5() {
        int v = Integer.MIN_VALUE;
        int c = 0;
        if ((v & 1) != 0) { // special case for odd v (assumed to happen half of the time)
            c = 0;
            return;
        }
        if ((v & 0xffff) == 0) {
            v >>= 16;
            c = c + 16;
        }
        if ((v & 0xff) == 0) {
            v >>= 8;
            c = c + 8;
        }
        if ((v & 0xf) == 0) {
            v >>= 4;
            c = c + 4;
        }
        if ((v & 0x3) == 0) {
            v >>= 2;
            c = c + 2;
        }
        if ((v & 1) == 0) {
            v >>= 1;
            c = c + 1;
        }
        if ((v & 1) == 0) {
            v >>= 1;
            c = c + 1;
        }
        Assert.assertEquals(c, 31);
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testInterleaveBits_1() {
        // x and y must initially be less than 65536.
        short x = 0b10101010;
        short y = 0b01010101;
        int z = 0; // bits of x are in the even positions and y in the odd;

        for (int i = 0; i < 32; i++) {
            z |= (x & 1 << i) << i | (y & 1 << i) << (i + 1);
        }
        Assert.assertEquals(z, 0b0110011001100110);
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testInterleaveBits_2() {
        // Interleave lower 16 bits of x and y.
        // x and y must initially be less than 65536. 0b0000 0000 0000 0001 0000 0000 0000 0000
        int x = 0b1010101010101010;
        int y = 0b0101010101010101;
        int z = 0;
        x = (x | (x << 8)) & 0x00FF00FF;
        x = (x | (x << 4)) & 0x0F0F0F0F;
        x = (x | (x << 2)) & 0x33333333;
        x = (x | (x << 1)) & 0x55555555;

        y = (y | (y << 8)) & 0x00FF00FF;
        y = (y | (y << 4)) & 0x0F0F0F0F;
        y = (y | (y << 2)) & 0x33333333;
        y = (y | (y << 1)) & 0x55555555;

        z = x | (y << 1); // bits of x are in the even positions and y in the odd;
        Assert.assertEquals(z, 0b01100110011001100110011001100110);
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testIfAWordHasAZeroByte_1() {
        // Determine if a word has a zero byte
        int v = 0b11111111000000001111111111111011;
        Assert.assertEquals(((v - 0x01010101) & ~v & 0x80808080) != 0, true);
        //   0x01010101 == 0b00000001000000010000000100000001
        // - 0x01010101 == 0b11111110111111101111111011111111
        //   0x80808080 == 0b10000000100000001000000010000000
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testIfAWordHasAZeroByte_2() {
        int v = 0b11111111000000001111111111111011;
        Assert.assertEquals(!(((v & 0xff) != 0)
                        && ((v & 0xff00) != 0)
                        && ((v & 0xff0000) != 0)
                        && ((v & 0xff000000) != 0)),
                true);
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testComputeTheLexicographicallyNextBitPermutation_1() {
        // Suppose we have a pattern of N bits set to 1 in an integer and we want the next permutation of N 1 bits
        // in a lexicographical sense. For example, if N is 3 and the bit pattern is
        // 00010011,
        // the next patterns
        // would be
        // 00010101,
        // 00010110,
        // 00011001,
        // 00011010,
        // 00011100,
        // 00100011,
        // and so forth.
        int n = 0B111; // current permutation of bits
        int w; // next permutation of bits
        // 1. fast way
        int trailingZero;
        // calculate the trailingZero
        int v = n;
        if (v != 0) {
            v = (v ^ (v - 1)) >> 1;
            for (trailingZero = 0; v != 0; trailingZero++) {
                v >>= 1;
            }
        } else {
            trailingZero = 32;
        }

        int t = n | (n - 1);
        w = (t + 1) | (((~t & -~t) - 1) >> (trailingZero + 1));
        Assert.assertEquals(w, 0b1011);
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testComputeTheLexicographicallyNextBitPermutation_2() {
        // 2.
        int n = 0b111;
        int w; // next permutation of bits
        int t2 = (n | (n - 1)) + 1;
        w = t2 | ((((t2 & -t2) / (n & -n)) >> 1) - 1);
        Assert.assertEquals(w, 0b1011);
    }

    @Test(timeout = 30l, expected = org.junit.Test.None.class)
    public void testIterateThroughAllKElementSubsets() {
        // all subsets of size k from a set
        // http://stackoverflow.com/questions/17435030/how-to-find-all-possible-n-elements-subsets-of-a-set
        // http://stackoverflow.com/questions/4098248/how-to-generate-all-k-elements-subsets-from-an-n-elements-set-recursively-in-java
        // http://stackoverflow.com/questions/4504974/how-to-iteratively-generate-k-elements-subsets-from-a-set-of-size-n-in-java
        // {0, 1, … N-1}
        // 1. when N <= 32
        int N = 4;
        int k = 3;
        int s = (1 << k) - 1;
        int t = s & 1 << N;
        while (t == 0 ? true : false) {
            // do stuff with s
            int lo = s & ~(s - 1);       // lowest one bit
            int lz = (s + lo) & ~s;      // lowest zero bit above lo
            s = s |= lz;                     // add lz to the set
            s &= ~(lz - 1);              // reset bits below lz
            s |= (lz / lo / 2) - 1;      // put back right number of bits at end

            System.out.println(BitOperation.binaryFormat(s));
            t = s & 1 << N;
        }

        // 2.
    }

    @Test(timeout = 3l, expected = org.junit.Test.None.class)
    public void testAllSubset() {
        // https://www.quora.com/Given-an-array-of-size-n-how-do-you-find-all-the-possible-subsets-of-the-array-of-size-k
        // http://apps.topcoder.com/forums/?module=Thread&threadID=671150&start=0&mc=19
        // http://stackoverflow.com/questions/20357104/subset-sum-of-k-elemnts-in-o2k-2-time
        // http://stackoverflow.com/questions/25455184/finding-k-elements-of-length-n-list-that-sum-to-less-than-t-in-onlogk-time
        // http://stackoverflow.com/questions/127704/algorithm-to-return-all-combinations-of-k-elements-from-n
    }
}
