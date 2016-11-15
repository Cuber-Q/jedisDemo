package jedisDemo;

import java.util.ArrayList;
import java.util.List;

public class MainTest {
	public static void main(String[] args){
		RedisService redisService = new RedisService("resource/redis-conf.properties");
		
		
		redisService.set("aTest", "{object:lalala哈哈哈666666}");
		
		System.out.println(redisService.get("aTest"));
		
		new MainTest().a();
	}
	
	public void a(){
		int a=0;
		b();
	}
	public void b(){
		int b=0;
		a();
	}
}
