import java.util.Arrays;
import java.math.BigInteger;

// Main Observation: Array sums are invariant

class Solution {
    public boolean canTransform(int[] source, int[] target) {
        BigInteger[] sourceArray = Arrays.stream(source).mapToObj(BigInteger::valueOf).toArray(BigInteger[]::new);

        BigInteger[] targetArray = Arrays.stream(target).mapToObj(BigInteger::valueOf).toArray(BigInteger[]::new);

        return Arrays.stream(sourceArray).reduce(BigInteger.ZERO, BigInteger::add).equals(Arrays.stream(targetArray).reduce(BigInteger.ZERO, BigInteger::add));
    }
}