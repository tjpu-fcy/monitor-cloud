package com.common;

//消息队列节点数据
public class QueueData {

	//消息标识 1：短信消息  2：微信消息 3:邮件消息
	private int number;

	//消息内容
	private String context;

	//消息接收人
	private String name;



	//消息接收人账号  微信：账号  短信：手机号   邮件：邮箱账户
	private String account;


	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}




}
