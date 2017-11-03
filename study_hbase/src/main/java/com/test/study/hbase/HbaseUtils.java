package com.test.study.hbase;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author Haibo-W
 * @date 2017年4月18日 下午2:47:15
 */
public class HbaseUtils {

	private HbaseUtils() {
	}

	static Configuration conf = HBaseConfiguration.create();

	public static void dropTable(String tableName) throws Exception {
		HBaseAdmin admin = new HBaseAdmin(conf);
		if (admin.tableExists(tableName)) {
			System.out.println(tableName + "表存在");
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
			System.out.println(tableName + "表已删");
		}
		admin.close();
		System.out.println(admin + "关闭");
	}

	public static boolean existsTable(String tableName) throws Exception {
		HBaseAdmin admin = new HBaseAdmin(conf);
		boolean exists = admin.tableExists(tableName);
		admin.close();
		return exists;
	}

	public static List<String> getAllTable() throws Exception {
		HBaseAdmin admin = new HBaseAdmin(conf);
		List<String> list = new ArrayList<String>();
		TableName[] table = admin.listTableNames();
		for (TableName tableName : table) {
			if (tableName.getName() != null) {
				list.add(Bytes.toString(tableName.getName()));
			}
		}
		admin.close();
		System.out.println(admin + "关闭");
		return list;
	}

	public static void main(String[] args) throws Exception {
		if (args == null || args.length == 0) {
			throw new IllegalArgumentException("没有输入任务参数");
		}
		String key = args[0];
		switch (key) {
			case "drop":
				if (args.length <= 1) {
					throw new IllegalArgumentException("没有输入表名");
				}
				HbaseUtils.dropTable(args[1]);
				break;
			// case "dropes":
			// EsClientUtils.deleteIndex(args[1]);
			// break;
			default:
				break;
		}
	}
}
