package util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class RedisUtil {
	
	private static JedisPool jedisPool = null;

	//初始化redispool
	static {
		
		try {
			
			JedisPoolConfig config = new JedisPoolConfig();
			//像redis中添加列表并将xml文件存放到列表中
			String redisIP = "172.24.10.161";
			int redisPort = 8715;
			int intervalTime = 6000;
			jedisPool = new JedisPool(redisIP, redisPort);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}

	/**
	 * 获取Jedis实例
	 * @return  
	 */
	public synchronized static Jedis getJedis() {
		
		try {
			
			if(jedisPool != null) {
				
				Jedis resource = jedisPool.getResource();
				
				return resource;
				
			} else 
				
				return null;
				
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return null;
			
		}
	
    }
	/**
	 * 释放jedis资源
	 * @return
	 */
	public static void freeSource(final Jedis jedis) {
		
 		if(jedis != null)
 			jedisPool.returnResource(jedis);
		
	}
	
	/**
	 * @param 流程订单xml
	 * @desc 提交订单至redis数据库 ./redis-cli -h 127.0.0.1 -p 8715
	 */
	public static void submit(String xmlStr) {
		String redisIP = "172.24.10.161";
		int redisPort = 8715;
		int intervalTime = 6000;
		Jedis redis = new Jedis(redisIP, redisPort, intervalTime);
		redis.rpush("dpps:queue:order",xmlStr);
		System.out.println("执行订单提交成功");
		redis.close();
		
	}
	/**
	 * @desc 查询流程是否完成
	 * @ps 若有需求则可通更改此方法用于增加通过率
	 */
//	public static boolean scan(List<String> procIds) {
//
//		boolean flag = false;
//
//		Jedis redis = new Jedis(PropertyDict.PADDR, PropertyDict.PPORT, PropertyDict.PTIMEOUT);
//
//		Set<String> completeSet = redis.zrange(PropertyDict.KEY_PROCOMPLETE, 0, -1);
//
//		Set<String> abortSet = redis.zrange(PropertyDict.KEY_PROABORT, 0, -1);
//
////		int go = 0;
//
//		if(completeSet != null) {
//
//			for(String procId : procIds) {
//
//				if(completeSet.contains(procId) || abortSet.contains(procId)) {
//					flag = true;
//				} else {
////					redo++;
//					flag = false;
//					break;
//
//				}
//
//			}
//
//		}
//
////		double centum = go / procIds.size();
//
////		if(centum >= PropertyDict.GO_PERCENT)
////			flag = true;
//
//		redis.close();
//
//		return flag;
//
//	}
	
	/**
	 * @desc 用于配准前剔除没有生成L1A产品的帧数或景
	 */
//	public static void EliminateDat(Label label) {
//
//		List<String> procIds = label.getProIdList();
//
//		List<File> dats = label.getDats();
//
//		List<File> files = new ArrayList<File>();
//
//		Jedis redis = new Jedis(PropertyDict.PADDR, PropertyDict.PPORT, PropertyDict.PTIMEOUT);
//
//		Set<String> abortSet = redis.zrange(PropertyDict.KEY_PROABORT, 0, -1);
//
////		int go = 0;
//
//		if(abortSet != null) {
//
//			for(int i = 0; i < procIds.size(); i++) {
//
//				if(abortSet.contains(procIds.get(i)))
//					files.add(dats.get(i));
//
//			}
//
//			for(File f : files)
//				dats.remove(f);
//
//			label.setDats(dats);
//
//		}
//
//		redis.close();
//
//	}
}
