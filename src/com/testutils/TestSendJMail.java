package com.testutils;

import org.junit.Test;

import com.util.SendJMail;

public class TestSendJMail {
	@Test
	public void testSendJMail() {
		String emailMsg = "���û��������<a href='#'>����</a>��ɼ��������";
		SendJMail.sendMail("menghsh@163.com", emailMsg );
		System.out.println("successful");
	}
}
