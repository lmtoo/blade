package cn.accessbright.blade.core;

import java.util.UUID;

/**
 * id生成器
 * 
 * @author Neusoft
 * 
 */
public class IdGenerator {
	public static String randomUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
