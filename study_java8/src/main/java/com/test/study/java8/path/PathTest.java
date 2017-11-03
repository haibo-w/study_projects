package com.test.study.java8.path;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * @author Haibo-W
 * @date 2017年10月19日 下午2:36:04	
 */
public class PathTest {

	@Test
	public void testPath1(){
		
		Path path = Paths.get("D:\\docs_shunwang");
		FileSystems.getDefault().getPath("D:\\docs_shunwang"); //eq with above
		System.out.println(path.getRoot());
		System.out.println(path.getNameCount());
		System.out.println(path.subpath(0, 1));
		
		Path path2 = Paths.get("D:\\docs_shunwang\\01_设计");
		try {
			System.out.println(path2.toRealPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testDirectory(){
		
		Path path = Paths.get("D:\\project_spaces\\mars_space\\paibo-datacenter\\conf");
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path,"*.properties")){
			for(Path p : stream){
				System.out.println(p);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static class FindProp extends SimpleFileVisitor<Path>{

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			
			if(file.toString().endsWith("properties")){
				System.out.println(file);
			}
			return FileVisitResult.CONTINUE;
		}
		
	}
	
	
	@Test
	public void testWalkTree(){
		
		try {
			Path path = Paths.get("D:\\project_spaces\\mars_space\\paibo-datacenter");
//			Stream<Path> walk = Files.walk(Paths.get("D:\\project_spaces\\mars_space\\paibo-datacenter"), 2);
//		walk.
//			Path tree = Files.walkFileTree(path, new FindProp());
//			tree.forEach(p -> {
//				System.out.println(" e " + p);
//			});
			
			
			//Files.move(source, target, options)
			Path path2 = Paths.get("D:\\project_spaces\\mars_space\\paibo-datacenter\\pom.xml");
			System.out.println(Files.readAttributes(path2, "*"));
			
			List<String> lines = Files.readAllLines(path2, StandardCharsets.UTF_8);
			for(String l : lines){
				System.out.println(l);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void testWatch(){
		
		try {
			//文件监控
			WatchService ws = FileSystems.getDefault().newWatchService();
			Path path = Paths.get("D:\\project_spaces\\mars_space\\paibo-datacenter");
			WatchKey key = path.register(ws, StandardWatchEventKinds.ENTRY_MODIFY);
			while(true){
				key = ws.take();
				for( WatchEvent<?> we : key.pollEvents()){
					if(we.kind()  == StandardWatchEventKinds.ENTRY_MODIFY){
						System.out.println("is modified");
					}
				}
				key.reset();
			}
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testAsynch(){
		
		
		Path path = Paths.get("D:\\project_spaces\\mars_space\\paibo-datacenter\\pom.xml");
		try {
			AsynchronousFileChannel open = AsynchronousFileChannel.open(path);
			ByteBuffer allocate = ByteBuffer.allocate(1024);
			
			Future<Integer> result = open.read(allocate, 0);
			
			while(!result.isDone()){
				System.out.println(" do something");
			}
			Integer integer = result.get();
			allocate.flip();
			byte [] at = new byte [allocate.limit()];
			allocate.get(at);
			System.out.println("read :" + integer);
			System.out.println("read :" + new String(at));
			allocate.clear();			
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void testStr(){
		
		String str1 = new StringBuilder("计算机").append("技术").toString();
		System.out.println(str1.intern() == str1);
		
		
		String str2 = new StringBuilder("ja").append("va").toString();
		System.out.println(str2.intern() == str2);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
