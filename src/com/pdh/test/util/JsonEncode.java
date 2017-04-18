package com.pdh.test.util;
import com.alibaba.fastjson.JSON;

public class JsonEncode {
	public static void main(String[] args) {
		Group group = new Group();
		group.setId(0L);
		group.setName("admin");

		User guestUser = new User();
		guestUser.setId(2L);
		guestUser.setName("guest");

		User rootUser = new User();
		rootUser.setId(3L);
		rootUser.setName("root");

		group.getUsers().add(guestUser);
		group.getUsers().add(rootUser);
		// 将JavaBean序列化为JSON文本
		String jsonString = JSON.toJSONString(group);
		System.out.println(jsonString);
		// 把JSON文本parse成JSONObject
		Group group2 = JSON.parseObject(jsonString, Group.class);
		System.out.println(group2.toString());
		
	}
}
