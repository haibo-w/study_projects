package com.test.study.hbase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.FilterList.Operator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


/**
 * @author Haibo-W
 * @date 2016年7月11日 下午5:19:03
 */
public class HbaseRowCounterMRUtil extends Configured implements Tool {
	
	private long count;

	static class RowCounterMapper extends TableMapper<ImmutableBytesWritable, Put> {

		public static enum InputCounters {
			ROWS
		}

		@Override
		protected void map(ImmutableBytesWritable key, Result value,
				Mapper<ImmutableBytesWritable, Result, ImmutableBytesWritable, Put>.Context context)
				throws IOException, InterruptedException {
			context.getCounter(InputCounters.ROWS).increment(1);
		}
	}

	/**
	 * @see org.apache.hadoop.util.Tool#run(java.lang.String[])
	 */
	@Override
	public int run(String[] args) throws Exception {

		if (args == null || args.length == 0) {
			return 0;
		}
		String tableName = args[0];
		String date = args.length > 1 ? args[1] : "<NA>";
		String familyName = args.length > 2 ? args[2] : null;
		String qualifier = args.length > 3 ? args[3] : null;
		System.out.print(" instance by args:");
		for (String ag : args) {
			System.out.print("\t" + ag);
		}
		System.out.println();

		Job job = Job.getInstance(getConf(), "RowCounter_" + tableName + "_" + date);
		job.setJarByClass(HbaseRowCounterMRUtil.class);
		Scan scan = new Scan();
		scan.setCaching(5000);
		scan.setCacheBlocks(false); // don't set to true for MR jobs

		if (date != null && familyName != null && qualifier != null) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date now = sdf.parse(date);
			Date tom = new Date(now.getTime() + 24 * 60 * 60 * 1000);

			byte[] family = Bytes.toBytes(familyName);
			Filter sgFilterS = new SingleColumnValueFilter(family, Bytes
					.toBytes(qualifier), CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(now.getTime()));
			Filter sgFilterE = new SingleColumnValueFilter(family, Bytes.toBytes(qualifier), CompareOp.LESS, Bytes
					.toBytes(tom.getTime()));

			FilterList list = new FilterList(Operator.MUST_PASS_ALL, sgFilterS, sgFilterE);
			scan.setFilter(list);
		}

		job.setOutputFormatClass(NullOutputFormat.class);
		TableMapReduceUtil.addDependencyJars(job.getConfiguration(), RowCounterMapper.class);
		TableMapReduceUtil.initTableMapperJob(tableName, // input table
				scan, // Scan instance to control CF and attribute selection
				RowCounterMapper.class, ImmutableBytesWritable.class, // mapper output key
				Put.class, // mapper output value
				job);

		//job.setNumReduceTasks(1); // at least one, adjust as required

		int res = job.waitForCompletion(true) ? 1 : 0;
		//this.count = job.getCounters().findCounter(InputCounters.ROWS).getValue();
		
		return res;
	}

	public long getCount() {
		return count;
	}



	/**
	 * @param args
	 * @author Haibo-W 2016年6月13日 下午4:34:51
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (args == null || args.length == 0) {
			System.out.println("请输入Counter查询参数：tableName [familyName qualifier] ");
			System.exit(0);
		}
		long start = System.currentTimeMillis();
		Configuration config = HBaseConfiguration.create();
		HbaseRowCounterMRUtil util = new HbaseRowCounterMRUtil();
		ToolRunner.run(config, util, args);
		System.out.println("counter rows:" + util.getCount() + " cost:" + (System.currentTimeMillis()-start));
		System.exit(0);
	}

}
