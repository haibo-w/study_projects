package com.test.study.hbase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList.Operator;
import org.apache.hadoop.hbase.util.Bytes;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;


/**
 * @author Haibo-W
 * @date 2016年7月26日 下午2:02:25	
 */
public class DMLTest {
	
	public static void main(String[] args) {
		DateTime dt = new DateTime(1471873668L*1000);
		System.out.println(dt.toString("yyyy-MM-dd HH:mm:ss"));
		System.out.println(dt.now().getMillis());
	}
	
	@Test
	public void copyData() throws Exception{
		
		Configuration conf = HBaseConfiguration.create();
		String path = System.getProperty("path.home");
		conf.addResource(new File(path+"/conf/hbase-site.xml").toURI().toURL());
		
		HTable source = new HTable(conf,"wifi_trace_201608");
		HTable dest = new HTable(conf,"wifi_trace_201610");
//		HTable source = new HTable(conf,"trace_offline_201608");
//		HTable dest = new HTable(conf,"trace_offline_201607");
//		HTable source = new HTable(conf,"mac_capture_20160801");
//		HTable dest = new HTable(conf,"mac_capture_20160901");
		
		Scan scan  = new Scan();
		
//		//过滤条件
//		DateTime now = DateTime.parse("2016-07-12", DateTimeFormat.forPattern("yyyy-MM-dd"));			
//		DateTime tom = now.plusDays(1);
//		byte[] family = Bytes.toBytes("tra");
//		Filter sgFilterS = new SingleColumnValueFilter(family, Bytes.toBytes("crt_tm"), CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(now.getMillis()));
//		Filter sgFilterE = new SingleColumnValueFilter(family, Bytes.toBytes("crt_tm"), CompareOp.LESS, Bytes.toBytes(tom.getMillis()));
//		
//		FilterList list = new FilterList(Operator.MUST_PASS_ALL,sgFilterS,sgFilterE);
//		scan.setFilter(list);
		
//		scan.setStartRow(Bytes.toBytes("0003ce22806874d11471873668")); //wifi
//		scan.setStopRow(Bytes.toBytes("0003ce22806874d11471873668"));
//		scan.setStartRow(Bytes.toBytes("21da4b950a61d1281471956975"));//21da4b950a61d1281471956975 bar
//		scan.setStopRow(Bytes.toBytes("21da4b950a61d1281471956975"));//21da4b950a61d1281471956975
//		scan.setStartRow(Bytes.toBytes("f429816ac8281472713233"));
//		scan.setStopRow(Bytes.toBytes("f429816ac8281472713233")); //mac
//		scan.setStartRow(Bytes.toBytes("9fdb8a64b027c8231472715150"));
//		scan.setStopRow(Bytes.toBytes("9fdb8a64b027c8231472715150")); //wifistore
//		scan.setStartRow(Bytes.toBytes("0024d2bf62581472713633"));
//		scan.setStopRow(Bytes.toBytes("0024d2bf62581472713633")); //macstore
		scan.setStartRow(Bytes.toBytes("f58c490a2aef4f4b1480666964"));
		scan.setStopRow(Bytes.toBytes("f58c490a2aef4f4b1480666964")); //macstore
		
		//b84267865bf4094f|581f28edaf38
		ResultScanner scanner = source.getScanner(scan);
		int i =0;
		for(Result rs : scanner){
			
//			if(i == 1){
//				break;
//			}
			
			Cell[] cells = rs.rawCells();
			
//			Put p = new Put(Bytes.toBytes("b84267865bf4094f|"+DefaultRowKeyGen.mobilePart("18601626190")));
//			Put p = new Put(Bytes.toBytes(IdMobileAssRowKeyGen.generateRowKey("341225199512188691", 2, "18601626190")));
//			Put p = new Put(Bytes.toBytes("0003ce22806874d11471873690"));
			Put p = new Put(Bytes.toBytes("f58c490a2aef4f4b1480666964"));
			for(Cell c : cells){
				
//				if(Bytes.toString(CellUtil.cloneQualifier(c)).equals("l_code")){
//					p.add(CellUtil.cloneFamily(c), Bytes.toBytes("l_code"), Bytes.toBytes("31010921028006"));
//				}else if(Bytes.toString(CellUtil.cloneQualifier(c)).equals("login_at")){
//					p.add(CellUtil.cloneFamily(c), Bytes.toBytes("login_at"), Bytes.toBytes(1472798997260L));
//				}else 
//				if(Bytes.toString(CellUtil.cloneQualifier(c)).equals("id_type")){
//					p.add(CellUtil.cloneFamily(c), Bytes.toBytes("id_type"), Bytes.toBytes(19));
//				}
//				}else if(Bytes.toString(CellUtil.cloneQualifier(c)).equals("id_code")){
//					p.add(CellUtil.cloneFamily(c), Bytes.toBytes("id_code"), Bytes.toBytes("162137381"));
//				}
//				else{
					p.add(CellUtil.cloneFamily(c), CellUtil.cloneQualifier(c), CellUtil.cloneValue(c));
//				}
			}			
			dest.put(p);
			i++;
		}
		
//		Put pr  = new Put(Bytes.toBytes("000016666f3916621470734488"));
//		pr.add(Bytes.toBytes("tra"), Bytes.toBytes("sid"), Bytes.toBytes("3101080130862669D3D9B20160711183039"));
//		pr.add(Bytes.toBytes("tra"), Bytes.toBytes("id_type"), Bytes.toBytes(2));
//		pr.add(Bytes.toBytes("tra"), Bytes.toBytes("id_code"), Bytes.toBytes("41152819871125585x"));
//		pr.add(Bytes.toBytes("tra"), Bytes.toBytes("l_code"), Bytes.toBytes("7124792729"));
//		pr.add(Bytes.toBytes("tra"), Bytes.toBytes("area"), Bytes.toBytes("310106"));
//		pr.add(Bytes.toBytes("tra"), Bytes.toBytes("logout_at"), Bytes.toBytes(new Date().getTime()));
//		dest.put(pr);
		dest.flushCommits();
		System.out.println("coppy  " + i);
	}
	
	@Test
	public void update() throws Exception{
//		String tableName = "wifi_trace_201609";
//		String rowkey = "00001b1a973b9beb1470734475";
//		String family = "tra";
		String tableName = "id_mobile_ass_201609";
		String rowkey = "09162610681|b84267865bf4094f";
		String family = "assr";
		
		Configuration conf = HBaseConfiguration.create();
		String path = System.getProperty("path.home");
		conf.addResource(new File(path+"/conf/hbase-site.xml").toURI().toURL());
		
		HTable table = new HTable(conf, tableName);
		Put put = new Put(Bytes.toBytes(rowkey));
//		put.add(Bytes.toBytes(family), Bytes.toBytes("auth_type"), Bytes.toBytes("1021111"));
//		put.add(Bytes.toBytes(family), Bytes.toBytes("auth_account"), Bytes.toBytes("41152819871125585x"));
		
		put.add(Bytes.toBytes(family),Bytes.toBytes("id_type"), Bytes.toBytes(2));
		put.add(Bytes.toBytes(family),Bytes.toBytes("id_code"), Bytes.toBytes("310112197101171526"));
		put.add(Bytes.toBytes(family),Bytes.toBytes("mobile"), Bytes.toBytes("18601626190"));
		put.add(Bytes.toBytes(family),Bytes.toBytes("tra_key"), Bytes.toBytes("b84267865bf4094f,c84267865bf4094f"));
		
		
		table.put(put);
		table.flushCommits();
		table.close();
	}
	
	@Test
	public void delete() throws Exception{
		
		Configuration conf = HBaseConfiguration.create();
		String path = System.getProperty("path.home");
		conf.addResource(new File(path+"/conf/hbase-site.xml").toURI().toURL());
		conf.addResource(new File(path+"/conf/hbase-site.xml").toURI().toURL());
		
//		path += "/datacenter-configuration/dev-support/test";
//		conf.addResource(new File(path + "/hbase-site.xml").toURI().toURL());
//		conf.addResource(new File(path + "/yarn-site.xml").toURI().toURL());
		
		String tableName [] = {
				"id_mac_ass_",
				"id_mobile_ass_",
				"id_vid_ass_",
				"mobile_mac_ass_",
				"mobile_vid_ass_",
				"mac_vid_ass_",
				"ap_hot_",
				"eq_trace_",
				"hotel_trace_",
				"trace_offline_",
				"wifi_trace_",
//				"id_mac_ass_201609",
//				"id_mobile_ass_201609",
//				"id_vid_ass_201609",
//				"mobile_mac_ass_201609",
//				"mobile_vid_ass_201607",
//				"mobile_vid_ass_201608",
//				"mobile_vid_ass_201609",
//				"mac_vid_ass_201609",
//				
//				"id_mac_ass_201608",
//				"id_mobile_ass_201607",
//				"id_vid_ass_201607",
//				"mobile_mac_ass_201607",
//				"mobile_vid_ass_201607",
//				"mac_vid_ass_201607",
//				"mac_vid_ass_201609",
//				"mobile_vid_ass_201609",
				
//				"wifi_trace_201607",
//				"wifi_trace_201608",
//				"wifi_trace_201609",
//				"trace_offline_201607",
//				"trace_offline_201608",
//				"trace_offline_201609",
//				"mac_capture_20160801"
				
				"mac_store",
				"mobile_store",
				"id_store",
				"vid_store",
				"imei_store",
				"imsi_store",
				"store_ass_",
//				"store_ass_201607",
//				"store_ass_201608",
//				"store_ass_201609",
//				"store_ass_201610",
//				"store_ass_201611",
//				"store_ass_201612",
//				"store_ass_201701",
				
				"store_ass_idx_",
//				"store_ass_idx_201607",
//				"store_ass_idx_201608",
//				"store_ass_idx_201609",
//				"store_ass_idx_201610",
//				"store_ass_idx_201611",
//				"store_ass_idx_201612",
//				"store_ass_idx_201701"
				
		};
		HBaseAdmin ad  = new HBaseAdmin(conf);
		String [] times = {"201703","201704","201705","201706","201707","201708",};
		for(String tab : tableName){
			for(String time : times){
				HTable dest = new HTable(conf,tab +time);
				if(ad.tableExists(dest.getName())){
					ad.disableTable(dest.getName());
	//				ad.truncateTable(dest.getName(), true); // 保留分区
					ad.deleteTable(dest.getTableName());
	//				ad.enableTable(dest.getName());
				}
			}
		}
		ad.close();
	}
	
	
	@Test
	public void deleteTab() throws Exception{
		
		Configuration conf = HBaseConfiguration.create();
		String path = System.getProperty("path.home");
		conf.addResource(new File(path+"/conf/hbase-site.xml").toURI().toURL());
		conf.addResource(new File(path+"/conf/hbase-site.xml").toURI().toURL());
		
		String tableName [] = {
				"TestTable","ap_hot_201704","ap_hot_201705","ap_hot_201708","ap_hot_201709","eq_trace_201704","eq_trace_201705","wifi_trace_201708","wifi_trace_201709"
		};
		HBaseAdmin ad  = new HBaseAdmin(conf);
		for(String tab : tableName){
			HTable dest = new HTable(conf,tab);
			if(ad.tableExists(dest.getName())){
				ad.disableTable(dest.getName());
				ad.deleteTable(dest.getTableName());
			}
		}
		ad.close();
	}
	
	@Test
	public void deleteDayTab() throws Exception{
		Configuration conf = HBaseConfiguration.create();
		String path = System.getProperty("path.home");
		conf.addResource(new File(path+"/conf/hbase-site.xml").toURI().toURL());
		conf.addResource(new File(path+"/conf/hbase-site.xml").toURI().toURL());
		String tableName [] = {"mac_capture_"};
		
		HBaseAdmin ad  = new HBaseAdmin(conf);
		DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime end = DateTime.parse("2017-04-21",pattern);
		DateTime start = DateTime.parse("2017-01-01",pattern);
		for(String tab : tableName){
			start = DateTime.parse("2017-01-01",pattern);
			while(true){
				start = start.plusDays(1);
				HTable dest = new HTable(conf,tab +start.toString("yyyyMMdd"));
				if(ad.tableExists(dest.getName())){
					ad.disableTable(dest.getName());
					ad.deleteTable(dest.getTableName());
				}
				if(start.isAfter(end)){
					break;
				}
			}
		}
		ad.close();
	}
	
	@Test
	public void deleteRecord() throws Exception{
		
		Configuration conf = HBaseConfiguration.create();
		String path = System.getProperty("path.home");
		conf.addResource(new File(path+"/conf/hbase-site.xml").toURI().toURL());
//		conf.addResource(new File(path+"/conf/yarn-site.xml").toURI().toURL());
		
		//Delete delete = new Delete(Bytes.toBytes("652201198811201647"));
		HTable dest = new HTable(conf,"wifi_trace_201608");
		//dest.delete(delete);
		Scan scan = new Scan();
		ResultScanner scanner = dest.getScanner(scan);
		
		int i =0;
		for(Result rs : scanner){
			
			Delete delete  = new Delete(rs.getRow());
			if(i<20000){
				dest.delete(delete);
			}
			i++;
		}
		
		dest.close();
		dest.flushCommits();
//		dest = new HTable(conf,"id_store");
//		delete = new Delete(Bytes.toBytes("41152819871125585x"));
//		dest.delete(delete);
//		dest.close();
		
//		dest = new HTable(conf,"wifi_trace_201609");
//		delete = new Delete(Bytes.toBytes("00001b1a973b9beb1470734475"));
//		dest.delete(delete);
//		dest.close();
	}

	/**
	 * TDH drop table
	 * @throws Exception
	 */
	@Test
	public void dropTable() throws Exception{
		
		Configuration conf = HBaseConfiguration.create();
		String path = System.getProperty("path.home");
		conf.addResource(new File(path+"/datacenter-configuration/dev-support/tdh/hbase-site.xml").toURI().toURL());
		HBaseAdmin admin = new HBaseAdmin(conf);
		TableName[] tables = admin.listTableNames();
		
		for (int i=0;i<tables.length;i++){
			admin.disableTable(tables[i]);
			admin.deleteTable(tables[i]);
		}
		admin.close();
	}
	
	
	@Test
	public void putsRecord() throws Exception{
		
		Configuration conf = HBaseConfiguration.create();
		String path = System.getProperty("path.home");
		conf.addResource(new File(path+"/conf/hbase-site.xml").toURI().toURL());
//		conf.addResource(new File(path+"/conf/yarn-site.xml").toURI().toURL());
		
		
		HTable dest = new HTable(conf,"mobile_vid_ass_201609");
//		
		Put put = new Put(Bytes.toBytes("99907957731|3836b5a4ec6ab3f4"));
		for(int i = 0; i< 2;i++){
			long ver = System.nanoTime();
			put.add(Bytes.toBytes("ass"), Bytes.toBytes("vid_type"),ver, Bytes.toBytes("1001"));
			put.add(Bytes.toBytes("ass"), Bytes.toBytes("vid_code"),ver, Bytes.toBytes("162137381"));
			put.add(Bytes.toBytes("ass"), Bytes.toBytes("times"),ver, Bytes.toBytes(1));
			put.add(Bytes.toBytes("ass"), Bytes.toBytes("lst_tm"),ver, Bytes.toBytes(System.currentTimeMillis()));
		}
//		Put put2 = new Put(Bytes.toBytes("99907957731|3836b5a4ec6ab3f4"));
//		put2.add(Bytes.toBytes("ass"), Bytes.toBytes("vid_type"), Bytes.toBytes("1001"));
//		put2.add(Bytes.toBytes("ass"), Bytes.toBytes("vid_code"), Bytes.toBytes("162137381"));
//		put2.add(Bytes.toBytes("ass"), Bytes.toBytes("times"), Bytes.toBytes(2));
//		put2.add(Bytes.toBytes("ass"), Bytes.toBytes("lst_tm"), Bytes.toBytes(1000000L));
//		
//		
//		List<Put> pus = new ArrayList<>();
//		
//		pus.add(put);
//		pus.add(put2);
		
//		dest.put(put);
//		dest.flushCommits();
//		dest.close();
////		
		Get get = new Get(Bytes.toBytes("99907957731|3836b5a4ec6ab3f4"));
		get.setMaxVersions(100);
		Result result = dest.get(get);
		List<Cell> cells = result.getColumnCells(Bytes.toBytes("ass"), Bytes.toBytes("lst_tm"));
		for(Cell c : cells){
			System.out.println(Bytes.toLong(CellUtil.cloneValue(c)));
		}
	}

}
