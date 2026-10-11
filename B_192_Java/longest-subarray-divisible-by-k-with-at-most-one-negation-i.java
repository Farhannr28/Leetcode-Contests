import java.util.HashMap;
import java.util.Map;

// Subarrays divisible by k means the 2 prefix sums are equal modulo k
// Iterating over elements in O(n), then negate then looking for equal prefix indexes in O(n), is still sufficient for nums.length <= 1000

class Solution {
    public int findLongestSubarray(int[] nums, int k){
        int[] prefix = new int[nums.length + 1];
        prefix[0] = 0;
        for (int i=1; i<=nums.length; i++){
            prefix[i] = (((prefix[i-1] + nums[i-1]) % k) + k) % k;
        }
        Map<Integer, int[]> map = new HashMap<Integer, int[]>();
        for (int i=0; i<=nums.length; i++){
            if (map.containsKey(prefix[i])){
                int[] pair = map.get(prefix[i]);
                pair[1] = i - pair[0];
            } else {
                map.put(prefix[i], new int[]{i, 0});
            }
        }
        Map.Entry<Integer, int[]> maxi = null;
        for (Map.Entry<Integer, int[]> entry : map.entrySet()){
            if (maxi == null || entry.getValue()[1] > maxi.getValue()[1]){
                maxi = entry;
            }
        }
        return maxi.getValue()[1];
    }

    public int longestSubarray(int[] nums, int k) {
        if (nums.length == 1){
            if (nums[0] % k == 0){
                return 1;
            } else {
                return 0;
            }
        }
        int ans = findLongestSubarray(nums, k);    
        for (int i=0; i<nums.length; i++){
            nums[i] = 0 - nums[i];
            ans = Math.max(ans, findLongestSubarray(nums, k));
            nums[i] = 0 - nums[i];
        }
        return ans;
    }
}