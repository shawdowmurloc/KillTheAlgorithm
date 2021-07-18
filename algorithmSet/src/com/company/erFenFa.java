package com.company;
/**
//1.丑数
//2.旋转数组的最小数字
//3.二分查找 **
//4.完全二叉树节点数
//5.求平方根
//6.在长度相等的排序数组中找到中位数
//7.lc35 搜索插入位置
//8.lc34 在排序数组中找到元素的第一个位置和最后一个位置
//9.找出第一个大于目标值的索引
//10.lc33 搜索旋转排序数组
 */
public class erFenFa {
    ///////////////////////
    //标准二分查找目标数据的算法////
    ////////////////////////
    /*
     对于有序数组，二分法查找
     mid = left +(right-left)/2 或者 left + ((right-left>>1))
     位运算更快一点
      */
    public static int binarySearch(int[] nums,int target,int left, int right){
        while(left<=right){
            int mid  =left+((right-left)>>1);
            if(nums[mid]==target){
                return mid;
            }else if(nums[mid]<target){
                left = mid+1;
            }else if(nums[mid]>target){
                right = mid-1;
            }
        }
        return -1;
    }
    public int binary_search(int[]nums,int target){
        int left=0;
        int right =nums.length-1;
        while(left<=right){
            int mid = left+(right-left)/2;
            if(nums[mid]==target){
                return mid;
            }else if(nums[mid]<target){
                left = mid+1;
            }else if(nums[mid]>target){
                right = mid-1;
            }
        }
        return -1;

    }

     public class TreeNode {
     int val = 0;
     TreeNode left = null;
     TreeNode right = null;

     public TreeNode(int val) {
     this.val = val;

     }
     }
    ////////////////
    //1.丑数
    ///////////////
    //把只包含质因子2、3、5的数称为丑数
    //求按从小到大顺序的第n个丑数
    public int getUglyNumber_Solution(int index){
        if(index<=0) return 0;
        int[] result = new int[index];
        //初始化指向三个指向潜在成为最小丑数的位置
        int p2=0,p3=0,p5=0;
        result[0] = 1;
        for(int i=1;i<index;i++){
            result[i] = Math.min(result[p2]*2,Math.min(result[p3]*3,result[p5]*5));
            if(result[i]==result[p2]*2) p2++; //防止重复，每个if都要走到
            if(result[i]==result[p3]*3) p3++;
            if(result[i]==result[p5]*5) p5++;
        }
        return result[index-1];
    }

    ////////////////
    //2.旋转数组的最小数字
    ////////////////
    public  int minNumberInRotateArray(int[] array){
        if(array.length==0) return 0;
        int l=0;
        int r=array.length-1;
        while(l<r-1){
            int mid = (l+r)>>1;
            if(array[mid]>=array[l]){
                l=mid; //说明mid所在位置是在第一个非递减子数组中
            }else if(array[mid]<=array[r]){
                r=mid; //说明mid所在位置在第二个非递减子数组中
            }
        }
        return array[r];

    }
    ////////////////
    //3.二分查找
    ////////////////
    //给定一个升序数组，和目标值，返回其在数组中的下标，否则返回-1
    public int search(int[]nums,int target){
        int low = 0;
        int high = nums.length-1;
        int mid = 0;
        while(low<=high){
            mid = low+(high-low)/2; //中位数索引
            if(nums[mid]==target){ //找到了
                while(mid!=0&&nums[mid-1]==nums[mid]){ //存在中位数相同的两个，指针往回
                    mid--;
                }
                return mid;

            }else if(nums[mid]>target){ //中位数大于目标数，对于递增数组来说说明目标值在左边，移动高指针在中位数左边
                high = mid-1;

            }else if(nums[mid]<target){ //中位数小于目标数，对于递增数组来说说明目标值在右边，移动低指针在中位数右边
                low = mid +1;
            }

        }return -1;


    }
    ///////////////
    //4.完全二叉树节点数
    ///////////////
    //
    //给定一个完全二叉树的头结点，返回这棵树的节点个数，如果完全二叉树的节点数为n,实现时间复杂度小于O(n)的方法
    //完全二叉树的节点个数等于一个满二叉树的节点个数+一个完全二叉树的节点个数
    //如果当前的节点为null,自然以此为根的二叉树节点个数为0
    public int nodeNum(TreeNode head){
         if(head==null){
             return 0;
         }
         int leftHeight = completeTreeHeight(head.left);
         int rightHeight = completeTreeHeight(head.right);
         if(leftHeight==rightHeight){ //说明至少左子树是满二叉树
             return (int) Math.pow(2,leftHeight)+nodeNum(head.right);
         }else{//左右子树高度不等，右子树一定是满二叉树
             return  (int) Math.pow(2,rightHeight)+nodeNum(head.left);
         }

    }
    //完全二叉树高度，一直往下沿着左孩子走
    public int completeTreeHeight(TreeNode root){
         int count = 0;
         while(root!=null){
             count++;
             root = root.left;
         }
         return count;
    }

    /////////////////////
    //5.求平方根
    /////////////
    //实现函数int sqrt(int x)
    //初始范围1到x,mid*mid<=x&&(mid+1)*(mid+1)>x时，返回结果
    //mid*mid<x,在右半部分寻找
    //mid*mid>x，在左半部分寻找
    public int sqrt (int x){
         if(x<=0){
             return 0;
         }
         int left = 1;
         int right = x;
         while(true){
             int middle = (left+right)>>1;
             if(middle<=x/middle && (middle+1)>x/(middle+1)){
                 return (int) middle;
             }else if(middle <x/middle){ //在右半部分寻找
                 left = middle +1;
             }else{                      //在左半部分寻找
                 right = middle -1;
             }
         }

    }

    /////////////////
    //6.在长度相等的排序数组中找到中位数，数组长度为奇数返回n/2+1，否则返回n/2
//    public int findMedianinTwoSortedAray (int[] arr1, int[] arr2){
//
//    }
    ///////////////
    //7. lc35
    //////////////
    //排序数组想二分
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length-1;
        while(left<=right){
            int mid = left +(right-left>>1);
            if(nums[mid]==target){
                return mid;
            }else if(nums[mid]>target){
                right = mid-1;
            }else if(nums[mid]<target){
                left = mid+1;
            }
        }
        return left;
    }


    ////////////////
    //8.lc34
    ////////////
    public int[] searchRange(int[] nums, int target) {
        int upper = upperBound(nums,target);
        int low = lowerBound(nums,target);
        //不存在情况
        if (upper < low) {
            return new int[]{-1,-1};
        }
        return new int[]{low,upper};
    }
    //计算下边界
    int lowerBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            //这里需要注意，计算mid
            int mid = left + ((right - left) >> 1);
            if (target <= nums[mid]) {
                //当目标值小于等于nums[mid]时，继续在左区间检索，找到第一个数
                right = mid - 1;

            }else if (target > nums[mid]) {
                //目标值大于nums[mid]时，则在右区间继续检索，找到第一个等于目标值的数
                left = mid + 1;
            }
        }
        return left;
    }
    //计算上边界
    int upperBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (target >= nums[mid]) {
                //目标值大于nums[mid]时，则在右区间继续检索，找到第一个等于目标值的数
                left = mid + 1;
            }else if (target < nums[mid]) {
                right = mid - 1;
            }
        }
        return right;
    }

    //////////
    //9.找出第一个大于目标元素的索引
    //////////////
    public static int lowBoundnum1(int[] nums,int target,int left, int right){
        while (left <= right){
            int mid = left +((right-left)>>1);
            //大于目标值
            if (nums[mid] > target){
                //mid指到最左边或者数组中包含target且是mid的前一位
                if(mid==0||nums[mid-1]<=target){
                    return mid;
                }else{
                    right = mid-1;

                }

            }else if(nums[mid]<=target){
                left = mid+1;
            }
        }
        //所有元素小于目标元素
        return -1;
    }

    /*
    //10.lc33 搜索旋转排序数组
     */
    public int searchInRoate(int[] nums, int target){
        int left = 0;
        int right = nums.length-1;
        while (left<=right){
            int mid = left +((right-left)>>1);
            if(nums[mid]==target){
                return  mid;
            }
            //落在同一个数组的情况，同落在数组1或者数组2
            if(nums[mid]>=nums[left]){
                //target 落在 left 和 mid 之间，则移动我们的right，完全有序的一个区间内查找
                //落在mid左边，移动右指针
                if (nums[mid] > target && target >= nums[left]){
                    right = mid-1;
                }
                // target 落在right和 mid 之间，有可能在数组1， 也有可能在数组2
                //落在mid右边，移动左指针
                else if (target > nums[mid] || target < nums[left]){
                    left = mid+1;
                }
                //不落在同一数组的情况，left 在数组1， mid 落在 数组2
            }else if (nums[mid] < nums[left]){
                //有序的一段区间，target 在 mid 和 right 之间
                if (nums[mid] < target && target <= nums[right]){
                    left = mid + 1;
                }// 两种情况，target 在left 和 mid 之间
                else if (target < nums[mid] || target > nums[right]){
                    right = mid - 1;
                }
            }

        }
        return -1;
    }







}
