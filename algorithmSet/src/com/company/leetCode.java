package com.company;

import java.util.*;

/**
 * lc13 罗马数字转整数
 * lc79 单词搜索
 * lc26 删除有限数组中的重复项
 * lc80 删除有序数组中的重复性2
 * lc172 阶乘后的零的个数
 * lc4寻找正序数组中的中位数
 * lc162 寻找峰值
 * lc16 最接近的三数之和
 * lc292 Nim游戏
 * lc231 2的幂次
 * lc557 反转字符串中的单词3
 * lc43 字符串乘积
 * lc179 最大数
 *
 */
public class leetCode {
    /**
     * lc13 罗马数字转整数
     */
    public int romanToInt(String s){
        int sum =0;
        //获取前一位
        int preValue = getValue(s.charAt(0));
        for (int i=1;i<s.length();i++){
            //当前位
            int num = getValue(s.charAt(i));
            if(preValue <num){
                sum -= preValue;
            }
            else{
                sum += preValue;
            }
            preValue = num;
        }
        sum += preValue;
        return sum;

    }
    private int getValue(char ch){
        switch(ch){
            case'I':return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }

    /**
     * lc79 单词搜素
     * 典型回溯算法求解，类似于枚举一个一个去尝试，
     * 回溯问题先想结束条件
     * 然后想要进行回溯的当前节点，然后递归调用方法，递归之后复原当前节点
     *
     */
    public boolean exist(char[][] board, String word){
        char[] words = word.toCharArray();
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++){
                //从[i,j]这个坐标开始查找
                if (dfs(board, words, i, j, 0)){
                    return true;
                }
            }
        }
        return false;
    }
    boolean dfs(char[][] board, char[] word, int i, int j, int index){
        //边界的判断，如果越界直接返回false。index表示的是查找到字符串word的第几个字符
        //如果这个字符不等于board[i][j]，说明验证这个坐标路径是走不通的，直接返回false
        if (i >= board.length || i < 0 || j >= board[0].length || j < 0 || board[i][j] != word[index]){
            return false;
        }
        //如果word的每个字符都查找完了，直接返回true
        if (index == word.length - 1){
            return true;
        }
        //把当前坐标的值保存下来，为了在最后复原
        char tmp = board[i][j];
        //然后修改当前坐标的值
        board[i][j] = '.';
        //走递归，沿着当前坐标的上下左右4个方向查找
        boolean res = dfs(board, word, i + 1, j, index + 1) || dfs(board, word, i - 1, j, index + 1) ||
                dfs(board, word, i, j + 1, index + 1) || dfs(board, word, i, j - 1, index + 1);
        //递归之后再把当前的坐标复原
        board[i][j] = tmp;
        return res;
    }
    /**
     * lc26删除有限数组中的重复项
     * 双指针法，分别用两个指针指向数组中的前两项索引，如果后指针指的值不于p指针指的值，
     * 将q指针指的值赋值给p指针后面的一项
     * 最后返回p+1
     */
    public int removeDuplicates(int[] nums){
        if(nums == null || nums.length == 0) return 0;
        int p=0;
        int q=1;
        while(q<nums.length){
            if(nums[q]!=nums[p]){
                nums[p+1] = nums[q];
                p++;
            }
            q++;
        }
        return p+1;

    }
    /**
     * lc80 删除有序数组中的重复项2
     * 双指针法
     * 们需要检查上上个应该被保留的元素nums[slow−2] 是否和当前待检查元素 nums[fast] 相同.
     * 当且仅当nums[slow−2]=nums[fast] 时，
     * 待检查元素nums[fast] 不应该被保留

     */
    public int removeDuplicates2(int[] nums){
        int n = nums.length;
        if(n<2){
            return n;
        }
        int slow = 2;
        int fast = 2;
        while (fast<n){
            if(nums[slow-2]!=nums[fast]){
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;


    }
    /**
     * lc172 阶乘后的零的个数
     *其实就是看每个阶乘数中有多少个5，有多少个5就有多少个0
     * 有些数字可以除25，125..那么就需要对这个数除以5之后的结果一直除以5
     * 直到商为0
     */
    public int trailingZeroes(int n){
        int cnt = 0;//计数器
        while (n>=5){
            cnt += n/5;
            n /=5;
        }
        return cnt;

    }

    /**
     * lc4寻找正序数组中的中位数
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2){
        int[] resNum = mergeNums(nums1,nums2);
        int k = resNum.length;
        double res = 0;
        if(k%2==1){
            res = resNum[k/2];
        }else if(k%2==0){
            res = (resNum[k/2]+resNum[k/2-1])/2.0;
        }
        return res;
    }

    public int[] mergeNums(int[] num1, int[] num2) {
        int res[] = new int[num2.length + num1.length];
        //分别表示数组1，数组2，结果数组的索引
        int i =0,j= 0,k=0;
        //遍历两个数组，进行比较，转移较小的数到新数组里面
        while (i < num1.length && j < num2.length) {
            if (num1[i] < num2[j]) {
                res[k] = num1[i];
                i ++;
            } else {
                res[k] = num2[j];
                j++;
            }
            k++;
        }
        //如果是true代表num1已经转移完成
        if (i > num1.length -1) {
            for (int l = j; l < num2.length; l++,k++) {
                res[k] = num2[l];
            }
        }
        //如果是true代表num2已经转移完成
        if (j > num2.length -1) {
            for (int l = i; l < num1.length; l++,k++) {
                res[k] = num1[l];
            }
        }
        return res;
    }
    /**
     * lc162 寻找峰值
     */
    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;
        while(left<right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid + 1]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     *lc16 最接近的三数之和
     * 有点二分法思想的感觉
     */
    public int threeSumClosest(int[] nums, int target){
        //先对数组从小到大排序
        Arrays.sort(nums);
        int ans = nums[0] + nums[1] + nums[2];
        for(int i=0;i<nums.length;i++) {
            //双指针
            int start = i+1, end = nums.length - 1;
            while(start < end) {

                int sum = nums[start] + nums[end] + nums[i];
                //距离更近
                if(Math.abs(target - sum) < Math.abs(target - ans))
                    ans = sum;
                //这里开始有点二分法内味儿
                if(sum > target)
                    end--;
                else if(sum < target)
                    //开始指针右边移动
                    start++;
                else
                    return ans;
            }
        }
        return ans;

    }
    /**
     * lc292 Nim游戏
     * 是4的倍数的时候先手一定输
     */
    public boolean canWinNim(int n) {
        if(n<=3){
            return true;
        }else if(n%4==0){
            return false;
        }
        return true;
    }
    /**
     * lc231 2的幂
     */
    public boolean isPowerOfTwo(int n) {
        if(n==0) return false;
        while(n%2==0){
            n = n/2;
        }
        return n==1;

    }
    /**
     * lc557 反转字符串中的单词3
     */
    public String reverseWords(String s){
        //将原字符串以空格为界分开，并放入数组
        String[] a = s.split(" ");
        //结果
        StringBuffer sb =new StringBuffer();

        for(int i=0;i<a.length;i++){
            for(int j=a[i].length()-1;j>=0;j--){
                sb.append(a[i].charAt(j));
            }
            //不是最后一个
            if(i!=a.length-1){
                sb.append(" ");
            }

        }
        return new String(sb);
    }

    /**
     * lc43 字符串乘积
     */
    public String multiply(String num1, String num2){
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int m = num1.length();
        int n = num2.length();
        int [] res = new int [m+n];
        for(int i=m-1;i>=0;i--){
            for(int j=n-1;j>=0;j--){
                //对应位乘积
                int num = (num1.charAt(i)-'0')*(num2.charAt(j)-'0');
                //p1 p2对应结果存放的位置
                int p1 = i+j;
                int p2 = i+j+1;
                int sum = num+res[p2];
                res[p2] = sum%10;
                //此处的+=是为了处理进位用的，例如19*19，列出竖式看一下就知道了。
                res[p1] += sum/10;
            }
        }
        StringBuilder result = new StringBuilder();
        for(int i=0;i<res.length;i++){
            //这里的i==0是因为只可能出现首位为0的情况，
            // 例如一个三位数乘一个两位数不可能出现结果是一个三位数的情况。所以只需要判断首位即可。
            if(res[i]==0&&i==0){
                continue;
            }
            result.append(res[i]);
        }
        return result.toString();

    }

    /**
     * lc179 最大数
     */

    /**
     * lc412 FizzBuzz
     */
    public List<String> fizzBuzz(int n){
        List<String> res = new ArrayList<>();
        for(int i=1;i<=n;i++){
            boolean divide3 = (n%3==0);//被3整除
            boolean divide5 = (n%5==0);//被5整除
            if(divide5&&divide5){
                res.add("FizzBuzz");
            }else if(divide5){
                res.add("Buzz");
            }else if(divide3){
                res.add("Fizz");
            }else{
                res.add(Integer.toString(i));
            }
        }
        return res;
    }

    /**
     * lc202 快乐数
     * 每个数字根据各位上的平方和都会指向另一个数，所以从任意数字开始进行平方和迭代
     * 就相当于在链表上游走，如果无限循环但是始终变不到1，就说明走到了环里
     * 快慢指针法
     */
    public boolean isHappy(int n){
        int slow = n;
        int fast = squareSum(n);
        while(slow!=fast){
            slow = squareSum(slow);
            fast = squareSum(squareSum(fast));
        }
        return slow==1;
    }
    private int squareSum(int num){
        int sum =0;
        while(num>0){
            int gewei = num %10;
            sum += gewei*gewei;
            num/=10;
        }
        return sum;
    }

    /**
     * lc378 有序矩阵中第k小的元素
     */
    /**
     *法1 二维数组中的元素放到一维数组，一维数组排序后输出
     */
    public int kthSmallest(int[][] matrix, int k){
        int row = matrix.length;
        int col = matrix[0].length;
        int[] arr = new int[col*row];
        int index = 0;
        for(int[] num:matrix){
            for(int i:num){
                arr[index++] = i;
            }
        }
        Arrays.sort(arr);
        return arr[k-1];
    }
    /**
     * 法2
     * 二分法解决
     */
    public int kthSmallest2(int[][] matrix, int k){
        int row =matrix.length;
        int col = matrix[0].length;
        int left =matrix[0][0];
        int right = matrix[row-1][col-1];
        while(left<right){
            //每次循环都保证第k小的数在start-end之间,当start==end,第k小的数就是start
            int mid = (left+right)/2;
            int cnt = findNum(matrix,mid,row,col);
            if(cnt<k){
                left = mid+1;
            }else{
                right = mid;
            }
        }
        return right;

    }
    private int findNum(int[][]matrix,int mid,int row,int col){
        // 以列为单位找，找到每一列最后一个<=mid的数即知道每一列有多少个数<=mid
        int i = col-1;
        int j = 0;
        int count = 0;
        while(i>=0&&j<col){
            if(matrix[i][j]<=mid){
                // 第j列有i+1个元素<=mid
                count += i+1;
                j++;
            }else{
                // 第j列目前的数大于mid，需要继续在当前列往上找
                i--;
            }
        }
        return count;
    }
    /**
     * lc50 Pow(x,n)
     */
    public double myPow(double x, int n){
        long N = n;
        return N>=0?quickMul(x,N):1/quickMul(x,-N);
    }
    private double quickMul(double x, long N){
        if(N==0){
            return 1.0;
        }
        //拆解开，N为偶数直接一半一半的乘，奇数多乘一个x
        double  y = quickMul(x,N/2);
        return N%2==0? y*y:y*y*x;
    }

    /**
     * lc324 摆动排序
     */
    public void wiggleSort(int[] nums) {
        int[] arr = nums.clone();
        Arrays.sort(arr);
        int N = nums.length;
        for(int i=1;i<nums.length;i+=2){
            nums[i] = arr[--N];
        }
        for(int i=0;i<nums.length;i+=2){
            nums[i] = arr[--N];
        }
    }

    /**
     * lc242 有效的字母异位词
     */
    public boolean isAnagram(String s, String t){
        if(s.length() != t.length()) return false;//长度不等一定返回false
        //在长度相等的情况下
        char[] s_array = s.toCharArray();
        char[] t_array = t.toCharArray();
        int[] arr = new int[26];
        for (int i=0;i<s_array.length;i++){
            int index = s_array[i]-'a';
            arr[index] += 1;
        }
        for (int i=0;i<t_array.length;i++){
            int index = t_array[i]-'a';
            arr[index] -= 1;
            if(arr[index]<0){
                return false;
            }
        }
        return true;
    }
    /**
     * lc8 字符串转为整数
     * 模拟法
     */
    public int myAtoi(String str){
        int len = str.length();
        // str.charAt(i) 方法回去检查下标的合法性，一般先转换成字符数组
        char[] charArray = str.toCharArray();
        //1.先去除先导的空格
        int index = 0;
        while (index<len && charArray[index]==' '){
            index++;
        }
        //2.如果遍历完成，针对极端用例“         ”
        if(index==len){
            return 0;
        }


        //3.针对出现的符号字符，仅第一个有效，记录正负号
        int sign = 1;
        char firstChar = charArray[index];
        if(firstChar=='+'){
            index++;
        }else  if(firstChar=='-'){
            index++;
            sign = -1;
        }

        //将后序出现的字符进行转换
        int res = 0;
        while (index<len){
            char currChar = charArray[index];
            //4.1先判断不合法的情况
            if(currChar>'9'||currChar<'0'){
                break;
            }
            //环境只能存储 32 位大小的有符号整数，因此，需要提前判断乘以 10 以后是否越界
            if(res >Integer.MAX_VALUE/10||(res==Integer.MAX_VALUE/10)&&(currChar-'0')>Integer.MAX_VALUE%10){
                return Integer.MAX_VALUE;
            }
            if (res < Integer.MIN_VALUE / 10 || (res == Integer.MIN_VALUE / 10 && (currChar - '0') > -(Integer.MIN_VALUE % 10))) {
                return Integer.MIN_VALUE;
            }
            //再考虑合法的情况，每一步把符号位乘进去
            res = res*10+sign*(currChar-'0');
            index++;
        }
        return res;
    }


    /**
     * lc38 外观数列
     */
    public String countAndSay(int n) {
        String s = "1";
        for (int i = 2; i <= n; i++) {
            s = nextString(s);
        }
        return s;
    }
    String nextString(String s){
        StringBuilder sb = new StringBuilder();
        int n = s.length();
        char[] cs = s.toCharArray();
        char c = cs[0];
        int cnt = 1;
        for(int i=1;i<n;i++){
            char cur = cs[i];
            if(cur==c){
                cnt++;
            }else{
                sb.append(cnt);
                sb.append(c);
                c = cur;
                cnt = 1;

            }
        }
        sb.append(cnt);
        sb.append(c);
        return sb.toString();
    }

    String nextString1(String s){
        StringBuilder sb = new StringBuilder();
        char[] charArray = s.toCharArray();
        char c = charArray[0];
        int cnt = 1;
        for(int i=1;i<charArray.length;i++){
            if(charArray[i]==c){
                cnt++;
            }else{
                sb.append(cnt);
                sb.append(c);
                c = charArray[i];
                cnt = 1;
            }
        }
        sb.append(cnt);
        sb.append(c);
        return sb.toString();
    }

    /**
     * lc287 寻找重复数
     */
//    public int findDuplicate(int[] nums){
//
//    }
    /**
     * lc198 打家劫舍
     */
    public int rob1(int[]nums){
        if(nums.length==0) return 0;
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        for(int i=1;i<n;i++){
            if(i<2){
                // 从i=0 or 1 开始起步
                dp[i] = Math.max(dp[i-1],0+nums[i]);
            }else{
                // 每个房间面临被偷和不被偷的可能，寻找最大值
                dp[i] = Math.max(dp[i-1],dp[i-2]+nums[i]);
            }
        }
        return dp[n-1];
    }

    /**
     * lc213 打家劫舍2
     */
    public int rob(int[] nums) {
        if(nums.length == 0) return 0;
        if(nums.length == 1) return nums[0];
        return Math.max(myRob(Arrays.copyOfRange(nums, 0, nums.length - 1)),
                myRob(Arrays.copyOfRange(nums, 1, nums.length)));
    }
    private int myRob(int[] nums) {
        int pre = 0, cur = 0, tmp;
        for(int num : nums) {
            tmp = cur;
            cur = Math.max(pre + num, cur);
            pre = tmp;
        }
        return cur;
    }
    /**
     * 环形打家劫舍问题在于将环形转换成单链，然后比较两个的结果
     * 第一个与最后一家首尾相连的话，可以从偷第一家和不偷第一家两个方向考虑
     */
    public int rob2(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }
        if(nums.length == 1){//针对只有一个长度的【0】  因为下面要复制数组
            return nums[0];
        }
        return Math.max(robSingle(Arrays.copyOfRange(nums,0,nums.length-1)),
                robSingle(Arrays.copyOfRange(nums,1,nums.length)));


    }
    public int robSingle(int[]nums){
        int len = nums.length;
        int[] dp = new int[len+1];
        dp[0] = 0;//为了方便操作i-2;
        dp[1] = nums[0];//第一间房子打劫的最大数
        for(int i=1;i<nums.length;i++){
            dp[i] = Math.max(dp[i-1],dp[i-2]+nums[i-1]); //第i家不偷的话，dp[i]数组就是前dp[i-1]
            //偷第i家的话那么第i-1家不能偷，最大就是dp[i-2]+nums[i-1]
        }
        return dp[len];
    }

    /**
     * lc35 前k个高频元素
     */
    public int[] topKFrequent(int[] nums, int k){
        //用hash表记录nums[]中每个元素出现的个数，key是元素，value是次数
        Map<Integer,Integer> map = new HashMap<>();
        for (int i:nums){
            if(map.containsKey(i)){
                map.put(i,map.get(i)+1);
            }else{
                map.put(i,1);
            }
        }
        //桶排序，构造一个桶的集合，长度为nums.length+1,因为bucket[0]没有意义
        //目的是将频率为i的数，放到第i个bucket位置上
        List<Integer>[] bucket = new List[nums.length+1];
        for (int key:map.keySet()){
            //某个数在HashMap里的value是几，就会被放到第几个桶里
            int value = map.get(key);//频率
            if(bucket[value]==null){
                //如果某个桶还没有放入数组，则初始化其为一个数组
                bucket[value] = new ArrayList<>();
            }
            bucket[value].add(key);
        }
        int[] res = new int[k];
        //结果数组索引
        int index = 0;
        for(int i=bucket.length-1;i>0;i--){
            //遍历每个桶
            if(bucket[i]!=null){
                //桶里面有数字
                for(int j=0;j<bucket[i].size()&&index<k;j++){
                    res[index++] = bucket[i].get(j);
                }
            }
        }
        return res;
    }

    /**
     * lc56 合并区间
     */
    public int[][] merge(int[][] intervals){
        //按照区间的起始位置排序
        Arrays.sort(intervals,(v1,v2)->v1[0]-v2[0]);
        //遍历区间
        int[][] res = new int[intervals.length][2];
        int idx = -1;
        for(int[] interval:intervals){
            //如果结果数组为空，或者当前区间的起始位置>结果数组中的结束位置
            //则不能合并。直接将当前区间加到结果数组
            if(idx==-1||interval[0]>res[idx][1]){
                res[++idx] = interval;
            }else{
                //反之将当前区间合并到结果数组的最后区间
                res[idx][1] = Math.max(res[idx][1],interval[1]);
            }
        }
        return Arrays.copyOf(res,idx+1);
    }

    /**
     * 搜索二维矩阵2
     * @return
     */

    public boolean searchMatrix(int[][] matrix, int target) {
        int row = matrix.length;  //行
        int col = matrix[0].length;  //列
        int left = 0;
        int right = col-1;
        while(left!=row&&right>=0){
            if(target<matrix[left][right]){
                right--;
            }else if(target>matrix[left][right]){
                left++;
            }else{
                return true;
            }
        }
        return false;

    }









}


