package com.company;
/**************************
 * 数组相关题目汇总
 * 1.两数之和
 * 2.合并两个有序数组
 * 3.矩阵查找
 * 4.在给定长数组中找到中位数
 * 5.顺时针旋转矩阵
 * 6.股票交易
 * 7.缺失数组
 * 8.合并区间
 * 9.lc380 o(1)时间插入、删除、获取随机元素
 * 10.数字在升序数组中出现的次数
 */

import java.util.*;
public class Array {
    //区间
    public class Interval{
        int start;
        int end;
        Interval(){ start=0;end =0;}
        Interval(int s,int e){start =s;end =e;}
    }

    ///////////////
    //1.两数之和，给出一个无序整数数组，找出数组中相加等于目标值的数，返回其索引
    ///////////////
    public int[] twoSum(int[] numbers, int target){
        int[] res = {0,0};
        HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
        for(int i=0;i<numbers.length;i++){
            //将数组中的值放到hashmap中的key里，索引是hashmap的value
            map.put(numbers[i],i);//分别是key和value;
        }
        for (int i=0; i<numbers.length;i++){
            if(map.containsKey(target-numbers[i])&&i!=map.get(target-numbers[i])){
                res[0] = i+1;
                res[1] = map.get(target-numbers[i])+1;
                return  res;
            }
        }
        return  res;
    }
    public int[] twoSum2(int[]nums, int target){
        Map<Integer,Integer> map2 = new HashMap<>();
        int n = nums.length;
        for(int i=0;i<n;i++){
            map2.put(nums[i],i);
        }
        for (int i=0;i<n;i++){
            int other = target-nums[i];
            //如果others存在且不是nums[i]本身
            if(map2.containsKey(other)&&map2.get(other)!=i){
                return new int[]{i,map2.get(other)};

            }
        }
        return new int[]{-1,-1};
    }


    //////////////////
    //2.合并两个有序的数组
    //////////////////
    //A长度长，且一定能装下B数组
    public void merge(int A[],int m,int B[],int n){
        int i = m-1;//A数组整个数组最后一位索引
        int j = n-1;//B数组整个数组最后一个索引
        int index = m+n-1; //最后合并完最后的索引
        while(i>=0 && j>=0){ //A与B合并，谁大谁放在最后
            A[index--] = A[i] > B[j]?A[i--]:B[j--];
        }
        //如果B没有遍历完，直接放在A数组里
        while(j>=0){
            A[index--]=B[j--];
        }

    }
    ////////////////////////////
    //3.矩阵查找，矩阵行元素左到右递增，列元素上到下递增
    ///////////////////////////
    public boolean searchMatrix(int[][] matrix, int target){
        //从右上角开始查找，如果目标元素小于右上元素，行内左移动（就是列减），
        //如果目标元素大于右上元素，列下移（就是行加）；
        //目标元素等于右上元素，找到了返回true，最后都没找到返回false
        int row=matrix.length;//行
        int col=matrix[0].length;//列
        int i=0,j=col-1;
        while(i<row && j>=0){
            if(matrix[i][j]==target){
                return true;
            }else if(matrix[i][j]>target){
                j--; //列左移
            }else{
                i++; //行下移
            }
        }
        return false;

    }

    ///////////////////////
    //4.给定长数组中找到中位数
    //////////////////////
    //已知数组长度都为N,求数组中所有数的上中位数
    //假设递增序列长度为n，n为奇数则上中位数为第n/2+1，否则为n/2
    public int findMedianinTwoSortedArray(int[] arr1,int[] arr2){
        ArrayList list = new ArrayList();
        for(int i:arr1){
            list.add(i);
        }
        for(int j:arr2){
            list.add(j);
        }
        Collections.sort(list);
        int num = list.size()/2-1;
        return (int)list.get(num);
    }

    //////////////////////////////
    //5.顺时针旋转矩阵
    ////////////////////////////
    //有一个NxN的矩阵，写一个算法，将矩阵顺时针转90度，返回旋转后的矩阵
    //找规律原来数组中元素的位置，变到了[j][n-1-i]
    public int[][] rotateMatrix(int[][]mat,int n){
        int[][]temp =new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                temp[j][n-1-i]=mat[i][j];
            }
        }return temp;
    }

    /////////////////
    //6.股票交易
    ////////////////
    //假设有一个数组，其中第i个元素是股票第i天的价格，有一次买入和卖出机会
    //设计一个算法得到最大收益
    public int maxProfit(int[] prices){
        if(prices.length<2) return 0;
        int max=0;//最大收益
        int min=prices[0]; //给定第一个数为买入值
        for(int i=1;i<prices.length;i++){
            if(prices[i]-min>max){
                max = prices[i]-min;//对最大值重新赋值
            }
            if(prices[i]<min){
                min = prices[i];
            }
        }
        return max;
    }

    ///////////
    //7.缺失数字
    ///////////
    //从0，1，2..n这n+1个数中选择n个数，组成有序数组，找出缺失的那个数
    //要求O(n)尽可能小
    //只要遍历过程中a[i]!=i，输出i，当遍历到最后时，输出
    public int solve(int[] a){
        for(int i=0;i<a.length;i++){
            if(a[i]!=i){
                return i;
            }if(i==a.length-1){
                return i+1;
            }
        }
        return 0;

    }
    ///////////////
    //8.合并区间
    ///////////////
    //给出一组区间，合并所有重叠的区间，保证合并后的区间按区间起点升序排列
    //直观一点就是比较相邻区间，当后一个区间的开始值在小于前一个区间的结束值时，
    //保留前一个区间的开始值，和后一个区间结束值和前一个区间的结束值
    public ArrayList<Interval> merge (ArrayList<Interval> intervals){
        ArrayList<Interval> res = new ArrayList<Interval>(); //存储结果
        Collections.sort(intervals,(a,b)->a.start-b.start); //先排序
        int len = intervals.size(); //原ArrayList内各区间总数
        int i=0;
        while(i<len){
            int left = intervals.get(i).start; //区间开始值
            int right= intervals.get(i).end;  //区间结束值
            while(i<len-1&&intervals.get(i+1).start<=right){ //当后一个区间的开始值在小于前一个区间的结束值时
                right = Math.max(right,intervals.get(i+1).end); //后一个区间结束值和前一个区间的结束值取其中大的那个
                i++;
            }
            res.add(new Interval(left,right));
            i++;
        }
        return res;

    }

    ///////////////////////
    //9.O(1)时间插入、删除、获取随机元素
    ////////////////////////
    /** Initialize your data structure here. */
    Map<Integer,Integer> map = new HashMap<>();
    List<Integer> list =new ArrayList<>();
    Random random = new Random();

    public void RandomizedSet() {

    }
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    //插入一个值到集合里，集合中不包含返回真，集合中已经存在返回假
    public boolean insert(int val) {
        //若val已经存在，返回false
        if(map.containsKey(val)){
            return false;
        }
        //val不存在，加入list尾部
        //并且记录val对应的索引值
        list.add(val);
        map.put(val,list.size()-1);
        return true;

    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        //表中不包含val，返回假，不用再删除
        if(!map.containsKey(val)){
            return false;
        }
        //先拿到val的索引，
        int idx = map.get(val);
        //获取数组中最后一个元素的值
        int last = list.get(list.size()-1);
        //将最后一个元素对应的索引修改为idx
        list.set(idx,last);
        //数组中删除最后一个元素
        list.remove(list.size()-1);
        //最后记得修改hash表
        map.put(last,idx);
        map.remove(val);
        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        //随机获取nums中的一个元素
        return list.get(random.nextInt(list.size()));

    }

    ////////////////
    //10.数字在升序数组中出现的次数
    /////////////////////
    public int GetNumberOfK(int [] array , int k) {
        int arrayLen = array.length;
        int cnt = 0;
        for(int i=0;i<arrayLen;i++){
            if(array[i]==k){
                cnt++;
            }
        }
        return cnt;
    }

    ///////////////////
    //11.








}
