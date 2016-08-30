/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.uitle;

 public abstract class MyCallBack implements ICallBack{
	 
	 
}
 interface ICallBack{
	 
	 void httpCallBackSucc(int tag, Object src) ;
//	 void httpCallBackSucc(String src,int tag) ;
	 void httpCallBackErr(int tag, Object src);
 }
