// Observation: When there is an element x negated, every number on it's left are persists while every number on it's right reduced by 2x.
// The search for furthest match in prefix remainders after negation can be done by fixing the leftmost number, picking an anchor to be negated, and the rightmost element which will match after the anchor's negation.
// There are k ways to pick the leftmost modulo k prefix, while k ways to pick anchors modulo k, resulting in an O(k^2) loop.
// Precompute in O(n) to make sure everything needed in the loop can be received in O(1).

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;

class Solution {
    public int longestSubarray(int[] nums, int k) {
        if (nums.length == 1){
            if (nums[0] % k == 0){
                return 1;
            } else {
                return 0;
            }
        }

        int ans = 0;

        // modulo all nums
        for (int i=0; i<nums.length; i++){
            nums[i] = ((nums[i] % k) + k) % k;
        }

        // store all appearances of nums by it's modulo (1-indexed)
        Map<Integer, List<Integer>> nums_appearances = new HashMap<Integer, List<Integer>>();
        for(int i=0; i<nums.length;  i++){
            if (nums_appearances.containsKey(nums[i])){
                nums_appearances.get(nums[i]).add(i+1);
            } else {
                nums_appearances.put(nums[i], new ArrayList<Integer>(List.of(i+1)));
            }
        }

        // create prefix sum
        int[] prefix = new int[nums.length + 1];
        prefix[0] = 0;
        for (int i=1; i<=nums.length; i++){
            prefix[i] = (((prefix[i-1] + nums[i-1]) % k) + k) % k;
        }

        // store first appearences of prefix reminders (preserving insertion order)
        Map<Integer, Integer> first_prefix = new LinkedHashMap<Integer, Integer>();
        for(int i=0; i<prefix.length;  i++){
            if (!first_prefix.containsKey(prefix[i])){
                first_prefix.put(prefix[i], i);
            }
        }

        // store last appearences of prefix reminders
        Map<Integer, Integer> last_prefix = new HashMap<Integer, Integer>();
        for(int i=prefix.length-1; i>-1;  i--){
            if (!last_prefix.containsKey(prefix[i])){
                last_prefix.put(prefix[i], i);
            }
        }

        // no negation case
        Map<Integer, int[]> prefix_intervals = new HashMap<Integer, int[]>();
        for(int i=0; i<prefix.length;  i++){
            if (prefix_intervals.containsKey(prefix[i])){
                prefix_intervals.get(prefix[i])[1] = i - prefix_intervals.get(prefix[i])[0];
            } else {
                prefix_intervals.put(prefix[i], new int[]{i, 0});
            }
        }
        for (int i=0; i<k; i++){
            if (prefix_intervals.containsKey(i)){
                ans = Math.max(ans, prefix_intervals.get(i)[1]);
            }
        }

        // negation loop
        for (int i=0; i<k; i++){ // anchor
            if (!nums_appearances.containsKey(i)){
                continue;
            }

            List<Integer> appearances = nums_appearances.get(i);
            int anchor_index = 0;

            for (int j : first_prefix.keySet()){ // leftmost
                int first = first_prefix.get(j);

                // move anchor forward until it is after first
                while (anchor_index < appearances.size() &&
                    appearances.get(anchor_index) <= first){
                    anchor_index++;
                }
                if (anchor_index >= appearances.size()){
                    break;
                }

                int target = (((j + 2 * i) % k) + k) % k;
                int last = 0;

                if (last_prefix.containsKey(target)){
                    last = last_prefix.get(target);
                } else {
                    continue;
                }

                int anchor_position = appearances.get(anchor_index);

                if (first >= anchor_position || anchor_position > last) {
                    continue;
                }

                ans = Math.max(ans, last - first);
            }
        }
        return ans;
    }
}