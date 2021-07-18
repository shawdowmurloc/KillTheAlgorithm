package com.company;
/**************************
 * 滑动窗口问题
 * 1.lc76  最小覆盖字串
 * 2.lc567 字符串排列
 * 3.lc438 统计字母异位词
 * 4.lc3 最长无重复子串
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//滑动窗口的问题
public class huangDongChuangKou {
//    public void (String s, String t){
//        unordered_map<char, int> need, window;
//        for (char c : t) need[c]++;
//
//        int left = 0, right = 0;
//        int valid = 0;
//        while (right < s.size()) {
//            // c 是将移入窗口的字符
//            char c = s[right];
//            // 右移窗口
//            right++;
//            // 进行窗口内数据的一系列更新
//        ...
//
//            /*** debug 输出的位置 ***/
//            printf("window: [%d, %d)\n", left, right);
//            /********************/
//
//            // 判断左侧窗口是否要收缩
//            while (window needs shrink) {
//                // d 是将移出窗口的字符
//                char d = s[left];
//                // 左移窗口
//                left++;
//                // 进行窗口内数据的一系列更新
//            ...
//            }
//        }
//
//    }//当移动right增大窗口的时候，加入字符时，应该更新哪些数据？
       //什么条件下窗口应该停止扩大？开始移动left减小窗口？
       //当移动left减小窗口的时候，移除字符时，应该更新哪些数据？
       //我们要的结果应该在扩大窗口时还是缩小窗口时进行更新？
/**********************
 * 基本思想：用两个字典分别维护窗口中字符的统计数量、以及被求解子串中字符的统计数量
 * 用双指针遍历主字符串，双指针初始值均为0，窗口的范围是[left,right)左闭右开
 * 遍历过程中不断增大、缩小窗口并且更新状态
 *
 */
    ///////////////
    //1.最小覆盖子串问题
    /////////////////
    public String minWindow (String S, String T) {
        // write code here
        //边界条件
        if (S == null || S == "" || T == null || T == "" || S.length() < T.length()){
            return "";
        }
        //用来统计T中每个字符出现次数
        int[] needs = new int[128];
        //用来统计滑动窗口中每个字符出现次数
        int[] window = new int[128];
        for (int i = 0; i < T.length(); i++){
            needs[T.charAt(i)]++;
        }
        //左右两个指针,从0开始
        int left = 0;
        int right = 0;
        String res = "";
        //目前有多少个字符
        int count = 0;
        //用来记录最短需要多少个字符。
        int minLength = S.length() + 1;

        while (right < S.length()){
            char ch = S.charAt(right);
            window[ch]++;
            if (needs[ch] > 0 && needs[ch] >= window[ch]){
                count++;
            }
            //移动到不满足条件为止
            while(count==T.length()){
                //如果一个字符进入窗口，应该增加count计数器，如果移出窗口时，应该减小count计数器
                ch = S.charAt(left);
                if (needs[ch] > 0 && needs[ch] >= window[ch]){
                    count--;
                }
                if (right - left + 1 < minLength){
                    minLength = right - left + 1;
                    res = S.substring(left, right + 1);
                }
                window[ch]--;
                left++;
            }
            right++;
        }
        return res;
    }

    //解法2 拉不拉东框架
    public String minWindow1(String s, String t) {
        HashMap<Character, Integer> need = new HashMap<Character, Integer>();
        HashMap<Character, Integer> window = new HashMap<>();
        for (char c :  t.toCharArray()) need.put(c, need.getOrDefault(c, 0) + 1);

        int left = 0, right = 0;
        int valid = 0;
        // 记录最小覆盖字串的起始索引及长度
        int start = 0, len = Integer.MAX_VALUE;
        while (right < s.length()) {
            char c = s.charAt(right);
            right++;
            // 判断取出的字符是否在字串中
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c,0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }
            // 判断是否需要收缩（已经找到合适的覆盖串）
            while (valid == need.size()) {
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }

                char c1 = s.charAt(left);
                left++;
                if (need.containsKey(c1)) {
                    if (window.get(c1).equals(need.get(c1))) {
                        valid--;
                    }
                    window.put(c1, window.getOrDefault(c1, 0) - 1);
                }

            }
        }

        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    ///////////////////
    //2.字符串的排列
    //////////////////
    //第二个字符串中的子串是否是第一个字符串的排列
    public boolean checkInclusion(String s1, String s2) {
        //构建need计数器和window计数器
        HashMap<Character,Integer> window = new HashMap<>();
        HashMap<Character,Integer> need = new HashMap<>();
        int left=0,right=0;
        int valid=0;
        //表示窗口中满足need条件的字符个数
        //如果need.size和valid大小相同，则说明窗口条件已经满足，已经完全覆盖串
        //将s1中的字符放入need map里
        for(char a:s1.toCharArray()){
            need.put(a,need.getOrDefault(a,0)+1);
        }

        while(right<s2.length()){
            char c=s2.charAt(right);
            right++;
            //进行窗口内一系列数据更新
            if(need.containsKey(c)){
                window.put(c,window.getOrDefault(c,0)+1);
                if(need.get(c).equals(window.get(c))){
                    valid++;
                }
            }
            //判断左窗口是否要收缩
            while(right-left>=s1.length()){
                if(valid==need.size()){
                    return true;
                }
                char c1=s2.charAt(left);
                left++;
                if(need.containsKey(c1)){
                    if(need.get(c1).equals(window.get(c1))){
                        valid--;
                    }
                    window.put(c1,window.get(c1)-1);
                }
            }

        }
        //未找到
        return false;
    }

    ///////////////
    //3.找到所有字母异位词
    //////////////////
    public List<Integer> findAnagrams(String s, String p){
        HashMap<Character,Integer> window = new HashMap<>();
        HashMap<Character,Integer> need = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        for(char c:p.toCharArray()){
            need.put(c,need.getOrDefault(c,0)+1);
        }
        int left=0,right=0;
        int valid=0;
        while(right<s.length()){
            char z = s.charAt(right);
            right++;
            //判断字符是否在串p中
            if (need.containsKey(z)){
                window.put(z,window.getOrDefault(z,0)+1);
                if(window.get(z).equals(need.get(z))){
                    valid++;
                }
            }
            //判断是否要收缩左边界
            while(right - left >= p.length()){
                //符合条件时,把起始索引加入
                if (valid == need.size()){
                    list.add(left);
                }
                //左边收缩
                char y =s.charAt(left);
                left++;
                //判断收缩的该字符是否在p串里
                if (need.containsKey(y)){
                    if(window.get(y).equals(need.get(y))){
                        valid--;
                    }
                    window.put(y, window.getOrDefault(y, 0) - 1);
                }
            }

        }
        return list;

    }




    ////////////////////
    //4.最长无重复子串
    ////////////////////
    public int lengthOfLongestSubstring(String s){

        if(s.length()==0) return 0;
        HashMap<Character,Integer> map = new HashMap<>();
        int max = 0;
        int left = 0;
        for(int i=0;i<s.length();i++){
            if(map.containsKey(s.charAt(i))){
                left = Math.max(left,map.get(s.charAt(i))+1);

            }
            map.put(s.charAt(i),i);
            max = Math.max(max,i-left+1);
        }
        return max;


    }


}
