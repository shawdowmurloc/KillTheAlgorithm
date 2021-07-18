package com.company;

import java.util.ArrayList;
import java.util.HashSet;
////////////////////////
//双指针可以分为快慢指针和左右指针
//快慢指针可以判断链表中是否有环，求环的起点..
//左右指针可以用于二分查找，两数之和，翻转数组...
//1.最长无重复的子串
//2.翻转字符串
//3.滑动窗口的最大值
//4.划分链表
//5.判断链表中是否有环
//6.已知链表有环，求环的起始位置
//7.找链表的中点
//8.找链表中倒数第k个元素
//9.升序数组，两数之和是目标数
//10.翻转数组
public class shaungZhiZhen {
    public class ListNode {
        int val;
        ListNode next = null;
 }

    /////////////////////
    //1.给定一个数组，返回数组中的最长的无重复的子串长度
    public int maxLength(int[] arr){
        if(arr.length==0) return 0;
        if(arr.length==1) return 1;
        HashSet<Integer> set = new HashSet<>();
        int l=0,r=0;
        int cnt=1; //计数器
        while(l<arr.length && r<arr.length){
            if(set.contains(arr[r])==false){
                //如果set中不存在，则把arr[r]插入hashset,更新计数器，移动r指针
                set.add(arr[r]);
                r++;
                cnt = Math.max(r-l,cnt);
            }else{
                //set中存在arr[r],移动l指针，并且删除arr[l];
                //因为是求子串，所以要重新开始判断
                set.remove(arr[l]);
                l++;
            }
        }
        return  cnt;

    }

    /////////////
    //2.翻转字符串
    ////////////
    public String solve (String str){
        if(str == null||str.length()==0||str.length()==1){
            return str;
        }
        char[] arr = str.toCharArray();
        for(int i=0;i< arr.length/2;i++){
            char temp = arr[i];
            arr[i] = arr[arr.length-1-i];
            arr[arr.length-1-i] = temp;
        }
        return new String(arr);
    }
    ////////////////
    //3.滑动窗口的最大值
    ////////////////
    public ArrayList<Integer> maxInWindows(int [] num, int size){
        ArrayList<Integer> res=new ArrayList<Integer>();
        if(num.length==0|| size>num.length||size==0){//边界条件
            return res;
        }
        for(int i=0;i< num.length-size;i++){//一共有num.length-size个窗口，i是代表第几个窗格
            int max = num[i];
            for(int j=i;j<size+i;j++){//在第几个窗口内的第几个
                if(max<num[j]){
                    max = num[j];
                }
            }
            res.add(max);
        }
        return res;
    }

    ///////////////
    //4.划分链表
    ///////////////
    //给出 1→4→3→2→5→2 和x=3,返回1→2→2→4→3→5.
    public ListNode partition (ListNode head, int x) {
        // write code here
        if(head==null) return head;
        ListNode h1 = new ListNode();
        ListNode h2 = new ListNode();
        ListNode n1 = h1; //构建两个指针
        ListNode n2 = h2;
        ListNode tmp = head;
        while(tmp!=null){
            if(tmp.val<x){
                n1.next = tmp;
                n1 = tmp;
            }else{
                n2.next = tmp;
                n2 = tmp;
            }
            tmp = tmp.next;
        }
        n2.next = null;
        n1.next = h2.next;
        return h1.next;

    }
    ///////////////////
    //5.判断链表是否有环
    ///////////////////
    public boolean hasCycle(ListNode head){
        ListNode fast,slow;
        fast=slow=head;
        while(fast!=null&&fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast==slow) return true;
        }
        return false;
    }
    /////////////////////
    //6.求环的起点
    ///////////////////
    public ListNode detectCycle(ListNode head){
        ListNode fast,slow;
        fast = slow = head;
        while (fast!=null&&fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow) break; //找到重合点以后，让其中一个指针重新指向头节点
            //让他俩以相同的速度前进，再次相遇的时候，所在的节点位置就是环开始的位置
        }
        slow = head;
        while (slow!=fast){
            slow = slow.next;
            fast = fast.next;
        }
        return  slow;
    }
    ////////////////////
    //7.求链表的中点
    ////////////////////
    public ListNode middlePoint(ListNode head){
        ListNode slow,fast;
        slow = fast = head;
        while (fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return  slow;
    }

    ////////////////////
    // 8.找链表中倒数第k个元素
    /////////////////////
    //让快指针先走k,然后快慢指针同步前进，最后快指针走到链表尾部null时，
    //慢指针所在的位置就是倒数第k个节点
    public ListNode daoShuDiK(ListNode head, int k){
        ListNode fast,slow;
        fast = slow = head;
        while (k-- >0){
            fast = fast.next;
        }
        while (fast!=null){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    ////////////////
    //9.两数之和
    /////////////////
    public int[] twoSum(int[] nums,int target){
        int left=0,right = nums.length-1;
        while (left<right){
            int sum = nums[left]+nums[right];
            if(sum==target){
                return new int[]{left+1,right+1};
            }else if(sum<target){
                left++;
            }else if(sum>target){
                right--;
            }

        }return new int[]{-1,-1};
    }


    ////////////////
    //10.翻转数组
    ////////////////
    //交换前后的元素
    public void reverseNum(int[]nums){
        int left = 0;
        int right = nums.length-1;
        while (left<right){
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }

}
