package jedisDemo;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisService {
	private static JedisPool pool;
	private String filePath;
	
	public RedisService(String filePath){
		this.filePath = filePath;
		init();
	}
	
	private void init(){
		Properties prop = new Properties();     
        try{
        	System.out.println("prepare to init redisService...");
        	// 读取配置文件
        	InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            prop.load(in);     ///加载属性列表
            
            // 建立连接池配置参数
            JedisPoolConfig config = new JedisPoolConfig();

            // 设置最大连接数
            config.setMaxTotal(Integer.valueOf(prop.getProperty("maxTotal")));

            // 设置最大阻塞时间，记住是毫秒数milliseconds
            config.setMaxWaitMillis(Long.valueOf(prop.getProperty("maxWaitmillis")));

            // 设置最大空闲连接
            config.setMaxIdle(Integer.valueOf(prop.getProperty("maxIdle")));

            // 创建连接池
            pool = new JedisPool(config
            		, prop.getProperty("host")
            		, Integer.valueOf(prop.getProperty("port")));
            
            System.out.println("[jedisPool init sucessfully]");
        } catch(Exception e){
            System.out.println(e);
        }
	}
    /**
	 * 获取一个jedis 对象
	 * 
	 * @return
	 */
	public Jedis getJedis() {
	    return pool.getResource();
	}

	/**
	 * 归还一个连接
	 * 
	 * @param jedis
	 */
    public void returnRes(Jedis jedis) {
        pool.returnResource(jedis);
    }
	    
	public void set(String key, String value){
		Jedis jedis = getJedis();
		jedis.set(key, value);
		returnRes(jedis);
	}
	
	public String get(String key){
		Jedis jedis = getJedis();
		String value = jedis.get(key);
		returnRes(jedis);
		return value;
	}
	
	public void hset(String key, String field, String value){
		Jedis jedis = getJedis();
		jedis.hset(key, field, value);
		returnRes(jedis);
	}
	
	public String hget(String key, String field){
		Jedis jedis = getJedis();
		String value = jedis.hget(key, field);
		returnRes(jedis);
		return value;
	}
	
	public void hmset(String key, Map<String,String> map){
		Jedis jedis = getJedis();
		jedis.hmset(key, map);
		returnRes(jedis);
	}
	
	public List<String> hmget(String key, String...fields){
		Jedis jedis = getJedis();
		List<String> list = jedis.hmget(key, fields);
		returnRes(jedis);
		return list;
	}

	public Map<String,String> hgetAll(String key){
		Jedis jedis = getJedis();
		Map<String,String> map = jedis.hgetAll(key);
		returnRes(jedis);
		
		return map;
	}
	
	public boolean hexist(String key, String field){
		Jedis jedis = getJedis();
		boolean exist = jedis.hexists(key, field);
		returnRes(jedis);
		return exist;
	}
}
