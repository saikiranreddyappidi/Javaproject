package Misc;

import java.util.SortedMap;
import java.util.TreeMap;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        SortedMap<Integer, Integer> map = new TreeMap<>();
        int[] result = new int[nums.length - k + 1];
        for (int i = 0; i < k; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        result[0] = map.lastKey();
        for (int i = k; i < nums.length; i++) {
            map.put(nums[i - k], map.get(nums[i - k]) - 1);
            if (map.get(nums[i - k]) == 0) {
                map.remove(nums[i - k]);
            }
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            result[i - k + 1] = map.lastKey();
        }
        return result;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] nums = {1,3,-1,-3,5,3,6,7};
        int k = 3;
        int[] result = s.maxSlidingWindow(nums, k);
        for (int i : result) {
            System.out.println(i);
        }
    }
}