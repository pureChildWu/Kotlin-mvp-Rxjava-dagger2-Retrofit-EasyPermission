package com.gzinfo.kotlintiktok;

import java.util.Queue;

/**
 * @ClassName:MyCircularDeque
 * @Author:CreatBy wlh
 * @Time:2020/8/4 16点
 * @Email:m15904921255@163.com
 * @Desc:TODO
 */
class MyCircularDeque {
    class node{
        int val;
        node next = null;
        node pre = null;

        node(int v){
            this.val = v;
        }
    }

    node firsthead = null;
    node lasthead = null;
    int capacity;  //链表的总节点容量
    int count;   //链表的当前节点容量

    /** Initialize your data structure here. Set the size of the deque to be k. */
    public MyCircularDeque(int k) {
        this.capacity = k;
        this.count = 0;
    }

    /** Adds an item at the front of Deque. Return true if the operation is successful. */
    //头插法 保持队列的先进先出特性
    public boolean insertFront(int value) {
        if(isFull()){
            return false;
        }
        node nd = new node(value);
        //只要firsthead为空，那么lasthead必定为空
        if(firsthead==null){
            firsthead = nd;
            lasthead = nd;
        }else {
            //只要firsthead不空 lasthead肯定也不空
            nd.next = firsthead;
            firsthead.pre = nd;
            firsthead = nd;
        }
        count++;
        return true;
    }

    /** Adds an item at the rear of Deque. Return true if the operation is successful. */
    public boolean insertLast(int value) {
        if(isFull())
            return false;
        node nd = new node(value);
        if(lasthead==null){
            lasthead = nd;
            firsthead = nd;
        }else {
            nd.pre = lasthead;
            lasthead.next = nd;
            lasthead = nd;
        }
        count++;
        return  true;

    }

    /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    public boolean deleteFront() {
        if(isEmpty())
            return false;
        if(count==1){
            firsthead = null;
            lasthead = null;
        }else {
            firsthead = firsthead.next;
            firsthead.pre = null;
        }
        count--;
        return true;
    }

    /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
    public boolean deleteLast() {
        if (isEmpty())
            return false;
        if (count==1){
            firsthead = null;
            lasthead = null;
        }else {
            lasthead = lasthead.pre;
            lasthead.next = null;
        }
        count --;
        return true;

    }

    /** Get the front item from the deque. */
    public int getFront() {
        if(this.firsthead == null)
            return -1;
        else
            return firsthead.val;
    }

    /** Get the last item from the deque. */
    public int getRear() {
        if(this.lasthead == null)
            return -1;
        else
            return this.lasthead.val;
    }

    /** Checks whether the circular deque is empty or not. */
    public boolean isEmpty() {
        if(this.count == 0)
            return true;
        else
            return false;
    }

    /** Checks whether the circular deque is full or not. */
    public boolean isFull() {
        if(this.count==this.capacity)
            return true;
        else
            return false;
    }
}
