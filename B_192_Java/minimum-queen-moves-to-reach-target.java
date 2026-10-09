// Check how many horizontal/vertical moves to be done, up to a maximum of 2, but answer is 1 if it's already on the diagonal

class Solution {
    public int minQueenMoves(int[] source, int[] target) {
        int ans = 2;
        if (source[0] == target[0]){
            ans--;
        }
        if (source[1] == target[1]){
            ans--;
        }
        if (ans == 0){
            return 0;
        } else if (Math.abs(source[0] - target[0]) == Math.abs(source[1] - target[1])){
            return 1;
        } else {
            return ans;
        }
    }
}