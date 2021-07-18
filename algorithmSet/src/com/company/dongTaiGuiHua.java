package com.company;
/**************************
 * 动态规划问题
 * 1.最长公共子串
 * 2.不相邻的最大子序列和
 * 3.最长的回文子串
 * 4.lc518 零钱兑换
 * 5.lc377 组合总数
 * 6.lc1143 最长公共子序列
 */



public class dongTaiGuiHua {

    ///////////////
    //1.最长公共子串
    //////////////
    //给定了两个字符串str1和str2,输出其最长公共子串
    //maxlen:记录最长字串的长度
    //index 最长字串的下标
    //在str1中找包含str2中的最长字串
    //1.解法一，没用动态规划，直接在str1中查
    public String LCS (String str1, String str2){
        if(str1==null||str2==null||str1.equals(" ")||str2.equals(" ")){
            return "-1";
        }
        int maxLen = 0;
        int index = 0;
        for(int i=0;i<str2.length();i++){
            for(int j=i+1;j<str2.length();j++){
                if(str1.contains(str2.substring(i,j))){//在str1中包含str2子串
                    if(maxLen<j-i){ //更新最大长度
                        maxLen = j-i;
                        index = i;
                    }
                }else {
                    break;
                }
            }
        }
        if(maxLen==0){ //没有找到，返回“-1”
            return "-1";
        }
        return str1.substring(index,index+maxLen);

    }
    //2，解法2 动态规划


    /////////////////////
    //2.不相邻的最大子序列和 (打家劫舍问题）
    //////////////////
    //给定一个n和一个长度为n的数组，在不同时选位置相邻的两个数的基础上，求该序列最大子序列和
    //其实就是偷东西问题，不能偷相邻两家，求能偷的最大金额
    //构建一个dp数组，dp[i]表示前i家我能偷的最大金额。对于当前元素array[i],我偷的话，
    //dp[i]=dp[i-2]+array[i];
    //我不偷的话 dp[i]=dp[i-1]
    public long subsequence (int n, int[] array){
        long[] dp = new long[n+1];
        dp[0]=0;
        dp[1]=1;
        for(int i=2;i<n;i++){
            dp[i] = Math.max(dp[i-1],dp[i-2]+array[i-1]);
        }
        return dp[n];
    }

    ///////////////
    //3.最长回文子串
    ////////////////
    //给定字符串A以及它的长度n，请返回最长回文子串的长度。
    //dp数组存储所经过的字符串是否是回文数
    public int getLongestPalindrome(String A, int n) {
        // write code here
        if(n==0) return 0;
        int max = 1;
        char[] arr = A.toCharArray();
        boolean[][] dp = new boolean[n][n]; //dp数组存储所经过的字符串是否是回文数
        dp[0][0]=true;
        for(int i=1;i<n;i++){
            dp[i][i]=true;
            dp[i][i-1]=true;
        }
        for(int i=1;i<n;i++){
            for(int j=0;j<i;j++){
                if(dp[j+1][i-1] && arr[i]==arr[j]){
                    //如果字串substring(i+1,j-1)是回文，如果string[i]=string[j]，那么substring(i,j)也是回文串。
                    dp[j][i]=true;
                    if(max<i-j+1){
                        max = i-j+1;
                    }
                }else{
                    dp[j][i]=false;
                }
            }
        }
        return max;
    }

    /////////////////////
    //4.lc518 零钱兑换2
    //元素排列位置不同，结果相同 - 组合问题
    //如果是组合，决策就只需要考虑哪个 coin（硬币）可用，所以 coin的遍历在外部
    //////////////////////
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }

    ///////////////////////
    //5.lc377组合总数
    //元素排列位置不同，结果不同 - 排列问题
    //如果是排列，决策需要考虑每个数字 i 用分别可以用哪些num（硬币），所以 i 的遍历在外部
    //////////////////////
    public int combinationSum4(int[] nums, int target) {
        //target-nums[i]可以在在nums中继续找
        //dp[i]表示数字为i时方案的数量
        //初始化dp[0]=1;
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 0; i <= target; i++) {
            for (int num : nums) {
                if (num <= i) dp[i] += dp[i - num];
            }
        }
        return dp[target];
    }

    /**
     * lc1143 最长公共子序列
     */
    public int longestCommonSubsequence(String text1, String text2){
        int M = text1.length();
        int N = text2.length();
        int[][] dp = new int[M+1][N+1];
        for(int i=1;i<=M;i++){
            for(int j=1;j<=N;j++){
                if(text1.charAt(i-1)==text2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[M][N];
    }






















































































}
