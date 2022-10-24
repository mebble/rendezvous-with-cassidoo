const sum = (nums) => nums.reduce((acc, x) => acc + x, 0);

function largestSubarraySum(nums, len) {
    return nums.reduce((longest, x, i) => {
        const window = nums.slice(i, i + len);
        if (window.length != len) return longest;
        if (sum(window) > sum(longest)) return window;
        return longest;
    }, []);
}

console.log(largestSubarraySum([3,1,4,1,5,9,2,6], 3))
