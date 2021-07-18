package com.company;
/**************************
 * 哈希
 */
//1.两数之和
//2.给一个数组arr,找到其中最长无重复子串

import java.util.HashMap;
import java.util.HashSet;

public class Hash {
    ///////////
    //1.两数之和
    ///////////
    public int[] twoSum (int[] numbers, int target){
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<numbers.length;i++){
            int num = target-numbers[i];
            if(map.containsKey(num)){
                return new int[] {map.get(num)+1,i+1};
            }
            map.put(numbers[i],i);
        }
     return null;
    }

    //////////////
    //2.找到最长无重复子串
    ///////////////
    public int maxLength (int[] arr){
        if(arr.length==0) return 0;
        if(arr.length==1) return 1;
        HashSet<Integer> set = new HashSet<>();
        int l=0,r=0; //左右两个指针
        int cnt=1; //计数器
        while(l<arr.length && r<arr.length){
            if(set.contains(arr[r])==false){//hashset中不包含
                set.add(arr[r]);
                r++;
                cnt = Math.max(r-l,cnt);
            }else{
                set.remove(arr[l]);
                l++;
            }
        }
        return  cnt;
    }
}
