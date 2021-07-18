package com.company;
/**************************
 * 链表问题
 * 链表题目汇总
 * 1.(迭代法)翻转单链表
 * 2.判断链表中是否有环
 * 3.合并有序链表
 * 4.链表中每k个进行翻转
 * 5.链表中环的入口节点
 * 6.删除链表中倒数第n个节点
 * 7.两个链表生成相加链表
 * 8.两个链表的第一个公共结点
 * 9.无序单链表排序
 * 10.（递归法）翻转单链表
 * 11.（递归法）翻转链表前n个节点
 * 12.（递归法）翻转链表的一部分，给出一个一个区间[m,n]翻转这一部分的链表
 * 13.删除有序链表中重复的元素
 * 14.链表的奇偶重排
 * 15.判断链表是否是回文
 * 16.lc82 删除链表中的重复元素2
 *
 */
import java.util.*;

//定义链表类
public class ListNode {
    int val;
    ListNode next = null;
    ListNode(int val){
        this.val = val;
    }

    /**
    //1.经典翻转单链表，输出新链表的表头
    */
    public ListNode reverseList(ListNode head){
        //初始化pre指针，用于记录当前节点的前一个节点地址
        ListNode pre = null;
        //初始化next指针，用于记录当前节点的下一个节点地址
        ListNode next = null;
        while(head != null){
            //将next指针记录当前节点的下一个节点的地址
            next = head.next;
            //让当前节点与链表断开，指向前一个节点
            head.next = pre;
            //pre指针指向当前节点
            pre = head;
            //head指向远链表中head的下一个节点地址
            head = next;
        }
        return pre;
    }
    //解法2
    public ListNode reverse(ListNode head){
        ListNode pre = null;
        ListNode cur = head;
        ListNode next = head;
        while (cur!=next){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
    //2.判断链表中是否有环
    //快慢指针发判断是否有环
    */
    public boolean hasCycle(ListNode head){
        ListNode p = head;
        ListNode q = head;
        while(p!=null && q!=null){
            p = p.next.next;
            q = q.next;
            if(p==q){
                return true;
            }
        }
        return false;

    }
    /**
    //////////////////////
    //3.合并有序链表
    //将两个有序的链表合并为一个新链表，新链表是通过拼接两个链表节点生成的，并且合并后新链表有序
    /////////////////////
     */
    public ListNode mergeTwoList(ListNode l1,ListNode l2){
        if(l1==null){
            return l2;
        }
        if(l2==null){
            return l1;
        }
        ListNode dun = new ListNode(0);
        ListNode current = dun;
        while(l1 !=null && l2!=null){
            //比较一下，哪个小就放进新的链表里
            if(l1.val<l2.val){
                current.next = l1;
                l1 = l1.next;
            }else{
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        //最后剩余一个不为空的链表放在新的链表最后
        current.next = l1==null?l2:l1;
        return dun.next;
    }
    /**
    ///////////////////////
    //4.链表每k个一组进行翻转
    //////////////////////
    *///用栈来实现
    public ListNode reverseKGroup(ListNode head, int k){
        Stack<ListNode> stack = new Stack<ListNode>();
        //初始化一个新的链表存放最终的结果
        ListNode res = new ListNode(0);
        //为新链表定义一个指针，防止后续操作改变头结点
        ListNode p = res;
        //循环原有的链表
        while (true){
            //计数器，为每次翻转计数
            int count = 0;
            //定义指针操作原始链表
            ListNode temp = head;
            //循环入栈
            while(temp !=null && count<k){
                stack.push(temp);
                temp = temp.next;
                count++;
            }
            //判断该次翻转是否达到要求，防止因temp==null跳出循环条件
            if(count !=k){
                //表示剩下的节点不够k个，直接将剩余节点插入末尾
                p.next = head;
                break;
            }
            //出栈，翻转链表
            while(!stack.isEmpty()){
                p.next = stack.pop();
                p = p.next;
            }
            //重置下一次操作的初始节点
            p.next = temp;
            head = temp;

        }
        return res.next;
    }
    /**
    /////////////////////////
    //5.链表中环的入口节点//
    ////////////////////////
     */
    public ListNode detectCycle(ListNode head){
        ListNode slow = head;
        ListNode fast = head;
        while (slow!=null && fast!=null){
            //快慢指针找环
            slow = slow.next;
            fast = fast.next.next;
            if(fast==slow){//找到相遇点了
                ListNode slow2 = head;
                while (slow2 != slow){//设置新指针以相同的速度从起始位置出发
                    slow = slow.next;
                    slow2 = slow2.next;
                }
                return slow;//找到并返回
            }
        }
        return null;
    }

    /**
     *法2
     */
    public ListNode detectCycle2(ListNode head) {
        ListNode fast = head, slow = head;
        while (true) {
            if (fast == null || fast.next == null) return null;
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) break;
        }
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return fast;
    }

    /**
    /////////////////////
    //6.删除链表中倒数第n个节点，一共k个节点，最后返回头结点
    //即删除正数第k-n+1个节点
    //双指针法，当走到倒数第n+1个节点，跳过指向next.next
    /////////////////////
    //当遍历到要删除节点的前一位时候，将指针指向next.next，就删除了该节点
     */
    public ListNode removeNthFromEnd(ListNode head,int n){
        if(head == null || n<1){
            return null;
        }
        ListNode fast = head;
        ListNode slow = head;

        for(int i=0; i<n; i++){
            fast = fast.next;
        }
        //如果n的值等于链表长度，直接返回去掉头结点的链表
        if(fast==null){
            return head.next;
        }
        //走到倒数第n+1个节点
        while (fast.next !=null){
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return head;
    }
    /**
    /////////////////
    //7.两个链表生成相加链表
    /////////////////
    //用两个栈来实现，栈后进先出，满足加法
     */
    public ListNode addList(ListNode head1,ListNode head2){
        Stack<Integer> stack1 = new Stack<Integer>();
        Stack<Integer> stack2 = new Stack<Integer>();//创建两个栈来存储链表中的数据
        //链表1和链表2中数据压入栈
        while(head1 !=null){ //要保证头结点不为空
            stack1.push(head1.val);
            head1 = head1.next;
        }
        while(head2 != null){ //保证头结点不为空
            stack2.push(head2.val);
            head2 = head2.next;
        }
        ListNode res = null; //用于返回的链表
        int cnt = 0; //用于存储相加所产生进位1；
        while(!stack1.empty() || !stack2.empty()){//从两个栈中各取一个
            int x1 = stack1.isEmpty()?0:stack1.pop();
            int x2 = stack2.isEmpty()?0:stack2.pop();
            int sum = x1+x2+cnt;//当前这一位的总和
            cnt = sum/10; //查看是否有进位
            ListNode tempNode = new ListNode(sum%10);
            tempNode.next = res;
            res = tempNode;

        }
        //还存在多进的一位
        if(cnt>0){
            ListNode tempNode = new ListNode(cnt);
            tempNode.next = res;
            res = tempNode;
        }
        return res;
    }
    /**
    /////////////////////
    //8.两个链表的第一个公共结点
    //////////////////////
    //让两个指针分别在两个链表中走，
     */
    public ListNode findFirstCommonNode(ListNode pHead1,ListNode pHead2){
        if(pHead1==null || pHead2==null){
            return null;
        }
        ListNode p1 = pHead1; //指向头结点的指针
        ListNode p2 = pHead2;
        while(p1!=p2){
            p1 = p1.next;
            p2 = p2.next;
            if(p1!=p2){
                if(p1==null){ //链表1走完了，链表1头指针指向链表2；
                    p1 = pHead2;
                }
                if(p2==null){//链表2走完了，链表2头指针指向链表1；
                    p2 = pHead1;
                }
            }
        }
        return p1;
    }
    /**
    //////////////////////
    //9.给定一个无序单链表，实现链表的升序排序
    /////////////////////
     */
    //解法一，把链表的元素值都拿出来放arralist里，然后排序，最后连接
    public ListNode sortInList(ListNode head){
        if(head==null || head.next==null){
            return  null;
        }
        ArrayList<Integer> resultList = new ArrayList<Integer>();
        ListNode temp = head; //指向头节点的指针
        while(head !=null){ //头结点不为空
            resultList.add(temp.val);
            temp = temp.next;
        }
        //list内升序排序
        resultList.sort((a,b) -> {return a-b;});
        ListNode tmp = head;
        int i = 0;
        while (tmp!=null){
            tmp.val = resultList.get(i++);
            tmp = tmp.next;
        }
        return head;
    }

    //////////////
    //解法2
    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        int len = getLength(head);
        int[] nums = ListNode2Array(head,len);
        Arrays.sort(nums);
        ListNode newHead = new ListNode(-1);
        ListNode tmp = newHead;
        for(int i = 0;i <= len - 1;i++){
            newHead.val = nums[i];
            if(i < len - 1){
                newHead.next = new ListNode(-1);
                newHead = newHead.next;
            }
        }
        return tmp;
    }
    public int getLength(ListNode head){
        int len = 0;
        while(head != null){
            len++;
            head = head.next;
        }
        return len;
    }
    public int[] ListNode2Array(ListNode head,int len){
        int[] nums = new int[len];
        int i = 0;
        while(head != null){
            nums[i] = head.val;
            head = head.next;
            i++;
        }
        return nums;
    }
    /**
    //////////////
    //10.（递归法）翻转整个链表
    ///////////////////
     */
    public ListNode reverseDiGui(ListNode head){
        if(head.next==null) return head;
        ListNode last = reverseDiGui(head.next);
        head.next.next= head;
        head.next = null;
        return last;
    }
    /**
    /////////////////
    //11.翻转链表前n个节点
    /////////////////
     */
    ListNode successor = null; //后驱节点
    public ListNode reverseN(ListNode head,int n){
        if(n==1){
            //记录第n+1个节点
            successor = head.next;
            return head;
        }
        //以head.next为起点，需要翻转前n-1个节点
        ListNode last = reverseN(head.next,n-1);
        head.next.next = head;
        head.next = successor;
        return last;
    }
    /**
    //////////////
    //12.翻转链表的一部分，给出一个一个区间[m,n]翻转这一部分的链表、
    ////////////////
    //如果m等于1，也就是翻转前n个节点，可以利用上面的方法
    //如果m！=1,把head视为索引1，那么是从第m个开始翻转，
    //对于head.next，是从第m-1个开始翻转
    //对于head.next.next,是从第m-2个开始
    //可以利用迭代法来解决
     */
    public ListNode reverseBetween(ListNode head,int m,int n){
        if(m==1){
            return reverseN(head,n);
        }
        head.next = reverseBetween(head.next,m-1,n-1);
        return head;
    }
    /**
    ///////////////////////
    //13.删除有序链表中重复的元素
    ///////////////////////
     */
    public ListNode deleteDuplicates(ListNode head){
        if(head==null) return head;
        ListNode temp = head;
        while(temp.next != null){
            if(temp.val==temp.next.val){
                temp.next = temp.next.next;
            }else{
                temp = temp.next;
            }
        }
        return head;
    }
    /**
    //////////////////
    //14.链表奇偶重排
    //////////////////
     */
    public ListNode oddEvenList (ListNode head){
        if(head==null || head.next==null){
            return head;
        }
        /*
          odd 指向奇数节点的指针
          oddHead 指向初始奇数节点的头指针
          even 指向偶数节点的指针
          evenHead 指向初始偶数节点的头指针
         */
        ListNode odd = head,oddHead =head;
        ListNode even = head.next,evenHead = head.next;
        while (even!=null && even.next !=null){
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;

        }
        odd.next = evenHead;
        //返回奇数节点的初始头指针
        return oddHead;

    }
    public ListNode oddEvenList1(ListNode head){
        if(head==null||head.next==null){
            return head;
        }
        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;
        while(odd.next!=null && even.next!=null){
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }
    /**
    /////////////////////
    //15.判断链表是否回文
    /////////////////
     */
    public boolean isPail (ListNode head){
        if(head==null) return false;
        Stack<ListNode> stack = new Stack<ListNode>();
        ListNode cur = head;
        while (cur!=null){
            stack.push(cur);
            cur = cur.next;
        }
        while (head!=null){
            if(head.val!=stack.pop().val){
                return false;
            }
            head = head.next;
        }
        return true;
    }
    /**
    ////////////////
    //16.lc82
    /////////////////
    //建一个「虚拟头节点」dummy 以减少边界判断，往后的答案链表会接在 dummy 后面
    //使用 tail 代表当前有效链表的结尾
    //通过原输入的 head 指针进行链表扫描
    //我们会确保「进入外层循环时 head 不会与上一节点相同」，因此插入时机：
    //head 已经没有下一个节点，head 可以被插入
    //head 有一下个节点，但是值与 head 不相同，head 可以被插入
     */
    public ListNode deleteDuplicate2(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }
        // 建立一个哑结点
        ListNode dummy = new ListNode(-1);
        ListNode tail = dummy;
        while (head != null){
            // 进入循环时，确保了 head 不会与上一节点相同
            if (head.next == null || head.val != head.next.val) {
                tail.next = head;
                tail = head;
            }
            // 如果 head 与下一节点相同，跳过相同节点
            while (head.next != null && head.val == head.next.val) head = head.next;
            head = head.next;
        }
        tail.next = null;
        return dummy.next;
    }
    //解法2
    public ListNode deleteDuplicates22(ListNode head){
        if (head == null) return head;
        //设置哑结点
        ListNode dummyHead = new ListNode(0);
        //哑节点的下一个指向head
        dummyHead.next = head;
        //设置cur指向当前dummy节点
        ListNode cur = dummyHead;
        while(cur.next!=null && cur.next.next!=null){
            if(cur.next.val == cur.next.next.val){// 如果遇到后面两个连续数值相等，那么记录下这个数值
                int x = cur.next.val;
                while (cur.next!=null && cur.next.val == x){
                    // 将重复元素删除，这里设置while循环，可以删去多个重复元素
                    // 在删除元素的过程中，一定要好好理解cur->next的变化，这里很容易出错
                    cur.next = cur.next.next;
                }
            }else{
                cur = cur.next; // 如果没发现重复元素，那么正常推进
            }
        }
        return dummyHead.next;
    }

    /**
     * lc24 两两交换链表中的节点
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        if(head==null || head.next==null) {
            return head;
        }
        //用stack保存每次迭代的两个节点
        Stack<ListNode> stack = new Stack<ListNode>();
        ListNode p = new ListNode(-1);
        ListNode cur = head;
        //head指向新的p节点，函数结束时返回head.next即可
        head = p;
        while(cur!=null && cur.next!=null) {
            //将两个节点放入stack中
            stack.add(cur);
            stack.add(cur.next);
            //当前节点往前走两步
            cur = cur.next.next;
            //从stack中弹出两个节点，然后用p节点指向新弹出的两个节点
            p.next = stack.pop();
            p = p.next;
            p.next = stack.pop();
            p = p.next;
        }
        //注意边界条件，当链表长度是奇数时，cur就不为空
        if(cur!=null) {
            p.next = cur;
        } else {
            p.next = null;
        }
        return head.next;
    }









}
