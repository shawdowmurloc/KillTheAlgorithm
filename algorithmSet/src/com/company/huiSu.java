package com.company;

import java.util.*;

/********************
 * 回溯问题
 *1.输入一组不重复的数字，返回他们的全排列
 * 2，N皇后问题
 * 3.lc39 组合总和
 * */
public class huiSu {
    //
    //解决一个回溯问题，实际上就是一个决策树的遍历过程
    // 1.路径 已经做出的选择
    // 2.选择列表 当前可以做的选择
    // 3.结束条件 到达决策树的底层，无法再做的条件
    //
    //回溯算法的框架：
    //result = []
    //def backtrack(路径，列表)：
    //  if （满足结束条件）：
    //           result.add(路径)
    //           return
    //       for(选择)in(列表)：
    //           做选择
    //           backtrack(路径，选择列表)
    //           撤销选择

    ///////////////////////
    //1.输入一组不重复的数字，返回他们的全排列
    ////////////////////////
    List<List<Integer>> res = new LinkedList<>();
    List<List<Integer>> permute(int[] nums){
        // 记录「路径
        LinkedList<Integer> track = new LinkedList<>();
        backtrack(nums, track);
        return res;

    }
    // 路径：记录在 track 中
    // 选择列表：nums 中不存在于 track 的那些元素
    // 结束条件：nums 中的元素全都在 track 中出现
    public void backtrack(int[] nums, LinkedList<Integer> track){
        // 触发结束条件
        if (track.size() == nums.length){
            res.add(new LinkedList(track));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            // 排除不合法的选择
            if (track.contains(nums[i]))
                continue;
            // 做选择
            track.add(nums[i]);
            // 进入下一层决策树
            backtrack(nums, track);
            // 取消选择
            track.removeLast();
        }
    }
    //////////////////
    //2.N皇后问题
    //////////////////
    //给你一个 N×N 的棋盘，让你放置 N 个皇后，使得它们不能互相攻击。
    //皇后可以攻击同一行、同一列、左上左下右上右下四个方向的任意单位。
    public List<List<String>> solveNQueens(int n) {
        //棋盘
        char[][] chess = new char[n][n];
        //初始化数组
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                chess[i][j] = '.';
        List<List<String>> res = new ArrayList<>();
        solve(res, chess, 0);
        return res;
    }

    private void solve(List<List<String>> res, char[][] chess, int row) {
        //终止条件，最后一行都走完了，说明找到了一组，把它加入到集合res中
        if (row == chess.length) {
            res.add(construct(chess));
            return;
        }
        //遍历每一行
        for (int col = 0; col < chess.length; col++) {
            //判断这个位置是否可以放皇后
            if (valid(chess, row, col)) {
                //数组复制一份
                char[][] temp = copy(chess);
                //在当前位置放个皇后
                temp[row][col] = 'Q';
                //递归到下一行继续
                solve(res, temp, row + 1);
            }
        }
    }

    //把二维数组chess中的数据测下copy一份
    private char[][] copy(char[][] chess) {
        char[][] temp = new char[chess.length][chess[0].length];
        for (int i = 0; i < chess.length; i++) {
            for (int j = 0; j < chess[0].length; j++) {
                temp[i][j] = chess[i][j];
            }
        }
        return temp;
    }

    //row表示第几行，col表示第几列
    private boolean valid(char[][] chess, int row, int col) {
        //判断当前列有没有皇后,因为他是一行一行往下走的，
        //我们只需要检查走过的行数即可，通俗一点就是判断当前
        //坐标位置的上面有没有皇后
        for (int i = 0; i < row; i++) {
            if (chess[i][col] == 'Q') {
                return false;
            }
        }
        //判断当前坐标的右上角有没有皇后
        for (int i = row - 1, j = col + 1; i >= 0 && j < chess.length; i--, j++) {
            if (chess[i][j] == 'Q') {
                return false;
            }
        }
        //判断当前坐标的左上角有没有皇后
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (chess[i][j] == 'Q') {
                return false;
            }
        }
        return true;
    }

    //把数组转为list
    private List<String> construct(char[][] chess) {
        List<String> path = new ArrayList<>();
        for (int i = 0; i < chess.length; i++) {
            path.add(new String(chess[i]));
        }
        return path;
    }

    /**
    *回溯另一个写法 labuladong
     */
    List<List<String>> res1 = new ArrayList<>();
    public List<List<String>> solveNQueens1(int n) {
        //棋盘
        char[][] board = new char[n][n];
        for (char[] i : board){
            Arrays.fill(i,'.');
        }
        backtrack(board,0);
        return res1;
    }
    // 路径：board 中小于 row 的那些行都已经成功放置了皇后
    // 选择列表：第 row 行的所有列都是放置皇后的选择
    // 结束条件：row 超过 board 的最后一行
    void backtrack(char[][] board, int row){
        if (row == board.length){
            res1.add(array2List(board));
            return;
        }

        for (int j = 0;j<board.length;j++){
            if (!check(board,row,j)){
                continue;
            }
            board[row][j] = 'Q';
            backtrack(board,row+1);
            board[row][j] = '.';
        }
    }

    List<String> array2List(char[][] board){
        List<String> res = new LinkedList<>();
        for (char[] i : board){
            StringBuffer sb = new StringBuffer();
            for (char j : i){
                sb.append(j);
            }
            res.add(sb.toString());
        }
        return res;
    }

    boolean check(char[][] board,int row,int col){
        int n = board.length;
        // 检查列是否有皇后互相冲突
        for (int i = 0; i < n; i++) {
            if (board[i][col] == 'Q')
                return false;
        }
        // 检查右上方是否有皇后互相冲突
        for (int i = row - 1, j = col + 1;
             i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q')
                return false;
        }
        // 检查左上方是否有皇后互相冲突
        for (int i = row - 1, j = col - 1;
             i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q')
                return false;
        }
        return true;
    }


    /**
     * 3.lc39 组合总和
     * 回溯法经典问题
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target){
        int len = candidates.length;
        List<List<Integer>> res = new ArrayList<>();
        if(len==0){
            return  res;
        }
        Deque<Integer> path = new ArrayDeque<>();
        dfs(candidates,0,len,target,path,res);
        return res;
    }
    /**
     * @param candidates 候选数组
     * @param begin      搜索起点
     * @param len        冗余变量，是 candidates 里的属性，可以不传
     * @param target     每减去一个元素，目标值变小
     * @param path       从根结点到叶子结点的路径，是一个栈
     * @param res        结果集列表
     */
    private void dfs(int[]candidates,int begin,int len,int target,Deque<Integer> path,List<List<Integer>> res ){
        if(target<0){
            return;
        }
        if(target ==0){
            res.add(new ArrayList<>(path));
            return;
        }
        for(int i=begin;i<len;i++){
            path.addLast(candidates[i]);
            // 注意：由于每一个元素可以重复使用，下一轮搜索的起点依然是 i，这里非常容易弄错
            dfs(candidates,i,len,target-candidates[i],path,res);
            //状态重置
            path.removeLast();
        }



    }





}
