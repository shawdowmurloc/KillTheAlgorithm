package com.company;
/**
 1.冒泡排序
 2.快速排序
 3.归并排序
 4.简单选择排序
 5.直接插入排序
 6.希尔排序
 */
public class paiXu {
    /*
    冒泡
     */
    public int[] bubbleSortArray(int[] nums){
        int len = nums.length;
        for(int i=0;i<len;i++){
            //第i趟
            for(int j=0;j<len-i-1;j++){
                //前一个比后一个大就交换
                if(nums[j]>nums[j+1]){
                    swap(nums,j,j+1);
                }
            }
        }
        return nums;
    }
    public void swap(int[]nums,int i,int j){
        int temp =nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    /**
    快排 最好时间复杂度O(nlogn) 最坏时间复杂的O(n^2)
    先从数组中找一个基准数，让其他比它大的元素移动到数列一边，比他小的元素移动到数列另一边，从而把数组拆解成两个部分。
    再对左右区间重复第二步，直到各区间只有一个数。
     */
    public void quickSort (int[] nums, int low, int hight) {
        if (low < hight) {
            int index = partition(nums,low,hight);
            quickSort(nums,low,index-1);
            quickSort(nums,index+1,hight);
        }

    }
    //填坑法
    public int partition (int[] nums, int low, int high){
        int pivot = nums[low];
        while(low<high){
            while (low<high&&nums[high]>=pivot) {
                high--;
            }
            //填坑
            if(low<high){
                nums[low] = nums[high];
            }
            while(low<high&&nums[low]<=pivot){
                low++;
            }
            if(low<high){
                nums[high] = nums[low];
            }
        }
        nums[low] = pivot;
        return low;

    }
    //双指针法
    public int partition1 (int[] nums, int low, int hight) {
        int pivot = nums[low];
        int start = low;

        while (low < hight) {
            while (low < hight && nums[hight] >= pivot) hight--;
            while (low < hight && nums[low] <= pivot) low++;
            if (low >= hight) break;
            swap(nums, low, hight);
        }
        //基准值归位
        swap(nums,start,low);
        return low;
    }



    /**
    归并排序 平均时间复杂度O(nlogn)
    将两个或者两个以上的有序表合成一个新的有序表，思想是分治思想
    将一个大问题分解称若干个小的子问题来解决
     */
    public void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + ((right - left) >> 1);
            mergeSort(arr,left,mid);
            mergeSort(arr,mid+1,right);
            merge(arr,left,mid,right);
        }
    }
    //归并
    public void merge(int[] arr,int left, int mid, int right){
        //第一步，定义一个新的临时数组
        int[] tempArr = new int[right -left + 1];
        //定义两个指针。分别指向两个不同的小数组
        int temp1 = left, temp2 = mid + 1;
        int index = 0;
        //对应第二步，比较每个指针指向的值，小的存入大集合
        while (temp1 <= mid && temp2 <= right){
            if (arr[temp1] <= arr[temp2]){
                tempArr[index++] = arr[temp1++];
            }else{
                tempArr[index++] = arr[temp2++];
            }
        }
        //对应第三步，将某一小集合的剩余元素存到大集合中
        if (temp1 <= mid) System.arraycopy(arr, temp1, tempArr, index, mid - temp1 + 1);
        if (temp2 <= right) System.arraycopy(arr, temp2, tempArr, index, right -temp2 + 1);
        //将大集合的元素复制回原数组
        System.arraycopy(tempArr,0,arr,0+left,right-left+1);
    }

    /**
     * 简单选择排序 最好最坏平均时间复杂度O(n^2)
     * 简单选择排序的思想也很容易理解，主要思路就是我们每一趟在 n-i+1 个记录中
     * 选取关键字最小的记录作为有序序列的第 i 个记录。
     */
    public int[] sortArraySimple(int[] nums){
        int len = nums.length;
        int min = 0;
        for (int i = 0; i < len; ++i){
            min = i;
            for (int j = i + 1; j < len; ++j){
                if (nums[min] > nums[j]) min = j;
            }
            if (min != i) swap(nums,i,min);
        }
        return nums;

    }

    /**
     * 直接插入排序 最好O(n) 最坏O(n^2) 平均O(n^2)
     * 将一个记录插入到已经排好序的有序表中，从而得到一个新的有序表。
     * 通俗理解，我们首先将序列分成两个区间，有序区间和无序区间，我们每次在无序区间内取一个值，
     * 在已排序区间中找到合适的插入位置将其插入，并保证已排序区间一直有序。
     *
     */
    public int[] sortArrayChaRu(int[] nums){
        //注意 i 的初始值为 1，也就是第二个元素开始
        for (int i = 1; i < nums.length; ++i){
            //待排序的值
            int temp = nums[i];
            //需要注意
            int j;
            for (j = i-1; j >= 0; --j){
                //找到合适位置
                if (temp < nums[j]){
                    nums[j+1] = nums[j];
                    continue;
                }
                //跳出循环
                break;
            }
            //插入到合适位置，这也就是我们没有在 for 循环内定义变量的原因
            nums[j+1] = temp;
        }
        return nums;
    }
    /**
     * 希尔排序 时间复杂度O(n^(1.3--2))
     * 希尔排序是插入排序的一种，又称“缩小增量排序”（Diminishing Increment Sort），
     * 是直接插入排序的高级变形，其思想简单点说就是有跨度的插入排序，
     * 这个跨度会逐渐变小，直到变为 1，变为 1 时记录也就基本有序，
     * 这时用到的也就是我们之前讲的直接插入排序了。
     *
     */
    public int[] sortArrayHill(int[] nums){
        int increment = nums.length;
        while (increment > 1){
            //这里可以设置自己的希尔增量
            increment = increment / 2;
            for (int i = 0; i < increment; ++i){
                //这快是不是有点面熟，回去看看咱们的插入排序
                for (int j = i + increment; j < nums.length; ++j){
                    int temp = nums[j];
                    int k;
                    for (k = j - increment; k >= 0; k -= increment){
                        if (temp < nums[k]){
                            nums[k+increment] = nums[k];
                            continue;
                        }
                        break;
                    }
                    nums[k+increment] = temp;
                }
            }
        }
        return nums;

    }


}

