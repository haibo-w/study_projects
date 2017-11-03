package com.test.study.java8.path;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.junit.Test;

/**
 * @author Haibo-W
 * @date 2017年10月17日 上午10:19:36
 */
public class FileMoniterTest {
	
	FileAlterationListener listener = new FileAlterationListener(){

		@Override
		public void onStart(FileAlterationObserver observer) {
			System.out.println(" observer start " + observer);
		}

		@Override
		public void onDirectoryCreate(File directory) {
			System.out.println(" File onDirectoryCreate " + directory.getAbsolutePath());
		}

		@Override
		public void onDirectoryChange(File directory) {
			System.out.println(" File onDirectoryChange " + directory.getAbsolutePath());
		}

		@Override
		public void onDirectoryDelete(File directory) {
			System.out.println(" File onDirectoryDelete " + directory.getAbsolutePath());
		}

		@Override
		public void onFileCreate(File file) {
			System.out.println(" File onFileCreate " + file.getAbsolutePath());
		}

		@Override
		public void onFileChange(File file) {
			System.out.println(" File onFileChange " + file.getAbsolutePath());
		}

		@Override
		public void onFileDelete(File file) {
			System.out.println(" File onFileDelete " + file.getAbsolutePath());
		}

		@Override
		public void onStop(FileAlterationObserver observer) {
			System.out.println(" observer stop " + observer);			
		}
		
	};

	@Test
	public void testMoniter() {

		try {

			FileAlterationMonitor monitor = new FileAlterationMonitor(1000L);

			FileAlterationObserver observer = new FileAlterationObserver("D:\\opt\\track");
			
			observer.addListener(listener);
		 // intialize
//	      observer.init();
	      // invoke as required
//	      observer.checkAndNotify();
//	      observer.checkAndNotify();
	      // finished
//	      observer.finish();

			monitor.addObserver(observer);

			monitor.start();

			Thread.sleep(60000L);
			monitor.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFile() throws Exception{
		Files.walkFileTree(Paths.get("D:\\opt\\track\\FILE"), new FileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if(file.getFileName().startsWith("NETBAR_Track_201710150005")){
					System.out.println(file.getFileName());
				}
				System.out.println(file.getFileName());
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				// TODO Auto-generated method stub
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				// TODO Auto-generated method stub
				return FileVisitResult.CONTINUE;
			}
			
		});
		Thread.sleep(6000L);
	}
	
	@Test
	public void testException(){
		File file = new File("D:\\opt\\track");
		try(FileOutputStream fos = new FileOutputStream(file)){
			fos.flush();
		} catch (IOException e) {
			
		} 
	}
}
