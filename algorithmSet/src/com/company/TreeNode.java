package com.company;
/**************************
 * 树问题
 * 二叉树的难点在于把题目细化成每个节点要做的事情，递归递归递归！！
 * 1.求一个二叉树的最大深度；
 * 2.二叉树的层序遍历
 * 3.二叉树的前、中、后序遍历
 * 4.在二叉树中找到两个节点最近的公共祖先节点
 * 5.二叉树之字遍历
 * 6.重构二叉树
 * 7.根节点到叶子节点所有路径和
 * 8.判断二叉树是否是镜像
 * 9.二叉树是否存在节点和为指定值的路径
 * 10.翻转镜像二叉树
 * 11.lc116 填充二叉树中每一个节点的右侧指针
 * 12. lc114 将二叉树展开为链表
 * 13.二叉树的序列化和反序列化
 * 14.二叉树中的最大路径和
 * 15.二叉树的直径
 * 16.从前序遍历和中序遍历中递归二叉树
 *17.lc108 有序数组转换为二叉搜索树
 * 18.lc235 二叉搜索树的最近公共祖先
 * 19.lc98 验证二叉搜索树
 *20.lc103 二叉树的锯齿形层序遍历
 */



import java.util.*;

class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};

class TreeNode1 {
    int val = 0;
    TreeNode1 left = null;
    TreeNode1 right = null;
    TreeNode1 (int x) {val = x;}
    //定义树节点类


    //////////////////////////////////////////
    //1.如何求一个二叉树的深度 通过递归实现找到二叉树最大深度
    /////////////////////////////////////////
    public int maxDepth(TreeNode1 root){
        if(root == null){
            return 0;
        }
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        int biggerNum = Math.max(left,right);
        return biggerNum+1;
    }

    ////////////////////
    //2.二叉树的层序遍历//
    ///////////////////
    public List<List<Integer>> levelOrder(TreeNode1 root){
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        LinkedList<TreeNode1> partRes = new LinkedList<TreeNode1>();
        if(root==null){
            return res;
        }
        partRes.offer(root); //先加入根节点
        while(partRes.size()!=0){
            List<Integer> li = new ArrayList<Integer>();
            int size = partRes.size();
            for(int i=0; i<size; i++){
                TreeNode1 temp = partRes.poll();
                li.add(temp.val);
                if(temp.left != null){
                    partRes.offer(temp.left);
                }
                if(temp.right != null){
                    partRes.offer(temp.right);
                }
            }
            res.add(li);
        }
        return res;
    }
    //////////////////////////////
    //3.二叉树的前序、中序、后序遍历//
    /////////////////////////////
    //前序
    public void preOrder(TreeNode1 root, List<Integer>list){
        if(root==null) {
            return;
        }else{
            list.add(root.val);
            preOrder(root.left,list);
            preOrder(root.right,list);
        }
    }
    //中序
    public void inOrder(TreeNode1 root, List<Integer>list){
        if(root==null) {
            return;
        }else{
            inOrder(root.left,list);
            list.add(root.val);
            inOrder(root.right,list);
        }
    }
    //后序
    public void postOrder(TreeNode1 root, List<Integer>list){
        if(root==null) {
            return;
        }else{
            postOrder(root.left,list);
            postOrder(root.right,list);
            list.add(root.val);
        }
    }
    //////////////////////////////////////
    //4.在二叉树中找到两个节点的最近公共祖先节点//
    //////////////////////////////////////
    public int lowestCommonAncestor(TreeNode1 root, int o1, int o2){
        // write code here
        if(root != null){
            int temp = 0; //标记位
            //本节点是o1，o2中的一个，temp标记加一
            if(root.val == o1 || root.val == o2){
                temp = 1;
            }
            //如果temp = 2则找到答案了
            temp +=lowestCommonAncestor(root.left,o1,o2);
            temp +=lowestCommonAncestor(root.right,o1,o2);
            if(temp==2){
                return root.val;
            }
            //若只出现一个，返回上层继续找
            return temp;
        }
        return 0;

    }
    ////////////////////////////////////////
    //5.二叉树之字形遍历（先从左往右，再从右往左）//
    ////////////////////////////////////////
    public ArrayList<ArrayList<Integer>> zigzagLevelOrder(TreeNode1 root){
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        if(root==null){
            return res;
        }
        ArrayList<TreeNode1> list = new ArrayList<TreeNode1>();
        list.add(root);
        int flag = 1;
        while(list.size()>0){
            ArrayList<Integer> newLine = new ArrayList<Integer>();
            ArrayList<TreeNode1> newList = new ArrayList<TreeNode1>();
            for(int i=0; i<list.size();i++){
                if(list.get(i).left != null) newList.add(list.get(i).left);
                if(list.get(i).right != null) newList.add(list.get(i).right);
                if(flag==1){
                    //flag为1表示从左往右添加
                    newLine.add(list.get(i).val);
                }
                else{
                    newLine.add(0,list.get(i).val); //从右往左添加
                }
            }
            flag = 1-flag;
            list = newList;
            res.add(newLine);

        }
        return res;

    }
    ////////////////
    //6.重建二叉树//
    ///////////////
    public TreeNode1 reConstructBinaryTree(int[] pre, int[] in){
        if(pre.length==0 || in.length==0){
            return null;
        }
        //前序遍历的第一个节点一定是树的根节点
        TreeNode1 root = new TreeNode1(pre[0]);

        //在中序遍历中查找根节点，然后左右划分子树
        for(int i =0;i<in.length;i++){
            if(in[i] == pre[0]){
                //左子树
                root.left = reConstructBinaryTree(Arrays.copyOfRange(pre,1,i+1),Arrays.copyOfRange(in,0,i));
                //右子树
                root.right = reConstructBinaryTree(Arrays.copyOfRange(pre,i+1,pre.length),Arrays.copyOfRange(in,i+1,in.length));
                break;
            }
        }
        return root;
    }
    //////////////////////////////
    //7.二叉树根节点到叶子节点所有路径和
    //////////////////////////////
    //递归求法
    // 叶子节点就是左右子树不存在，那么就从根节点一直往下找到叶子节点计算和
    //当左右子树不为空不是叶子节点，低轨计算
    public int sumNumbers(TreeNode1 root, int sum){
        if(root==null){
            return 0;
        }
        if(root.left==null || root.left == null){
            return sum;
        }else{
            int result = 0;
            if(root.left != null){
                result += sumNumbers(root.left,sum*10+root.left.val);
            }
            if(root.right !=null){
                result += sumNumbers(root.right,sum*10+root.right.val);
            }
            return  result;
        }

    }

    /////////////////////
    //8.判断二叉树是否是镜像二叉树//
    /////////////////////
    //其实就是递归比较左右子树数值大小
    public boolean isSymmetric(TreeNode1 root){
        if(root==null){
            return true;
        }
        return check(root.left,root.right);
    }
    public boolean check(TreeNode1 left, TreeNode1 right){
        if(left==null && right==null){
            return true;
        }
        if(left==null || right==null){
            return false;
        }
        return left.val== right.val && check(left.left,right.right)&& check(left.right,right.left);
    }

    //////////////////////////
    //9.二叉树是否存在节点和为指定值的路径
    //////////////////////////
    //判断是否存在根节点到叶子节点的节点值之和等于sum的路径
    //递归实现，dfs
    boolean flag = false;
    public void dfs(TreeNode1 root,int sum,int cnt){
        if(root==null || flag==true){
            return;
        }
        cnt += root.val;//首先获取根节点的值
        if(root.left==null && root.right==null){
            if(sum==cnt){
                flag = true;
            }
        }else{
            dfs(root.left,sum,cnt);
            dfs(root.right,sum,cnt);
        }
    }
    public boolean hasPathSum(TreeNode1 root,int sum){
        dfs(root,sum,0);
        return flag;
    }

    /////////////
    //10.翻转镜像二叉树
    ////////////////
    public TreeNode1 invertTree(TreeNode1 root){
        if(root==null){
            return  null;
        }
        TreeNode1 temp = root.left;
        root.left = root.right;
        root.right = temp;
        //递归
        invertTree(root.left);
        invertTree(root.right);
        return root;

    }

    //////////////
    //11.填充每一个二叉树节点的右侧指针 lc116
    /////////////////////////////
    public Node connect(Node root){
        if(root==null){
            return null;
        }
        connectTwoNode(root.left,root.right);
        return root;

    }
    //将每两个相邻的节点都连接起来
    public void connectTwoNode(Node node1,Node node2){
        if(node1==null || node2==null){
            return;
        }
        //将传入的两个节点连接
        node1.next = node2;
        //连接相同父节点的两个子节点
        connectTwoNode(node1.left,node1.right);
        connectTwoNode(node2.left,node2.right);
        //连接跨越父节点的两个子节点
        connectTwoNode(node1.right,node2.left);

    }

    /////////////////
    //12.将二叉树展开为链表
    ///////////////////
    public void flatten(TreeNode1 root){
        if(root==null){
            return;
        }
        //递归调用方法，把树的一边分别拉平
        flatten(root.left);
        flatten(root.right);//此时已经被拉平
        //将左子树作为右子树
        TreeNode1 left = root.left;
        TreeNode1 right = root.right;
        root.left = null;
        root.right = left;
        //将原来的右子树接到当前右子树的末端
        TreeNode1 p = root;
        while (p.right!=null){
            p = p.right;
        }
        p.right = right;

    }

    /////////////////
    //13.二叉树的序列化和反序列化
    ///////////////////
    //前序遍历实现
    public class Codec {

        // Encodes a tree to a single string.
        String Sep  = ",";
        String NUll = "#";
        public String serialize(TreeNode1 root) {
            StringBuilder sb = new StringBuilder();
            serialize(root,sb);
            return sb.toString();

        }
        //序列化
        void serialize(TreeNode1 root,StringBuilder sb){
            if(root==null){
                sb.append(NUll).append(Sep);
                return;
            }
            sb.append(root.val).append(Sep);
            serialize(root.left,sb);
            serialize(root.right,sb);
        }

        // Decodes your encoded data to tree.
        //反序列化
        public TreeNode1 deserialize(String data) {
            LinkedList<String> nodes = new LinkedList<>();
            for(String s : data.split(Sep)){
                nodes.addLast(s);
            }
            return deserialize(nodes);

        }
        TreeNode1 deserialize(LinkedList<String> nodes){
            if(nodes.isEmpty()) return null;
            String first = nodes.removeFirst();
            if(first.equals(NUll)) return null;
            TreeNode1 root = new TreeNode1(Integer.parseInt(first));
            root.left = deserialize(nodes);
            root.right = deserialize(nodes);
            return root;
        }
    }

    //////////////////////
    //14.二叉树中的最大路径和
    //递归实现
    //////////////////////
    private int max = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode1 root){
            //更新max
            dfs(root);
            //返回
            return max;
        }
    //计算并返回从当前根节点出发的最大左或右子树节点的路径和，同时实时更新最大左右子树路径和
    public int dfs(TreeNode1 root){
        if(root == null) return 0;
        //当前节点的左孩子节点的最大子路径和（注意舍弃掉和小于0的路径）
        int leftSum = Math.max(0, dfs(root.left));
        //当前节点的右孩子节点的最大子路径和
        int rightSum = Math.max(0, dfs(root.right));
        //更新最大路径和（最大左路径和+最大右路径和+当前节点值）
        max = Math.max((leftSum + rightSum + root.val), max);
        //返回从当前节点出发的最大左/右路径和
        return Math.max(leftSum, rightSum) + root.val;
    }

    /////////////////////
    //15.二叉树的直径、
    //递归实现
    //////////////////////
    private int res = 0;
    public int diameterOfBinaryTree(Node root) {
        if (root == null || (root.left == null && root.right == null)) {
            return 0;
        }
        maxDepth(root);
        return res;
    }
    private int maxDepth(Node root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);

        res = Math.max(res, leftDepth + rightDepth);

        return Math.max(leftDepth, rightDepth) + 1;
    }
    ///////////////////
    //16.从前序和中序递归二叉树
    ////////////////////////
    public Node buildTree(int[] preorder, int[] inorder){
        if(preorder.length==0||inorder.length==0){
            return null;
        }
        Node root = new Node(preorder[0]);
        for(int i=0;i<preorder.length;i++){
            if(preorder[0]==inorder[i]){
                //将前序数组分成左右两半，再将中序数组分成左右两半
                //之后递归的处理前序数组的左边部分和中序数组的左边部分
                //递归处理前序数组右边部分和中序数组右边部分
                int[] pre_left = Arrays.copyOfRange(preorder,1,i+1);
                int[] pre_right = Arrays.copyOfRange(preorder,i+1,preorder.length);
                int[] in_left = Arrays.copyOfRange(inorder,0,i);
                int[] in_right = Arrays.copyOfRange(inorder,i+1,inorder.length);
                root.left = buildTree(pre_left,in_left);
                root.right = buildTree(pre_right,in_right);
                break;
            }
        }
        return root;

    }
    /////////////////////////
    //17.有序数组转换为二叉搜索树BST
    ///////////////////////////
    public Node sortedArrayToBST(int[] nums){
        //BST的中序遍历是升序的，本题等同于根据中序遍历的序列恢复二叉树
        //以升序序列中的任意元素作为根节点，该元素左边的升序序列构建左子树，右边是右子树
        return dfs1(nums,0,nums.length-1);
    }
    private Node dfs1(int[]nums,int lo,int hi){
        if(lo>hi){
            return null;
        }
        int mid = lo + (hi-lo)/2;
        Node root = new Node(nums[mid]);
        root.left = dfs1(nums,lo,mid-1);
        root.right = dfs1(nums,mid+1,hi);
        return root;
    }

    /**
     *lc235 18.二叉搜索树的最近公共祖先
     * 二叉搜索树的特点是左子树的所有节点都小于当前节点，右子树的所有节点都大于当前节点
     * 并且每一颗子树都具有上述的特点
     * 如果两个节点值都小于根节点，那么一定在根节点的左子树上
     * 如果两个节点值都大于根节点，那么一定在根节点的右子树上
     * 如果节点值一个比根节点值大，一个比根节点值，那么说明一个在根节点的左子树一个在根节点的又子树上
     * 那么根节点就是他们的最近公共祖先节点
     */

    public Node lowestCommonAncestor(Node root, Node p, Node q){
        //如果根节点和p,q的差相乘是正数，说明这两个差值要么都是正数，要么都是负数
        //也就是说他们肯定都位于根节点的同一侧，继续往下找
        while ((root.val-p.val)*(root.val-q.val)>0){
            root = p.val<root.val?root.left:root.right;
        }
        //如果相乘结果是负数，说明p和q位于根节点的两侧，如果等于0，说明至少有一个就是根节点
        return root;
    }
    /*
    递归方法
     */
    public Node lowestCommonAncestor1(Node root, Node p, Node q) {
        //如果小于等于0，说明p和q位于root的两侧，直接返回即可
        if ((root.val - p.val) * (root.val - q.val) <= 0)
            return root;
        //否则，p和q位于root的同一侧，就继续往下找
        return lowestCommonAncestor1(p.val < root.val ? root.left : root.right, p, q);
    }


    /**
     * lc236 二叉树的最近公共祖先
     * 后序遍历
     */
    public Node lowestCommonAncestor2(Node root, Node p, Node q) {
        if(root == null || root == p || root == q) return root;
        Node left = lowestCommonAncestor(root.left, p, q);
        Node right = lowestCommonAncestor(root.right, p, q);
        //如果left为空，说明这两个节点在root结点的右子树上，我们只需要返回右子树查找的结果即可
        if(left == null) return right;
        if(right == null) return left;
        //如果left和right都不为空，说明这两个节点一个在root的左子树上一个在root的右子树上，
        //我们只需要返回root结点即可。
        return root;
    }

    /**
     * 二叉搜索树节点的最小距离
     * 中序遍历 递归法
     */
    int ans = Integer.MAX_VALUE;
    int pre = -1;

    public int minDiffInBST(Node root){
        dfsZhong(root);
        return ans;
    }
    private void dfsZhong(Node node){
        if(node==null) return;
        dfsZhong(node.left);
        if(pre<0){
            pre = node.val;
        }else{
            ans = Math.min(ans,node.val-pre);
            pre = node.val;
        }

        dfsZhong(node.right);
    }

    /**
     * lc98 验证二叉搜索树
     * 中序遍历，如果当前节点大于当前节点的前一个节点，那么继续遍历，否则返回false
     */
    long a = Long.MIN_VALUE;
    public boolean isValidBST(Node root){
        if(root==null) return true;
        if(!isValidBST(root.left)){
            return false;
        }
        if(root.val<=a){
            return false;
        }
        pre = root.val;
        //访问右子树
        return isValidBST(root.right);
    }


    /**
     * 20.二叉树的锯齿形层序遍历
     */
    public List<List<Integer>> zigzagLevelOrder(Node root){
        //层序遍历，借助队列来存放每一层的节点值。先入先出。但是这里要求是锯齿形，也就是要一来一回。
        //可以奇数层就按层序，但偶数层要翻转一下，比较容易想到的是用栈
        LinkedList<Node> queue = new LinkedList<Node>();
        List<List<Integer>> externalList = new ArrayList<List<Integer>>();
        //边界判断
        if(root == null){
            return externalList;
        }
        //先加入根节点
        queue.offer(root);
        //第几层
        int level = 0;
        while (!queue.isEmpty()){//队列不为空
            int size = queue.size();
            List<Integer> internalList = new ArrayList<Integer>();
            level++;
            Node curNode = new Node();
            for(int i=0;i<size;i++){
                curNode = queue.poll();
                internalList.add(curNode.val);
                if(curNode.left !=null){ //先left后right
                    queue.offer(curNode.left);
                }
                if(curNode.right != null){
                    queue.offer(curNode.right);
                }
            }
            //偶数层反转一下
            if(level%2==0){
                Collections.reverse(internalList);//需要反转一下
            }
            externalList.add(internalList);
        }
        return externalList;
    }

    /**
     * 21.平衡二叉树
     */
    public boolean isBalanced(Node root){
        if(root==null) return true;
        //左右子树的最大深度是差为1，且左右子树都是平衡树
        return Math.abs(depth(root.left)-depth(root.right))<=1 && isBalanced(root.left)
                && isBalanced(root.right);
    }
    //求左右子树的最大深度
    private int depth(Node root){
        if(root==null) return 0;
        return Math.max(depth(root.left),depth(root.right))+1;
    }











}




