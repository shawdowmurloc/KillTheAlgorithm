package com.company;

import java.util.*;

/**
 //lc6 1.字符串变换
 //剑指offer 2.股票的最大利润
 //lc15 3.三数之和
 //lc290 单词规律
 //lc2 两数相加
 //lc11 盛水最多的容器
 //lc202 快乐数
 //两个队列实现栈功能
 //两个栈实现队列
 //lc1047 删除字符串中的所有相邻重复项
 //lc27 移除元素
 //lc209 长度最小的子数组
 //剑指offer59 滑动窗口的最大值
 //lc 41 缺失的第一个正数
 //lc 485 最大连续1的个数
 //剑指offer03 数组中重复的数字
 //lc 7 整数反转
 //lc 219 数组中的重复元素2
 //lc560 和为k的子数组
 //lc 66 加1
 //lc 75 颜色分类
 //lc54 螺旋矩阵
 //lc59 螺旋矩阵2
 //lc 724 寻找数组的中心下标
 //lc1248 统计优美子数组
 //lc62 不同路径
 //字符串比较BF算法
 //lc 217 存在重复元素
 //lc204 计数质数
 //lc5 最长回文子串
 //lc90 子集2
 //lc227 基本计算器2
 //lc322 零钱兑换
 //lc 238 除自身外数组的乘积
 //lc279 完全平方数
 //lc1006 笨阶乘
 //lc139 单词拆分
 //lc371 两整数之和
 //lc136 只出现一次的数字
 //lc73 矩阵置零
 //直方图的水量
 //lc155 最小栈
 //lc53 最大子序和
 lc152 乘积最大的子数组
 lc169 多数元素
 lc300 最长递增子序列
 lc647 回文子串
 lc1143 最长公共子序列
 lc64 最小路径和
 lc78 子集
 lc198 打家劫舍问题
 lc454 四数相加
 lc121 买卖股票的最佳时间
 lc387 字符串中的第一个唯一的字符
 lc134加油站
 lc237 原地删除节点
 lc88 合并有序数组
 lc230 中序遍历 二叉搜索树中的第k小的元素










 */
public class Others {
    //lc 6 1.Z字形变换
    public String convert(String s, int numRows){
        if(numRows<2) return s;
        List<StringBuilder> rows = new ArrayList<StringBuilder>();
        //加入numRows个StringBuilder
        for(int i=0;i<numRows;i++){
            rows.add(new StringBuilder());
        }
        char[] sCharArray = s.toCharArray();
        int i=0;
        int flag = -1;
        for(char c:sCharArray){
            rows.get(i).append(c);
            //在第一行或者是最后一行，其实也就是z的拐弯处，flag置为反
            if(i==0||i==numRows-1){
                flag = -flag;
            }
            i+=flag;
        }
        StringBuilder res = new StringBuilder();
        for (StringBuilder row : rows){
            res.append(row);
        }
        return  res.toString();
    }

    /////////////
    //2.股票的最大利润
    //////////////
    //动态规划问题
    //dp[i]定义为以prices[i]为结尾的子数组的最大利润（前日最大利润）
    //前i日最大利润为Math.max(前dp[i-1]日最大利润和第i日价格-前i日最低价格)
    public int maxProfit(int[] prices){
        if(prices.length==0) return 0;
        int[]dp = new int[prices.length];
        dp[0] = 0;
        int min = prices[0];
        for(int i=1;i<prices.length;i++){
            if(prices[i]<min) min = prices[i];
            dp[i]  = Math.max(dp[i-1],prices[i]-min);
        }
        return dp[prices.length-1];
    }


    //////////////
    //3.三数之和
    ////////////
    public List<List<Integer>> threeSum(int[] nums) {
        //先排序数组
        Arrays.sort(nums);
        List<List<Integer>> result = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            //第一个数就大于0，肯定求不出
            if (nums[i] > 0) {
                break;
            }
            //排除重复
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            //定义左右双指针
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                //求和
                int threeSum = nums[i] + nums[left] + nums[right];
                if (threeSum < 0) {
                    left++;
                } else if (threeSum > 0) {
                    right--;
                } else {//求到和为0，把数字放入list
                    LinkedList<Integer> res = new LinkedList<>();
                    res.add(nums[i]);
                    res.add(nums[left]);
                    res.add(nums[right]);
                    //加入结果list
                    result.add(res);
                    //排除重复
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    //排除重复
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    //左移左指针。右移右指针
                    right--;
                    left++;
                }
            }
        }
        return result;
    }
    ///////////
    //4.单词规律
    ////////////
    public boolean wordPattern(String pattern, String s){
        //解决方法重点在于两个字符串的下标一一对应
        //其次是利用put的返回值
        String[] strings = s.split("");
        if(pattern.length()!=s.length()){
            return false;
        }
        HashMap<Object, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++){
            if(!Objects.equals(hashMap.put(pattern.charAt(i), i), hashMap.put(strings[i], i))){
                return false;
            }
        }
        return true;
    }

    //////////
    //5.两数相加 链表
    /////////
    public ListNode addTwoNumbers(ListNode l1, ListNode l2){
        ListNode pre  = new ListNode(0);
        ListNode cur = pre;
        int carry =0;
        while(l1!=null || l2!=null){
            int x = l1==null?0:l1.val;
            int y = l2==null?0:l2.val;
            int sum = x+y+carry;
            carry  = sum/10;
            sum = sum%10;
            cur.next = new ListNode(sum);
            cur = cur.next;
            if(l1!=null){
                l1 = l1.next;
            }
            if(l1!=null){
                l2 = l2.next;
            }
        }
        if(carry==1){
            cur.next = new ListNode(carry);
        }
        return pre.next;
    }
    //法2
    //对于链表问题，返回结果为头结点时，可以构造一个初始指针,该指针的下一个节点指向真正的头结点
    //使用初始节点的目的在于链表初始化时无可用节点值，而且链表构造过程中需要指针移动
    //进而导致头结点丢失，无法返回结果
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2){
        ListNode res = new ListNode(0);
        ListNode cursor = res;
        //进位
        int carry = 0;
        while(l1 !=null || l2 != null || carry !=0){
            int l1val = l1 !=null? l1.val:0;
            int l2val = l2 !=null? l2.val:0;
            int sum = l1val +l2val + carry;
            carry = sum/10;
            ListNode newNode = new ListNode(sum%10);
            cursor.next = newNode;
            cursor = newNode;
            if(l1 != null) l1 = l1.next;
            if(l2 != null) l2 = l2.next;

        }
        return res.next;
    }

    //////////
    //lc11 盛水最多的容器
    //////////////
    public int maxArea(int[] height){
        int res = 0;
        int left = 0;//起始索引
        int right = height.length-1; //末尾索引
        while(left<right){
            //短板效应
            int area  = (right-left) * Math.min(height[left],height[right]);
            res = Math.max(res,area);
            if(height[left]<height[right]){
                left++;
            }else{
                right--;
            }
        }
        return res;

    }

    //////////////
    //两个队列实现栈功能
    //////////////
    //初始化队列
    Queue<Integer> queue;
    //    public MyStack() {
//        queue = new LinkedList<>();
//    }
    //模拟入栈操作
    public void push(int x) {
        queue.offer(x);
        //将之前的全部都出队，然后再入队
        for(int i = 1;i<queue.size();i++){
            queue.offer(queue.poll());
        }
    }
    //模拟出栈
    public int pop() {
        return queue.poll();

    }

    //返回栈顶元素
    public int top() {
        return queue.peek();

    }
    //判断是否为空
    public boolean empty() {
        return queue.isEmpty();

    }

    ////////////
    //两个栈实现队列
    ///////////
    Stack<Integer> stack1,stack2;
    //    public CQueue() {
//        stack1 = new Stack<>();
//        stack2 = new Stack<>();
//
//    }
    //入队，我们往第一个栈压入值
    public void appendTail (int value) {
        stack1.push(value);
    }
    //出队
    public int deleteHead() {
        //大家可以自己思考一下为什么if条件为stack2.isEmpty(),细节所在
        if (stack2.isEmpty()) {
            //如果此时A栈没有值，则直接-1，我们可以看示例
            if (stack1.isEmpty()) {
                return -1;
            }
            //将A栈的值，压入B栈中
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    ////////////////
    //lc1047 删除字符串中的所有相邻重复项
    //////////////////
    public String removeDuplicates(String S){
        char[] sChar = S.toCharArray();
        Stack<Character> stack1 = new Stack<Character>();
        if(S.length()==0||S.length()==1){
            return S;
        }
        for(int i=0;i<sChar.length;i++){
            if(stack1.isEmpty()||sChar[i]!=stack1.peek()){
                stack1.push(sChar[i]);
            }else{
                stack1.pop();
            }
        }
        StringBuilder str = new StringBuilder();
        while (!stack1.isEmpty()) {
            str.append(stack1.pop());
        }
        //翻转字符并返回
        return str.reverse().toString();



    }

    //////////////
    //lc27 移除元素
    //////////////
    public int removeElement(int[] nums, int val){
        int len = nums.length;
        int i=0;//计数器
        if(len==0) return 0;
        for(int j=0;j<len;j++){
            if(nums[j]==val){
                continue;
            }
            nums[i++] = nums[j];
        }
        return i;
    }

    //////////
    //lc209 长度最小子数组
    //////////
    //滑动窗口来解决问题
    public int minSubArrayLen(int target, int[] nums){
        int len = nums.length;
        int i=0;
        int windowsLength = Integer.MAX_VALUE;
        int sum = 0;
        for(int j=0;j<len;j++){
            sum += nums[j];
            while (sum>=target){
                windowsLength = Math.min(windowsLength,j-i+1);
                sum -= nums[i];
                i++;
            }

        }
        return windowsLength==Integer.MAX_VALUE?0:windowsLength;

    }

    ////////////
    //滑动窗口的最大值
    //////////////
    public int[] maxSlidingWindow(int[] nums, int k){
        int len = nums.length;
        //边界条件
        if(len==0){
            return nums;
        }
        //结果返回数组
        int[] arr = new int[len-k+1];
        //结果数组索引
        int arr_index = 0;
        //维护一个单调递增的双向队列，这样最大值只要把队头拿出来就好了
        Deque<Integer> deque  = new LinkedList<>();
        for(int i=0;i<k;i++){
            while (!deque.isEmpty()&&deque.peekLast()<nums[i]){
                deque.removeLast();
            }
            //把第一个窗口所有值加入arr
            deque.offerLast(nums[i]);
        }
        //放入第一窗口中的最大值
        arr[arr_index++] = deque.peekFirst();

        for(int j=k;j<len;j++){
            if(nums[j-k]==deque.peekFirst()){
                deque.removeFirst();
            }
            while (!deque.isEmpty()&&deque.peekLast()<nums[j]){
                deque.removeLast();
            }
            deque.offerLast(nums[j]);
            arr[arr_index++] = deque.peekFirst();
        }
        return arr;
    }

    /**
     * lc41 缺失的第一个正数
     * 利用辅助数组的方法
     */
    public int firstMissingPositive(int[] nums){
        if(nums.length==0) return 1;
        //因为是返回第一个正整数，不包括 0，所以需要长度加1，细节1
        int[] res = new int[nums.length+1];
        //将数组元素添加到辅助数组中
        for(int x:nums){
            if(x>0 && x<nums.length){
                res[x] = x;
            }
        }
        //遍历查找,发现不一样时直接返回
        for(int i=1;i<res.length;i++){
            if(res[i]!=i){
                return i;
            }
        }
        //缺少最后一个，例如 1，2，3此时缺少 4 ，细节2
        return res.length;
    }
    /**
     * lc485 最大连续1的个数
     */
    public int findMaxConsecutiveOnes(int[] nums){
        int count = 0;
        int maxCount = 0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]==1){
                count++;
            }else{
                maxCount = Math.max(count,maxCount);
                count = 0;
            }
        }
        return Math.max(count,maxCount);
    }
    /**
     * 剑指offer 数组中重复的数字
     */
    //HashSet方法
    public int findRepeatNumber(int[] nums){
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i=0;i<nums.length;i++){
            if(set.contains(nums[i])){
                return nums[i];
            }
            set.add(nums[i]);
        }
        return -1;
    }
    //原地置换法
    //意思就是将我们指针对应的元素放到属于它的索引对应的地方
    //可以理解为每个人都有自己的位置，我们需要和别人换座才找到自己的位置
    //换座以后发现位置上有人了则返回
    public int findRepeatNumber2(int[] nums){
        if(nums.length==0) return -1;
        for(int i=0;i<nums.length;i++){
            while (nums[i]!=i){
                if(nums[i]==i){
                    return nums[i];
                }
                //置换
                int temp = nums[i];
                nums[i] = nums[temp];
                nums[temp] = temp;
            }
        }
        return -1;
    }
    /**
     * lc7 整数反转
     */
    public int reverse(int x) {
        int y=0;
        while(x!=0){
            if(y>2147483647||y<-2147483647) return 0;
            y = y*10+x%10;
            //x%10求得个位数
            x = x/10;
        }
        return y;

    }
    /**
     * 回文数字
     */
    public boolean isPalindrome(int x){
        if(x<0) return false;
        long y=0; //防止溢出
        int flag = x; //留一个做比较
        while(x!=0){
            y = y*10+x%10;
            x = x/10;
        }
        return y==flag;
    }
    /**
     * lc219 数组中的重复元素
     *
     */
    public boolean containsNearbyDuplicate(int[] nums, int k){
        HashMap<Integer,Integer> map = new HashMap<>();
        if(nums.length==0) return false;
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(nums[i])){
                int abs = Math.abs(i-map.get(nums[i]));
                if(abs<=k) return true;
            }
            map.put(nums[i],i);
        }
        return false;
    }
    /**
     * lc560 和为k的子数组
     *
     */
    //暴力法
    public int subarraySum(int[] nums, int k){
        int count =0;
        int sum = 0;
        int len = nums.length;
        for(int i=0;i<len;i++){
            for(int j=i;j<len;j++){
                sum += nums[j];
                if(sum==k) count++;
            }
            sum=0;
        }
        return count;
    }
    //前缀和
    public int subarraySum2(int[] nums, int k) {
        //前缀和数组
        int[] presum = new int[nums.length+1];
        for (int i = 0; i < nums.length; i++) {
            //这里需要注意，我们的前缀和是presum[1]开始填充的
            presum[i+1] = nums[i] + presum[i];
        }
        //统计个数
        int count = 0;
        for (int i = 0; i < nums.length; ++i) {
            for (int j = i; j < nums.length; ++j) {
                //注意偏移，因为我们的nums[2]到nums[4]等于presum[5]-presum[2]
                //所以这样就可以得到nums[i,j]区间内的和
                if (presum[j+1] - presum[i] == k) {
                    count++;
                }
            }
        }
        return count;
    }

    //前缀和加hashmap
    public int subarraySum3(int[] nums, int k){
        if(nums.length==0)return 0;
        HashMap<Integer,Integer> map1 = new HashMap<>();
        map1.put(0,1); //*****注意
        //细节，这里需要预存前缀和为 0 的情况，会漏掉前几位就满足的情况
        //例如输入[1,1,0]，k = 2 如果没有这行代码，则会返回0,漏掉了1+1=2，和1+1+0=2的情况
        //输入：[3,1,1,0] k = 2时则不会漏掉
        //因为presum[3] - presum[0]表示前面 3 位的和，所以需要map.put(0,1),垫下底
        int count = 0;
        int presum = 0;
        for(int x:nums){
            presum += x;
            //当前前缀和已知，判断是否含有 presum - k的前缀和，那么我们就知道某一区间的和为 k 了。
            if(map1.containsKey(presum-k)){
                count += map1.get(presum-k); //获取presum-k前缀和出现次数
            }
            //更新
            map1.put(presum,map1.getOrDefault(presum,0)+1);
        }
        return count;
    }


    /**
     * lc66 加1
     */
    public int[] plusOne(int[] digits){
        int len = digits.length;
        for (int i=len-1;i>=0;i--){
            digits[i] = (digits[i]+1)%10;
            if(digits[i]!=0){
                return digits;
            }
        }
        //[9,9,9,9]-->[1,0,0,0,0]
        int[] arr = new int[len+1];
        arr[0] = 1;
        return arr;
    }


    /**
     * lc75 颜色分类
     */
    public void sortColors(int[] nums) {
        int len = nums.length;
        int left = 0;
        //这里和三向切分不完全一致
        int i = left;
        int right = len-1;

        while (i <= right) {
            if (nums[i] == 2) {
                swap(nums,i,right--);
            } else if (nums[i] == 0) {
                swap(nums,i++,left++);
            } else {
                i++;
            }
        }
    }
    public void swap (int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * lc54 螺旋矩阵
     */
    public List<Integer> spiralOrder(int[][] matrix){
        List<Integer> arr = new ArrayList<>();
        int left = 0,right = matrix[0].length-1;
        int top = 0,bottom = matrix.length-1;
        while(true){
            //遍历一行 从左往右
            for(int i=left;i<=right;i++){
                arr.add(matrix[top][i]);
            }
            top++; //上标记往下移
            if(top>bottom) break;

            //遍历一列，从上到下
            for(int i=top;i<=bottom;i++){
                arr.add(matrix[i][right]);
            }
            right--; //右标记往左移
            if(left>right) break;

            //遍历一行，从右往左
            for(int i=right;i>=left;i--){
                arr.add(matrix[bottom][i]);
            }
            bottom--; //下标记往上移
            if(top>bottom) break;

            //遍历一列，从下到上
            for(int i=bottom;i>=top;i--){
                arr.add(matrix[i][left]);
            }
            left++; //左标记往右移
            if(left>right) break;
        }
        return arr;
    }

    /**
     *lc59 螺旋矩阵2
     */
    public int[][] generateMatrix(int n) {
        int[][] arr = new int[n][n];
        int left = 0;
        int right = n-1;
        int top = 0;
        int buttom = n-1;
        int num=1;
        int numsize = n*n;
        while (true){
            for(int i=left;i<=right;i++){
                arr[top][i] = num++;
            }
            top++;
            if(num>numsize) break;


            for (int i = top; i <= buttom; ++i) {
                arr[i][right] = num++;
            }
            right--;
            if (num > numsize) break;


            for (int i = right; i >= left; --i) {
                arr[buttom][i] = num++;
            }
            buttom--;
            if (num > numsize) break;


            for (int i = buttom; i >= top; --i) {
                arr[i][left] = num++;
            }
            left++;
            if (num > numsize) break;
        }
        return arr;
    }

    /**
     * lc 724 寻找数组的中心下标
     */
    public int pivotIndex(int[] nums){
        int sum = 0;
        for(int x:nums){
            sum+=x;
        }
        int leftsum = 0;
        for (int i=0;i<nums.length;i++){
            if(leftsum==sum-nums[i]-leftsum){
                return i;
            }
            leftsum += nums[i];
        }
        return -1;
    }

    /**
     * lc1248 统计优美子数组
     */
    public int numberOfSubarrays(int[] nums, int k){
        if(nums.length==0) return 0;
        HashMap<Integer,Integer> map = new HashMap<>();
        //计数器
        int oddnum = 0;
        int count = 0;
        map.put(0,1);
        for(int x:nums){
            oddnum += x&1; //位运算，奇数&1 = 1；偶数&1 = 0；
            if(map.containsKey(oddnum-k)){
                count += map.get(oddnum-k);

            }
            map.put(oddnum,map.getOrDefault(oddnum,0)+1);
        }
        return count;

    }

    /**
     * lc62 不同路径
     * 动态规划方法
     * dp[i][j]是到达i,j最多的路径
     * * dp[i][j] = dp[i-1][j]+dp[i][j-1]
     * 对于第一行护着第一列，由于是在边界，所以只能为1
     */
    public int uniquePaths(int m, int n){
        int[][] dp = new int[m][n];
        for(int i=0;i<n;i++) dp[0][i] = 1;
        for(int j=0;j<m;j++) dp[j][0] = 1;
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                dp[i][j] = dp[i-1][j]+dp[i][j-1];
            }
        }
        return dp[m-1][n-1];

    }

    /**
     * 字符串比较BF算法
     * Brute Force
     * 将模式串与主串进行比较，一致时则继续比较下一个字符，直到比较完整个字符串
     * 不一致时则将模式串的首位开始对比，重复刚才的步骤
     */
    public int strStr(String haystack, String needle) {
        int haylen = haystack.length();
        int needlen = needle.length();
        if(haylen<needlen){
            return -1;
        }
        if(needlen==0){
            return 0;
        }
        //主串
        for(int i=0;i<haylen-needlen+1;i++){
            int j;
            //模式串
            for(j=0;j<needlen;j++){
                //不符合的情况，直接跳出，主串指针后移一位
                if(haystack.charAt(i+j)!=needle.charAt(j)){
                    break;
                }
            }
            if(j==needlen){
                return i;
            }
        }
        return -1;
    }

    /**
     * lc217 存在重复元素
     */
    public boolean containsDuplicate(int[] nums) {

        if(nums.length==0) return false;
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(nums[i])){
                return true;
            }
            map.put(nums[i],i);
        }
        return false;
    }

    /**
     * lc204 计数质数
     */
    public int countPrimes(int n){
        /**
         * 埃式塞法
         * 初始化长度为O(n)的标记数组，表示这个数组是否为质数，数组初始化所有的数都是质数、
         * 从2开始将当前数字的倍数全都标为合数，标记到根号n停止即可
         * 注意每次找当前素数x的倍数时是从x平方开始，因为如果x>2,那么2*x肯定被素数2给过滤了
         * 最小未被过滤的肯定是x平方
         */
        boolean[] isPrim = new boolean[n];
        Arrays.fill(isPrim, true);
        // 从 2 开始枚举到 sqrt(n)。
        for (int i = 2; i * i < n; i++) {
            // 如果当前是素数
            if (isPrim[i]) {
                // 就把从 i*i 开始，i 的所有倍数都设置为 false。
                for (int j = i * i; j < n; j+=i) {
                    isPrim[j] = false;
                }
            }
        }
        // 计数
        int cnt = 0;
        for (int i = 2; i < n; i++) {
            if (isPrim[i]) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * lc5 最长回文子串
     * 给定一个字符串s，找到s中最长的回文子串
     */
    /**
     * 1.直接法
     */
    public String longestPalindrome(String s){
        int len = s.length();
        if (len < 2) {
            return s;
        }
        int maxLen = 1;
        int begin = 0;
        // s.charAt(i) 每次都会检查数组下标越界，因此先转换成字符数组
        char[] charArray = s.toCharArray();
        // 枚举所有长度大于 1 的子串 charArray[i..j]
        for (int i = 0; i < len - 1; i++){
            for (int j = i + 1; j < len; j++){
                if (j - i + 1 > maxLen && validPalindromic(charArray, i, j)){
                    maxLen = j-i+1;
                    begin = i;
                }
            }
        }
        return s.substring(begin,begin+maxLen);

    }
    //私有方法，判断子串是否是回文串
    private boolean validPalindromic(char[] charArray, int left, int right){
        while (left<right){
            if (charArray[left] != charArray[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /**
     *解法2 动态规划
     */

    public String longestPalindrome2(String s) {
        // 特判
        int len = s.length();
        if (len < 2) {
            return s;
        }

        int maxLen = 1;
        int begin = 0;

        // dp[i][j] 表示 s[i, j] 是否是回文串
        boolean[][] dp = new boolean[len][len];
        char[] charArray = s.toCharArray();

        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }
        for (int j = 1; j < len; j++) {
            for (int i = 0; i < j; i++) {
                if (charArray[i] != charArray[j]) {
                    dp[i][j] = false;
                } else {
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                // 只要 dp[i][j] == true 成立，就表示子串 s[i..j] 是回文，此时记录回文长度和起始位置
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    /**
     * lc90 子集2
     */

    private List<List<Integer>> ans;
    private List<Integer> path;

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        ans = new ArrayList<>();
        path = new ArrayList<>();
        // 首先排序，让相同的两个元素排到一起去，便于去重
        Arrays.sort(nums);
        int n = nums.length;
        // 使用 visited 数组来记录哪一个元素在当前路径中被使用了
        boolean[] visited = new boolean[n];
        // 开始回溯
        backtrace(nums, 0, visited, n);
        return ans;
    }

    private void backtrace(int[] nums, int start, boolean[] visited, int n) {
        // 首先加入当前路径
        ans.add(new ArrayList<>(path));
        // 从 start 开始遍历每一个元素，尝试加入路径中
        for (int i = start; i < n; ++i) {
            // 如果当前元素和前一个元素相同，而且前一个元素没有被访问，说明前一个相同的元素在当前层已经被用过了
            if (i > 0 && nums[i - 1] == nums[i] && !visited[i - 1]) continue;
            // 记录下来，用过了当前的元素
            visited[i] = true;
            path.add(nums[i]); // 放到路径中
            backtrace(nums, i + 1, visited, n); // 向下一个递归
            visited[i] = false; // 回溯
            path.remove(path.size() - 1);
        }
    }
    /**
    *lc227 基本计算器2
     */
    public int calculate(String s) {
        int ans = 0;
        int preSign = 1;

        int n = s.length();
        int temp = 0;
        int sign = 1;

        // 整体思路，先计算乘除法，再计算加减法
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '+') {
                // 结算前面的部分
                ans += preSign*temp;
                sign = 1;
                preSign = 1;
            } else if (c == '-') {
                // 结算前面的部分
                ans += preSign*temp;
                sign = -1;
                preSign = -1;
            } else if (c == '*') {
                sign = 2;
            } else if (c == '/') {
                sign = 3;
            } else if (Character.isDigit(c)) {
                int num = c - '0';
                while (i + 1 < n && Character.isDigit(c = s.charAt(i + 1))) {
                    num = num * 10 + c - '0';
                    i++;
                }
                // 乘除法优先级更高，遇到直接算
                if (sign == 2) {
                    temp *= num;
                } else if (sign == 3) {
                    temp /= num;
                } else {
                    temp = num;
                }
            }
        }
        ans += preSign*temp;
        return ans;
    }

    /**
     * 用栈实现
     * 计算器乘除的优先级一定大于加减的优先级
     * 可以利用一个栈，先保存进行乘除后的整数值，对于加减号后的数字，
     * 将其直接压入栈中，对于乘除号后的数组，可以直接与栈顶元素计算并替换栈顶元素作为计算值
     *遍历字符串s,用变量preSign记录每个数字之间的运算符，对于第一个数字，
     * 其之前的运算符视为加号，每次遍历到数字末尾时，根据preSign来决定计算方式
     */
    public int calculate2(String s){
        Deque<Integer> stack = new LinkedList<Integer>();
        char preSign = '+';
        int num = 0;
        int n = s.length();
        for(int i=0;i<n;i++){
            if(Character.isDigit(s.charAt(i))){
                num = num*10+s.charAt(i)-'0';
            }
            if (!Character.isDigit(s.charAt(i)) && s.charAt(i) != ' ' || i == n - 1){
                switch (preSign){
                    case '+':
                        stack.push(num);
                        break;

                    case '-':
                        stack.push(-num);
                        break;

                    case '*':
                        stack.push(stack.pop()*num);
                        break;

                    default:
                        stack.push(stack.pop()/num);
                }
                preSign = s.charAt(i);
                num = 0;
            }
        }
        int ans = 0;
        while (!stack.isEmpty()){
            ans += stack.pop();
        }
        return ans;

    }

    /**
     * lc322 零钱兑换
     */
    public int coinChange(int[] coins, int amount) {
        // 自底向上的动态规划
        if(coins.length == 0){
            return -1;
        }
        // memo[n]的值： 表示的凑成总金额为n所需的最少的硬币个数
        int[] memo = new int[amount+1];
        // 给memo赋初值，最多的硬币数就是全部使用面值1的硬币进行换
        // amount + 1 是不可能达到的换取数量，于是使用其进行填充
        Arrays.fill(memo,amount+1);
        memo[0] = 0;
        for(int i = 1; i <= amount;i++){
            for(int j = 0;j < coins.length;j++){
                if(i - coins[j] >= 0){
                    // memo[i]有两种实现的方式，
                    // 一种是包含当前的coins[i],那么剩余钱就是 i-coins[i],这种操作要兑换的硬币数是 memo[i-coins[j]] + 1
                    // 另一种就是不包含，要兑换的硬币数是memo[i]
                    memo[i] = Math.min(memo[i],memo[i-coins[j]] + 1);
                }
            }
        }

        return memo[amount] == (amount+1) ? -1 : memo[amount];
    }

    /**
     * lc238 除自身以外数组的乘积
     * 给一个长度为n的数组nums，返回输出数组output
     * 其中output[i]等于num中除nums[i]之外所有元素的乘积
     */
    public int[] productExceptSelf(int[] nums){
        int[] res = new int[nums.length];
        int k = 1;
        for(int i=0;i<nums.length;i++){
            res[i] = k;
            k *=nums[i];// 此时数组存储的是除去当前元素左边的元素乘积
        }
        k = 1;
        for (int j = nums.length-1;j>=0;j--){
            res[j] *= k; //此时k为该数右边的乘积
            k *=nums[j]; //此时数组等于该数左边的*该数右边的
        }
        return res;

    }
    //思路同上
    public int[] productExceptSelf2(int[] nums) {
        int n = nums.length;
        if(n == 0){
            return null;
        }
        // preProduct[i] 指 i之前乘积 不包括i  afterProduct[i] 指i之后 不包括i
        int[] preProduct = new int[n] , afterProduct = new int[n];
        preProduct[0] = 1;
        afterProduct[n - 1] = 1;
        for(int i = 1 ; i < n ; i++){
            preProduct[i] = preProduct[i - 1] * nums[i - 1];
        }

        for(int i = n - 2 ; i >= 0 ; i--){
            afterProduct[i] = afterProduct[i + 1] * nums[i + 1];
        }

        for(int i = 0 ; i < n ; i++){
            nums[i] = preProduct[i] * afterProduct[i];
        }
        return nums;
    }

    /**
     * lc279 完全平方数
     */
    //暴力解法
    public int numSquares(int n){
        return numSquaresHelper(n);

    }
    private int numSquaresHelper(int n){
        if(n==0) return 0;
        int count = Integer.MAX_VALUE;
        //依次减去一个平方数
        for(int i=1;i*i<=n;i++){
            count = Math.min(count,numSquaresHelper(n-i*i)+1);
        }
        return count;
    }
    /**
     * 法2
     */
    int memo[];
    public int numSqua(int n){
        memo = new int[n+1];
        return numS(n);
    }
    private int numS(int n){
        if(memo[n]!=0) return memo[n];
        int val = (int) Math.sqrt(n);
        if(val*val==n){
            return memo[n]=1;
        }
        int res = Integer.MAX_VALUE;
        for(int i=1;i*i<n;i++){
            res = Math.min(res,numS(n-i*i)+1);
        }
        return memo[n] = res;
    }
    public int numSquares2(int n) {
        int[] memo = new int[n+1];
        for (int i = 0;i<=n;i++)  memo[i]=i;

        for(int i = 2;i<=n;i++){
            for (int j = 1;j*j<=i;j++){
                memo[i] = Math.min(memo[i],memo[i-j*j]+1);
            }
        }
        return memo[n];
    }

    /**
     * lc1006 笨阶乘
     */
    public int clumsy(int N){
        Deque<Integer> stack = new LinkedList<Integer>();
        stack.push(N);
        N--;
        int index = 0; //用于控制乘除加减
        while (N>0){
            if(index%4==0){
                stack.push(stack.pop()*N);
            }
            else if(index%4==1){
                stack.push(stack.pop()/N);
            }
            else if(index%4==2){
                stack.push(N);
            }
            else {
                stack.push(-N);
            }
            index++;
            N--;

        }
        //把栈中所有的数字弹出求和
        int sum = 0;
        while (!stack.isEmpty()){
            sum += stack.pop();
        }
        return sum;
    }

    /**
     * lc139 单词拆分
     * 初始化dp={false,false,...false} 长度为n+1,n为字符串长度
     * dp[i]表示s的前i位是否可以用dict中的单词表示
     * 初始化dp[0]=true;空字符可以被表示
     * 遍历字符串的所有子串，遍历开始索引i,遍历区间[0,n)
     * 遍历结束索引j,遍历区间[i=1,n+1)
     * 若dp[i]=true且s[i,...j)在wordlist中
     * 说明s的前i位可以用wordList表示，且s的前j位可以表示
     */
    public boolean wordBreak(String s, List<String> wordDict){
        int length = s.length();
        boolean[] dp = new boolean[length+1];
        Arrays.fill(dp,false);
        dp[0] = true;
        for (int i=0;i<length;i++){
            if(!dp[i]){
                continue;
            }
            for(String word:wordDict){
                if (word.length() + i <= s.length() && s.startsWith(word, i)){
                    dp[i + word.length()] = true;
                }
            }
        }
        return dp[length];
    }

    /**
     * lc371 两整数之和
     * 不使用+-号，实现两数之和
     * 可以将十进制整数转换为二进制数
     * 二进制每位相加就相当于各位做异或操作
     * 计算进位值相当于各位进行与操作，再向左移一位
     * 重复上述两步
     * 结束条件 进位为0，即a为最终的求和结果
     */
    public int getSum(int a, int b){
        while(b!=0){
            int temp = a^b; //相加各位的值
            b = (a&b)<<1;//进位
            a = temp;
        }
        return a;
    }

    /**
     *lc136 只出现一次的数字
     */

    public int singleNumber(int[] nums){
        HashMap<Integer,Integer> map =new HashMap<>();
        if(nums.length==0) return 0;
        for(int num:nums){
            map.put(num,map.getOrDefault(num,0)+1);
        }
        for(Integer i:map.keySet()){
            Integer count = map.get(i);
            if(count==1){
                return i;
            }
        }
        return -1;

    }

    /**
     *lc73 矩阵置零
     */
    public void setZeroes(int[][] matrix) {
        //做一个数组,标记已经作为0的位置,然后继续遍历,
        // 要是原数组的位置本来就是0,则标记数组还是为0
        int[][] m=new int[matrix.length][matrix[0].length];
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                if(matrix[i][j]==0 && m[i][j] != 1){
                    for(int p=0;p<matrix[i].length;p++){
                        if(matrix[i][p] != 0)  m[i][p] = 1;
                        matrix[i][p]=0;
                    }
                    for(int z=0;z<matrix.length;z++){
                        if(matrix[z][j] != 0)   m[z][j] =1;
                        matrix[z][j]=0;
                    }
                }
            }
        }
    }

    /**
     * 直方图的水量
     * 思路：可以用总体积，减去柱子的体积，就是水量
     */
    public int trap(int[] height){
        int sum = 0;
        //柱子的体积
        for(int heigh:height){
            sum+=heigh;
        }
        int volume = 0;
        int high = 1;
        int size = height.length;
        //双指针初始化
        int left = 0;
        int right = size-1;
        while (left<=right){
            while(left<=right && height[left]<high){
                left++;
            }
            while(left<=right && height[right]<high){
                right--;
            }
            volume += right-left+1; //每一层的容量都加起来
            high++;//高度加1
        }
        return volume-sum;

    }

    /**
     * lc155 最小栈
     * 思路：构建一个辅助栈，每当push进来元素时，如果其小于等于min_stack的栈顶值，更新栈顶最小值
     * 将pop出去的元素是否是min_stack栈顶元素值，如果是则将min_stack栈顶元素一起pop()
     * get_min方法直接返回min_stack栈顶值
     *
     */

    /**
     * lc53 最大子序和
     * tmpSum代表子数组的和，
     * 二是子数组的和在变化过程中产生的最大res
     */
    public int maxSubArray(int[]nums){
        int tmpSum = 0;
        int res = 0;
        for(int num:nums){
            //计算子数组的和
            //两者之间取大值是因为，如果num之前的和是负数，何必加上它？那么返回num
            //如果num之前和是正数，加上num一定大，返回num+tmpSum
            tmpSum = Math.max(tmpSum+num,num);
            //res更新最大值，只要变大就更新
            res = Math.max(res,tmpSum);
        }
        return res;
    }

    /**
     * lc152 乘积最大的子数组
     * 动态规划、
     * 乘积的问题在于当前面乘积是正数，下一个数是负数的时候，就可能变成最小值了
     * 当前面的乘积是负数，下一个是负数的时候，就可能变成最大值了
     * maxDp[i] = Math.max(nums[i],Math.max(maxDp[i-1]*nums[i]),minDp[i-1]*nums[i]))
     * minDp[i] = Math.min(nums[i],Math.min(maxDP[i-1]*nums[i], minDP[i-1]*nums[i]))
     */
    public int maxProduct(int[] nums) {
        if(nums.length == 0) return 0;
        int ans = nums[0];
        //两个mDP分别定义为以i结尾的子数组的最大积与最小积；
        int[] maxDP = new int[nums.length];
        int[] minDP = new int[nums.length];
        //初始化DP；
        maxDP[0] = nums[0];
        minDP[0] = nums[0];

        for(int i = 1; i < nums.length; i++){
            //最大积的可能情况有：元素i自己本身，上一个最大积与i元素累乘，上一个最小积与i元素累乘；
            //与i元素自己进行比较是为了处理i元素之前全都是0的情况；
            maxDP[i] = Math.max(nums[i], Math.max(maxDP[i-1]*nums[i], minDP[i-1]*nums[i]));
            minDP[i] = Math.min(nums[i], Math.min(maxDP[i-1]*nums[i], minDP[i-1]*nums[i]));
            //记录ans；
            ans = Math.max(ans, maxDP[i]);
        }
        return ans;

    }

    /**
     *lc169 多数元素
     */
    public int majorityElement(int[] nums) {
        int times = nums.length/2;
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int num:nums){
            map.put(num,map.getOrDefault(num,0)+1);
        }
        int res = 0;
        for(int key:map.keySet()){
            if(map.get(key)>=times){
                res = key;
            }
        }
        return res;
    }

    /**
     * lc300 最长递增子序列
     * dp[i]为前i个数字的最长子序列长度 即以nums[i]结尾的最长子序列长度
     *
     */
    public int lengthOfLIS(int[] nums){
        if(nums.length==0) return 0;
        int[] dp = new int[nums.length];
        int res = 0;
        Arrays.fill(dp,1);
        for(int i=0;i<nums.length;i++){
            for(int j=0;j<i;j++){
                if(nums[j]<nums[i]) dp[i] = Math.max(dp[i],dp[j]+1);
            }
            res = Math.max(res,dp[i]);
        }
        return res;
    }
    /**
     * lc647 回文子串
     * 动态规划
     * dp[i][j]表示字符串i到j之间的子串是否是回文
     * 当s[i]=s[j]时&&(j-i<2||dp[i+1][j-1])时，dp[i][j]=true
     * 否则false
     * 当只有一个字符时 a是一个回文
     * 有两个字符aa，也是一个回文
     * 当有三个字符以上时，ababa，记为串1，两边的a去掉，bab串2，串2是一个回文串
     * 那么两边多个a也是回文串
     * 当s[i]==s[j]，要看dp[i+1][j-1]是否是一个回文串
     */
    public int countSubstrings(String s){
        boolean[][] dp = new boolean[s.length()][s.length()];
        int ans = 0;
        for(int j=0;j<s.length();j++){
            for(int i=0;i<=j;i++){
                if (s.charAt(i) == s.charAt(j) && (j - i < 2 || dp[i + 1][j - 1])){
                    dp[i][j] = true;
                    ans++;
                }
            }
        }
        return ans;
    }
    /**
     * 法2 中心扩散法
     * 中心点一共有len+len-1个，分别对应一个中心点和两个数作为中心点
     */
    public int countSubstrings2(String s){
        //中心扩散法
        int ans = 0;
        for(int center = 0; center<2*s.length()-1;center++){
            // left和right指针和中心点的关系是？
            // 首先是left，有一个很明显的2倍关系的存在，其次是right，可能和left指向同一个（偶数时），也可能往后移动一个（奇数）
            // 大致的关系出来了，可以选择带两个特殊例子进去看看是否满足。
            int left = center / 2;
            int right = left + center % 2;
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)){
                ans++;
                left--;
                right++;
            }
        }
        return ans;

    }

    /**
     * lc1143 最长公共子序列
     * 子序列是可以不连续的，子数组是必须连续的
     * 单个数组或者字符串要动态规划时，可以把dp[i]定为nums[0:i]中想要求的结果
     * 当两个数组或者字符串时要用动规时，可以定义二维数组dp[i][j]含义是A[0,i]B[0.j]之间匹配得到想要的结果
     * 1.状态定义：
     * 对于本题：dp[i][j]表示[0,i-1][0,j-1]的最长公共子序列
     * 2.状态转移方程
     * dp[i][j]=dp[i−1][j−1]+1, 当 text1[i - 1] == text2[j - 1];text1[i−1]==text2[j−1];
     * dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])dp[i][j]=max(dp[i−1][j],dp[i][j−1]), 当 text1[i - 1] != text2[j - 1]text1[i−1]!=text2[j−1]
     *
     *
     */
    public int longestCommonSubsequence(String text1, String text2){
        int M = text1.length();
        int N = text2.length();
        int[][] dp =new int[M+1][N+1];
        for(int i=1;i<M;i++){
            for(int j=1;j<N;j++){
                if(text1.charAt(i-1)==text2.charAt(j)){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[M][N];
    }

    /**
     * lc64 最小路径和
     * dp[i][j]走到(i,j)的最短路径
     * 当左边上边都不是矩阵边界时，dp[i][j]=  min(dp[i-1][j],dp[i][j-1])+grid[i][j]
     * 当左边是矩阵边界时，i=0,只能从上边来，dp[i][j] = dp[i][j-1]+grid[i][j]
     * 当上边是矩阵边界时，j=0,只能从左边来，dp[i][j] = dp[i-1][j]+grid[i][j]
     */
    public int minPathSum(int[][] grid){
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                if(i==0&&j==0) continue;
                else if(i==0){
                    grid[i][j] = grid[i][j-1] +grid[i][j];
                }
                else if(j==0){
                    grid[i][j] = grid[i-1][j] +grid[i][j];
                }else grid[i][j] =  Math.min(grid[i-1][j],grid[i][j-1])+grid[i][j];
            }
        }
        return grid[grid.length-1][grid[0].length-1];

    }
    /**
     * lc78 子集
     * 回溯求子集问题
     */
    public List<List<Integer>> subsets(int[] nums){
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> cur = new ArrayList<>();
        dfs(nums,0,cur,res);
        return res;

    }
    // nums：原数组，start：从下标start开始，cur：当前得到的列表，res：最终结果
    private void dfs(int[]nums,int start,List<Integer> cur,List<List<Integer>> res){
        // 当前位数时的cur直接加到res中，一开始是空集，后续每调用一层代表多一个位数的集合
        res.add(new ArrayList<Integer>(cur));
        // 当前位从start开始（start以前的元素在前面的位中用过了），遍历到nums末尾
        for(int i=start;i<nums.length;i++){
            cur.add(nums[i]);
            // 当前位取nums[i]，继续从i+1开始遍历下一位，避免重复
            dfs(nums, i + 1, cur, res);
            // 取消当前位的选择，下一轮循环重新选择一次当前位
            cur.remove(cur.size() - 1);
        }
    }
    /**
     * lc198 打家劫舍问题
     */
    public int rob(int[] nums){
        //dp[i] 打劫到第i家，前i家能在满足条件下的最大值
        int n = nums.length;
        if(n==0) return 0;
        int[] dp =  new int[n];
        dp[0] = nums[0];
        for(int i=1;i<n;i++){
            if(i<2){
                dp[i] = Math.max(dp[i-1],0+nums[i]);
            }else{
                dp[i] = Math.max(dp[i-1],dp[i-2]+nums[i]);
            }

        }
        return dp[n-1];
    }
    /**
     * lc46 全排列问题
     * 经典回溯法解决问题
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>(); //结果list
        List<Integer> tempList=new ArrayList<>();
        dfs(nums,res,tempList);
        return res;

    }
    private void dfs(int[]nums,List<List<Integer>> res,List<Integer>tempList){
        if(tempList.size()==nums.length){
            res.add(new ArrayList<Integer>(tempList));
            return;
        }
        for(int i=0;i<nums.length;i++){
            if(tempList.contains(nums[i]))  continue;
            //部分结果加入
            tempList.add(nums[i]);
            //继续遍历
            dfs(nums,res,tempList);
            //取消当前的选择
            tempList.remove(tempList.size()-1);
        }
    }
    /**
     * lc454 四数相加
     * 将数组两两相加，并把和存如hash表，把和当作key，把和出现的次数当作value存入hash表
     * 另外两组同理，求任意两数之和的相反数，在hash表中查是否存在key为sumCD
     */
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D){
        Map<Integer,Integer> map = new HashMap<>();
        int res = 0;
        for(int i=0;i<A.length;i++){
            for(int j=0;j<B.length;j++){
                int sumAB = A[i]+B[j];
                if(map.containsKey(sumAB)){
                    map.put(sumAB,map.get(sumAB)+1);
                }else{
                    map.put(sumAB,1);
                }
            }
        }
        for(int i=0;i<C.length;i++){
            for(int j=0;j<D.length;j++){
                int sumCD = -(C[i]+D[j]);
                if(map.containsKey(sumCD)){
                    res += map.get(sumCD);
                }
            }
        }
        return res;
    }

    /**
     * lc121 买卖股票的最佳时间
     */
    public int maxProfit3(int[] prices){
        int len = prices.length;
        if(len<2){
            return 0;
        }
        //有可能不发生交易，结果集的初始值为0
        int  res = 0;
        //枚举
        for(int i=0;i<len;i++){
            for(int j=i+1;j<len;j++){
                res = Math.max(res,prices[j]-prices[i]);
            }
        }
        return res;
    }
    /**
     * 法2
     * 如果能在历史最低点买就好了 minPrice表示历史最低价格
     * maxValue = price[i]-minPrice
     */
    public int maxProfit2(int prices[]){
        int minPrice = Integer.MAX_VALUE; //历史最低值
        int maxValue = 0;
        for(int i=0;i<prices.length;i++){
            if(prices[i]<minPrice){
                minPrice = prices[i];
            }
            else if(prices[i]-minPrice>maxValue){
                maxValue = prices[i]-minPrice;
            }
        }
        return maxValue;
    }

    /**
     * lc387 字符串中的第一个唯一的字符
     */
    public int firstUniqChar1(String s){
        Map<Character, Integer> map1= new HashMap<Character, Integer>();
        for (int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            map1.put(ch,map1.getOrDefault(ch,0)+1);
        }
        for (int i=0;i<s.length();i++){
            if(map1.get(s.charAt(i))==1){
                return i;
            }
        }
        return -1;
    }

    /**
     *lc134加油站
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int i = 0;//起点
        int rest = 0;//油箱剩余油量
        int need = 0;//油箱缺值
        for(int j = 0;j<cost.length;j++){
            if(rest>=0){
                rest += gas[j]-cost[j];
            }else{
                i = j;
                need += rest;
                rest = gas[j]-cost[j];
            }
        }
        if(rest+need>=0)
            return i;
        else
            return -1;
    }
    /**
     * lc237 原地删除节点
     * 只给了要删除的节点，把这个要删除的节点赋值为它下一个节点的值，这样把node.next = node.next.next
     * 就完事
     */
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;

    }

    /**
     *
     *lc88 合并有序数组
     * 从后边遍历数组，设置指针分别指向nums1和nums2的尾部 len1和len2
     * 设置len指向nums1的最末尾，每次遍历比较大小的时候，大的放到最后
     * 当len1长度为0的时候，直接将nums2剩余的放到nums1前面
     */
    public void merge(int[] nums1, int m, int[] nums2, int n){
        int len1 = m-1;//指向第一个末尾指针
        int len2 = n-1;//指向第二个末尾指针
        int len = m+n-1;//指向末尾的指针
        while(len1>=0 && len2>=0){
            nums1[len--] = nums1[len1]>nums2[len2]?nums1[len1--]:nums2[len2--];
        }
        // 表示将nums2数组从下标0位置开始，拷贝到nums1数组中，从下标0位置开始，长度为len2+1
        System.arraycopy(nums2, 0, nums1, 0, len2 + 1);

    }
    /**
     * lc230 中序遍历
     * 二叉搜索树中的第k小的元素
     */
    private int res;
    private int count;
    public int kthSmallest(TreeNode1 root, int k) {
        inorder(root, k);

        return res;
    }

    public void inorder(TreeNode1 root, int k) {
        if (root == null) {
            return;
        }

        inorder(root.left, k);

        ++count;
        if (count == k) {
            res = root.val;
            return;
        }

        inorder(root.right, k);
    }























}



