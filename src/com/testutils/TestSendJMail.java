package com.testutils;

import org.junit.Test;

import com.util.SendJMail;

public class TestSendJMail {
	@Test
	public void testSendJMail() {
		String emailMsg = "新用户激活：请点击<a href='#'>激活</a>完成激活操作。";
		SendJMail.sendMail("menghsh@163.com", emailMsg );
		System.out.println("successful");
	}
}
