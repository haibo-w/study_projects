package com.test.study.kafka;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.SystemPropertyUtils;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.producer.Producer;
import kafka.message.MessageAndMetadata;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;


/**
 * @author Haibo-W
 * @date 2017年10月12日 下午4:21:26	
 */
public class TestKafka {

	Properties kafkaProps;
	@Before
	public void init() throws Exception{
		
		kafkaProps = new Properties();
		
		InputStream input = new FileInputStream(SystemPropertyUtils.resolvePlaceholders("${user.dir}/conf/kafka.properties"));
		kafkaProps.load(input);
		input.close();
		
	}
	
	@Test
	public void testAll(){
		testProducer();
		testConsumer();
	}
	
	@Test
	public void testProducer(){
		
		try {
			Properties np = new Properties();
			for(Object obj : kafkaProps.keySet()){
				String key = (String)obj;
				if(key.startsWith("producer.")){
					np.put(key.replace("producer.", ""), kafkaProps.get(key));
				}
			}
			np.put("request.required.acks","1");   
			ProducerConfig pc = new ProducerConfig(np);
			Producer<String, byte[]> producer = new Producer<>(pc);
			int i = 10;
			char key = 65;
			while(i > 0){
				KeyedMessage<String, byte[]> msg = new KeyedMessage<String, byte[]>("mytest", String.valueOf((char)(key+i))  ,"Test Message A !".getBytes());
				producer.send(msg);
				i--;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testConsumer(){
		Properties np = new Properties();
		for(Object obj : kafkaProps.keySet()){
			String key = (String)obj;
			if(key.startsWith("consumer.")){
				np.put(key.replace("consumer.", ""), kafkaProps.get(key));
			}
		}
		
		new Thread(new Calc("grouptest","testconsumer1",np)).start();;
		new Thread(new Calc("grouptest","testconsumer2",np)).start();;
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	class Calc implements Runnable{
		
		private String gid;
		private String cid;
		
		private Properties np;

		public Calc(String gid, String cid, Properties np) {
			super();
			this.gid = gid;
			this.cid = cid;
			this.np = np;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			np.setProperty("group.id", gid);
			np.setProperty("consumer.id", cid);
			ConsumerConfig cc = new ConsumerConfig(np);
			ConsumerConnector connector = Consumer.createJavaConsumerConnector(cc);
			
			Map<String,Integer> topicCountMap = new HashMap<>();
			topicCountMap.put("mytest", 1);
			Map<String, List<KafkaStream<byte[], byte[]>>> streams = connector.createMessageStreams(topicCountMap);
			
			for(Map.Entry<String, List<KafkaStream<byte[], byte[]>>> map : streams.entrySet()){
				System.out.println(map.getKey() + " " + map.getValue().size());
			}
			
			List<KafkaStream<byte[], byte[]>> list = streams.get("mytest");

			KafkaStream<byte[],byte[]> stream = list.get(0);
			
			ConsumerIterator<byte[],byte[]> iterator = stream.iterator();
			while(iterator.hasNext()){
				MessageAndMetadata<byte[], byte[]> msg = iterator.next();
				System.out.println(" consumer "+cid+":" + new String(msg.key()) + " " + new String(msg.message()) +" "+ msg.offset());
			}
		}
		
	}
	
	
	
	
}
