package com.company;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class jianZhiOffer {
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
   public class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }
    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }


    /**
     * 3.数组中的重复数字
     */
    public int findRepeatNumber(int[] nums) {
        if(nums.length==0){
            return 0;
        }
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(nums[i])){
                map.put(nums[i],map.get(nums[i])+1);
                return nums[i];
            }
            map.put(nums[i],1);
        }
        return 0;
    }

    /**
     * 4.二维数组中的查找
     */
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return false;
        }
        int rows = matrix.length;//总行数
        int cols = matrix[0].length;//总列数
        int row = 0; //从第一行最右边开始
        int col = cols-1;

        while(row<rows && col>=0){
            int temp = matrix[row][col];
            if(temp==target){
                return true;
            }else if(temp<target){
                row++;

            }else if(temp>target){
                col--;
            }

        }
        return false;
    }

    /**
     * 5.替换空格
     */
    public String replaceSpace(String s) {
        StringBuilder res = new StringBuilder();
        for (char c:s.toCharArray()){
            if(c==' '){
                res.append("%20");
            }else{
                res.append(c);
            }
        }
        return res.toString();
    }
    /**
     * 6.从尾到头打印链表
     */
    public int[] reversePrint(ListNode head) {
        LinkedList<Integer> sta = new LinkedList<>();
        ListNode cur = head;
        while(cur!=null){
            sta.push(cur.val);
            cur = cur.next;
        }
        int[] res = new int[sta.size()];
        int i=0;
        while(!sta.isEmpty()){
            res[i] = sta.pop();
            i++;
        }
        return res;
    }

    /**
     * 7.重建二叉树
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder.length==0 || inorder.length==0){
            return null;
        }
        //前序遍历第一个节点是根节点，找到根节点后，在中序遍历里分两边左右子树
        TreeNode root = new TreeNode(preorder[0]); //找到根节点
        for(int i=0;i<inorder.length;i++){
            if(inorder[i]==preorder[0]){
                //左子树
                root.left = buildTree(Arrays.copyOfRange(preorder,1,i+1),Arrays.copyOfRange(inorder,0,i));
                root.right = buildTree(Arrays.copyOfRange(preorder,i+1,preorder.length),Arrays.copyOfRange(inorder,i+1,inorder.length));
                break;
            }
        }
        return root;
    }

    /**
     * 9.用两个栈实现队列
     */


    Stack<Integer> s1 = new Stack<>();;
    Stack<Integer> s2 = new Stack<>();;

    public void appendTail(int value) {
        s1.push(value);
    }

    public int deleteHead() {
        if(s2.empty()){
            if(s1.empty()){
                return -1;
            }
            while(!s1.empty()){
                s2.push(s1.pop());
            }
        }
        return s2.pop();
    }

    /**
     * 11. 旋转数组的最小数字
     * 二分法
     * 找旋转数组的最小数字其实就是找右边升序数组的最小数
     * 定义左右指针分别指向数组左右，再取中点
     * 当nums[mid]>nums[right],mid位置一定处于左排序数组，旋转点x一定处于[mid+1,right]闭区间，那么将左指针指向mid+1
     * 当nums[mid]<nums[right],mid位置一定处于右排序数组，旋转点x一定处于[left,mid]闭区间，那么将右指针指向mid
     * 当nums[mid]=nums[right],不能判断，缩小right,right = right-1;
     */
    public int minArray(int[] numbers) {
        int left=0;
        int right = numbers.length-1;
        while(left<right){
            int mid = left+((right-left)>>1);
            if(numbers[mid]>numbers[right]) left = mid+1;
            else if(numbers[mid]<numbers[right]) right = mid;
            else right--;
        }
        return numbers[left];
    }


    /**
     * 12.矩阵中的路径
     */
    public boolean exist(char[][] board, String word){
        char[] words = word.toCharArray();
        for (int i=0;i<board.length;i++){
            for (int j=0;j<board[0].length;j++){
                //从[i,j]这个位置开始查找
                if(dfs(board,words,i,j,0)){
                    return true;
                }
            }
        }
        return false;
    }
    boolean dfs(char[][] board,char[]word,int i,int j,int index){
        //边界的判断，如果越界直接返回false。index表示的是查找到字符串word的第几个字符，
        if(i>=board.length||i<0||j>=board[0].length||j<0||board[i][j]!=word[index]){
            return  false;
        }
        //如果word的每个字符都查找完了，直接返回true
        if(index == word.length){
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
     * 13.机器人的运动范围
     */


    /**
     * 15.二进制中1的个数
     * 如果n&1==0,则最后一位是0；n&==1，则最后一位是1
     * 每次将n右移一位得到更新的最后一位
     */
    public int hammingWeight(int n) {
        // 返回结果
        int res = 0;
        while(n != 0){
            res = res + (n & 1);
            // 无符号右移1位
            n = n >>> 1;
        }
        return res;
    }


    /**
     * 16.数值的整数次方
     * java中n的最小值可以取Integer.Min_Value,取相反数还是自己，所以提一个x出来
     */
    public double myPow(double x, int n){
        if(n==0) {
            return 1;
        }else if(n<0){
            return 1/(x*myPow(x,-n-1));
        }else if(n%2==1){
            return x*myPow(x,n-1);
        }else{
            return myPow(x*x,n/2);
        }


    }

    /**
     * 打印1到最大的n位数
     * 找到最大的n+1位的数，比如n=3,找10的3次方
     */
    public int[] printNumbers(int n){
        int res = 1;
        int i=0;
        while(i<n){
            res = res*10;
            i++;
        }
        int[] resArr = new int[res-1];
        for(int j=1;j<res;j++){
            resArr[j-1] = j;
        }
        return resArr;
    }
    //
    public int[] printNumbersLing(int n) {
        int m=(int)Math.pow(10,n);
        int[] mm=new int[m-1];
        for(int i=1;i<m;i++){
            mm[i-1]=i;
        }
        return mm;
    }

    /**
     * 18.删除链表的节点
     */
    public ListNode deleteNode(ListNode head, int val) {
        //删除链表中的结点需要一个新的哑节点
        if(head==null) return head;
        ListNode dummyNode  = new ListNode(0);
        //哑节点在头节点之前
        dummyNode.next = head;
        //指向哑节点的指针pre
        ListNode pre = dummyNode;
        while(pre.next!=null){
            if(pre.next.val==val){
                pre.next = pre.next.next;
            }else{
                pre = pre.next;

            }
        }
        return dummyNode.next;

    }

    /**
     * 20.表示数值的字符串
     */


    /**
     * 21.调整数组顺序使奇数位于偶数前面
     * 定义左右两个指针分别从两端开始，
     * 左指针从左往右找偶数，遇到奇数跳过，右指针从右往左找奇数，遇到偶数跳过
     * 找到对应的左右交换
     */
    public int[] exchange(int[] nums){
        int left = 0;
        int right = nums.length-1;
        int tmp;
        while(left<right){
            while(left<right&&(nums[left]&1)==1) left++;
            while(left<right&&(nums[right]&1)==0) right--;
            tmp  = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;
        }
        return nums;
    }

    /**
     * 22.倒数第k个节点
     */
    public ListNode getKthFromEnd(ListNode head, int k){
        ListNode fast = head;
        ListNode slow = head;
        for(int i=0;i<k;i++){
            fast = fast.next;
        }
        while (fast!=null){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;

    }

    /**
     * 24.反转链表
     */
    public ListNode reverseList(ListNode head){
        if(head==null) return  null;
        ListNode pre = null;
        ListNode cur = head;
        ListNode next = null;
        while (cur!=null){
            next = cur.next;
            cur.next = pre;
            pre = cur ;
            cur = next;
        }
        return pre;
    }

    /**
     * 25.合并两个排序链表
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2){
        if(l1==null) return l2;
        if(l2==null) return l1;
        ListNode dun = new ListNode(0);
        ListNode cur = dun;
        //哪个小就放进新的链表
        while(l1!=null && l2!=null){
            if(l1.val<l2.val){
                cur.next = l1;
                l1 = l1.next;
            }else{
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        //如果其中一个遍历完，另一个不为空，直接把剩下的放到新链表后面
        cur.next = l1==null?l2:l1;
        return dun.next;


    }

    /**
     * 26. 树的字结构
     */
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A == null || B == null)
            return false;
        //先从根节点判断B是不是A的子结构，如果不是在分别从左右两个子树判断，
        //只要有一个为true，就说明B是A的子结构
        return isSub(A, B) || isSubStructure(A.left, B) || isSubStructure(A.right, B);
    }

    boolean isSub(TreeNode A, TreeNode B) {
        //这里如果B为空，说明B已经访问完了，确定是A的子结构
        if (B == null)
            return true;
        //如果B不为空A为空，或者这两个节点值不同，说明B树不是
        //A的子结构，直接返回false
        if (A == null || A.val != B.val)
            return false;
        //当前节点比较完之后还要继续判断左右子节点
        return isSub(A.left, B.left) && isSub(A.right, B.right);
    }

    /**
     *27.二叉树镜像
     */
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)){
            return root;
        }
        TreeNode temp = root.left;
        root.left=mirrorTree(root.right);
        root.right = mirrorTree(temp);
        return root;
    }

    /**
     * 28.对称的二叉树
     */
    public boolean isSymmetric(TreeNode root) {
        if(root==null) return false;
        return check(root.left,root.right);
    }
    private boolean check(TreeNode left,TreeNode right){
        //左右子树为空，返回true
        if(left==null&&right==null){
            return true;
        }
        //左右子树有一个不为空返回false
        if(left==null || right==null){
            return false;
        }
        //当左右子节点相等，且判断左子节点的左边是否和右子节点的右边且左子节点的右边和右子节点的左边是否相等
        return left.val==right.val&&check(left.left,right.right)&&check(left.right,right.left);
    }

    /**
     * 29.顺时针打印矩阵
     */
    public int[] spiralOrder(int[][] matrix) {
        if(matrix.length == 0) return new int[0];
        int left = 0,right = matrix[0].length-1;
        int top = 0,bottom = matrix.length-1;
        int [] res = new int[(right+1)*(bottom+1)];
        int index = 0;
        while(true){
            for(int i=left;i<=right;i++){
                res[index++] = matrix[top][i];
            }
            top++;
            if(top>bottom) break;
            for(int i=top;i<=bottom;i++){
                res[index++] = matrix[i][right];
            }
            right--;
            if(left>right) break;
            for(int i=right;i>=left;i--){
                res[index++] = matrix[bottom][i];
            }
            bottom--;
            if(top>bottom) break;
            for(int i=bottom;i>=top;i--){
                res[index++] = matrix[i][left];
            }
            left++;
            if(left>right) break;
        }
        return res;
    }

    /**
     * 30.最小栈
     */
    private Stack<Integer> stack;
    private Stack<Integer> min_stack;
    public void MinStack() {
        stack = new Stack<>();
        min_stack = new Stack<>();
    }
    public void push(int x) {
        //先将数压入辅助栈
        stack.push(x);
        //最小栈为空 或者压入的数小于最小栈栈顶的数，将最小数压入最小栈
        if(min_stack.isEmpty()||x<=min_stack.peek()){
            min_stack.push(x);
        }
    }
    public void pop() {
        //辅助栈弹出值和最小栈栈顶值相等，数从最小栈弹出
        if(stack.pop().equals(min_stack.peek())){
            min_stack.pop();
        }
    }
    public int top() {
        //获取辅助栈栈顶
        return stack.peek();
    }
    public int min() {
        //获取最小栈栈顶
        return min_stack.peek();
    }


    /**
     * 33.二叉搜索树的后序遍历序列
     * 后序遍历 左子树-右子树-根
     * 左子树的节点值小于根节点的节点值，右子树的节点值大于根节点的节点值
     */
    public boolean verifyPostorder(int[] postorder){
        if(postorder==null){
            return false;
        }
        int len = postorder.length;
        return verifyPostorderBST(postorder,0,len-1);
    }
    public boolean verifyPostorderBST(int[]postorder,int start,int end){
        if(start>=end){
            return true;
        }
        //后序遍历为 左 右 根
        //最后一个点一定是根节点
        int root = postorder[end];
        //向前遍历，比根节点大的都是右子树的，反之是左子树
        //注意只有右子树或者只有左子树的情况，需要从头遍历
        int i=start;
        while (postorder[i]<root){
            i++;
        }
        //找到i为分割点
        int leftEnd = i-1;
        //i前面的都是左子树，右边的是右子树
        int j = i; //获取右子树
        while (postorder[j]>root){
            j++;
        }
        int rightEnd = end-1;
        return j == end && verifyPostorderBST(postorder,start,leftEnd)&&
                verifyPostorderBST(postorder,leftEnd+1,rightEnd);
        //如果右子树的全部节点都大于根节点，那么遍历的结果应该是j==end，否则存在错误

    }

    /**
     * 34.二叉树和为某一个值的路径
     */
    private List<List<Integer>> result = new LinkedList<List<Integer>>();

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        LinkedList<Integer> curPath = new LinkedList<Integer>();
        dfs(curPath, root, sum);
        return result;
    }

    private void dfs(LinkedList<Integer> curPath, TreeNode curRoot, int target) {
        if (curRoot == null) {
            return;
        }

        curPath.add(curRoot.val);
        target -= curRoot.val;
        //结束递归条件
        if ((target == 0) && (curRoot.left == null) && (curRoot.right == null)) {
            result.add(new LinkedList<Integer>(curPath));
        } else {
            dfs(curPath, curRoot.left, target);
            dfs(curPath, curRoot.right, target);
        }
        curPath.removeLast();   // 清除当前状态，回溯
    }

    /**
     * 36.二叉搜索树与双向链表
     * 二叉搜索树的中序遍历为递增序列
     * 排序链表：中序遍历二叉搜索树
     * 双向链表：构建相邻节点的引用关系时，设前驱节点pre和当前节点cur
     * pre.right = cur , cur.left = pre
     * 循环链表：设链表图节点head和尾节点tail,应构建head.left=tail和tail.right=head;
     */
    Node pre,head;
    public Node treeToDoublyList(Node root) {
        if(root == null) return null;
        //转化为双向链表
        dfs(root);
        //进行头尾节点的互相指向，构造循环链表
        head.left = pre;
        pre.right = head;
        return head;
    }
    void dfs(Node cur) {
        if(cur == null) return;
        dfs(cur.left);
        //构造双向链表
        //pre用于记录双向链表中位于cur左侧的节点，即上一次迭代中的cur
        //当pre==null，cur左侧没有节点，即此时cur为双向链表中的头节点
        if(pre == null) {
            head = cur;
        }
        //反之，pre!=null,cur左侧存在节点pre,需要进行pre.right= cur
        else{
            pre.right = cur;
        }
        cur.left = pre; //pre是否为null对这句话没有影响，且这句放在上面两句if else之前也可
        pre = cur;//pre指向当前的cur

        dfs(cur.right); //全部迭代完成以后，pre指向双向链表中的尾节点
    }

    /**
     * 37.序列化二叉树
     */
    String Sep  = ",";
    String NUll = "#";
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serialize(root,sb);
        return sb.toString();

    }
    void serialize(TreeNode root,StringBuilder sb){
        if(root==null){
            sb.append(NUll).append(Sep);
            return;
        }
        sb.append(root.val).append(Sep);
        serialize(root.left,sb);
        serialize(root.right,sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        LinkedList<String> nodes = new LinkedList<>();
        for(String s : data.split(Sep)){
            nodes.addLast(s);
        }
        return deserialize(nodes);

    }
    TreeNode deserialize(LinkedList<String> nodes){
        if(nodes.isEmpty()) return null;
        String first = nodes.removeFirst();
        if(first.equals(NUll)) return null;
        TreeNode root = new TreeNode(Integer.parseInt(first));
        root.left = deserialize(nodes);
        root.right = deserialize(nodes);
        return root;
    }


    /**
     * 38.字符串的排列 ****
     * 经典回溯问题
     * private void backtrack("原始参数") {
     *     //终止条件(递归必须要有终止条件)
     *     if ("终止条件") {
     *         //一些逻辑操作（可有可无，视情况而定）
     *         return;
     *     }
     *
     *     for (int i = "for循环开始的参数"; i < "for循环结束的参数"; i++) {
     *         //一些逻辑操作（可有可无，视情况而定）
     *
     *         //做出选择
     *
     *         //递归
     *         backtrack("新的参数");
     *         //一些逻辑操作（可有可无，视情况而定）
     *
     *         //撤销选择
     *     }
     * }
     */
    public String[] permutation(String s){
        Set<String> res = new HashSet<>();
        backtrack(s.toCharArray(),"",new boolean[s.length()],res);
        return res.toArray(new String[res.size()]);
    }
    private void backtrack(char[]chars,String temp,boolean[] visited,Set<String>res){
        //边界条件判断，当选择的字符串长度等于原来字符串长度，说明原字符串已经选完了，终止条件
        if(temp.length()==chars.length){
            res.add(temp);
            return;
        }
        //每一个节点都要从头开始选
        for(int i=0;i<chars.length;i++){
            //已经选过的就不能再选了
            if(visited[i]){
                continue;
            }
            //表示选择当前字符
            visited[i]=true;
            //把当前字符选择后，到树的下一层继续选
            backtrack(chars,temp+chars[i],visited,res);
            //撤销选择
            visited[i] = false;
        }

    }

    /**
     * 39.数组中出现次数超过一半的数字
     */
    public int majorityElement(int[] nums) {
        int times = nums.length/2;
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int num:nums){
            map.put(num,map.getOrDefault(num,0)+1);
        }
        int res = 0;
        for(int key:map.keySet()){
            if(map.get(key)>times){
                res = key;
            }
        }
        return res;
    }

    /**
     * 40.最小的k个数
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        Arrays.sort(arr);
        int[]res = new int[k];
        for(int i=0;i<k;i++){
            res[i] = arr[i];
        }
        return res;
    }
    //2.快排思路***
    public int[] getLeastNumberskuaipai(int[] arr, int k) {
        quickSort(arr, 0, arr.length - 1);
        return Arrays.copyOf(arr, k);
    }
    private void quickSort(int[] arr, int l, int r) {
        // 子数组长度为 1 时终止递归
        if (l >= r) return;
        // 哨兵划分操作（以 arr[l] 作为基准数）
        //比基准数小的都放在左边，比基准数大的都放右边
        int i = l, j = r;
        while (i < j) {
            while (i < j && arr[j] >= arr[l]) j--; //从后往前找比哨兵值小的
            while (i < j && arr[i] <= arr[l]) i++;//从前往回找哨兵值大的
            swap(arr, i, j); //找到后交换这两个值
        }
        //交换基准数
        swap(arr, i, l);
        // 递归左（右）子数组执行哨兵划分
        quickSort(arr, l, i - 1);
        quickSort(arr, i + 1, r);
    }
    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    /**
     * 42.连续子数组的最大和
     * 动态规划
     * dp[i]表示以元素nums[i]结尾的连续子数组最大和
     * dp[i-1]<=0，dp[i]=nums[i]
     * dp[i-1]>0,dp[i]=dp[i-1]+nums[i]
     */
    public int maxSubArrayDp(int[] num) {
        int length = num.length;
        int[] dp = new int[length];
        //边界条件
        dp[0] = num[0];
        int max = dp[0];
        for (int i = 1; i < length; i++) {
            //转移公式
            dp[i] = Math.max(dp[i - 1], 0) + num[i];
            //记录最大值
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public int maxSubArray(int[] nums){
        int res = nums[0];
        for (int i=1;i<nums.length;i++){
            nums[i] += Math.max(nums[i-1],0);
            res = Math.max(res,nums[i]);
        }
        return res;
    }
    /**
     * 44.数字序列中某一位的数字
     */
    public int findNthDigit(int n){
        //digits表示这个n对应的是几位数，一位数的话一共9个数，对应9个数字
        //二位数90个数，对应180个数字，三位数900个数字，对应2700个数
        long base = 9,digits = 1;
        while(n-base*digits>0){
            n -= base*digits;
            base *= 10;
            digits++;
        }
        //n现在表示digits位数的第n个数字
        //计算n真实代表的数字是多少
        long idx = n %digits; //求目标数字之中的第几个数字
        if(idx==0) idx = digits;
        long number = 1;
        for(int i=1;i<digits;i++){
            number *=10;
        }
        number += idx==digits?n/digits-1:n/digits;
        for(long i=idx;i<digits;i++){
            number /=10;
        }
        return (int)number%10;
    }
    ///////
    public int findNthDigit2(int n) {
        if (n < 0) {
            return -1;
        }
        /*
            初始化 位数、当前位数的开始数字、当前位数的总数
        */
        int digit = 1;  // 位数
        long start = 1;  // 当前位数的开始数字
        long count = 9;  // 当前位数的总数
        /*
            计算目标数字 的 位数 和 余数
        */
        while (n > count) {
            n -= count; // 计算 剩余多少数字
            start *= 10;
            digit++;
            count = digit * start * 9;  // 计算 下一位数，新增的数字范围大小
        }
        long num = start + (n - 1) / digit; // 计算当前位数下，目标数字距离第一个数字的偏移量
        return Long.toString(num).charAt((n - 1) % digit) - '0';    // 计算结果
    }


    /**
     * 45.把数组排成最小的数
     */
    public String minNumber(int[] nums){

    }

    /**
     * 46.把数字翻译成字符串
     * 数字xyzcba，从最后两位看，如果ba>=26,则可以分解称xyzcb+a,不能分解称 xyzc+ba
     * 同理，对于ba来说，b为0，即ba<=9，此时也不能分解
     *
     */
    public int translateNum(int num){
        if(num<=9){
            return 1;
        }
        int ba = num%100;
        if(ba>=26||ba<=9){
            return translateNum(num/10);
        }
        else {
            //xyzcb+a  xyzc+ba
             return  translateNum(num/100)+translateNum(num/10);
        }
    }



    /**
     * 47.礼物的最大价值
     * 经典动态规划
     */
    public int maxValue(int[][] grid){
        int m  = grid.length;
        int n = grid[0].length;
        for(int j=1;j<n;j++){
            grid[0][j] += grid[0][j-1];
        }
        for(int i=1;i<n;i++){
            grid[i][0] += grid[i-1][0];
        }
        for (int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                grid[i][j] = Math.max(grid[i][j-1],grid[i-1][j]);
            }
        }
        return grid[m-1][n-1];
    }









































}
