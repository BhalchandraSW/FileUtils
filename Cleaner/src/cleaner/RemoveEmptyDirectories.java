package cleaner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class RemoveEmptyDirectories {

	public static void main(String[] args) throws IOException {

		String pathToBeCleaned = args[0];

		try (Stream<Path> paths = Files.walk(Paths.get(pathToBeCleaned)).sorted(Comparator.reverseOrder()).parallel()
				.filter(path -> {
					return Files.isDirectory(path) && path.toFile().list().length == 0;
				})) {
			paths.forEach(path -> {
				System.out.println(path);
				try {
					Files.deleteIfExists(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}

//		try (Stream<Path> paths = Files.walk(Paths.get(pathToBeCleaned)).filter(path -> {
//			return Files.isDirectory(path) && path.toFile().list().length == 0;
//		})) {
//			paths.forEach(path -> {
//				System.out.println(path);
//				try {
//					Files.deleteIfExists(path);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			});
//		}
	}
}