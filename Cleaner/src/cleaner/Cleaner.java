package cleaner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Cleaner {

	public static void main(String[] args) throws IOException {

		String toBeKept = args[0];
		String toBeRemoved = args[1];

		try (Stream<Path> pathsToBeRemoved = Files.walk(Paths.get(toBeRemoved)).filter(pathToBeRemoved -> {

			Path pathToBeKept = Paths.get(toBeKept, ((Path) pathToBeRemoved).toString().replaceFirst(toBeRemoved, ""));

			try {
				return Files.isRegularFile(pathToBeRemoved) && Files.mismatch(pathToBeKept, pathToBeRemoved) == -1;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		})) {

			pathsToBeRemoved.forEach((Path pathToBeRemoved) -> {

				try {
					System.out.println(pathToBeRemoved);
					Files.deleteIfExists(pathToBeRemoved);
				} catch (IOException e) {
					e.printStackTrace();
				}

//				try (Stream<Path> pathsToBeKept = Files.walk(Paths.get(toBeKept)).parallel().filter(path -> {
//					try {
//						return Files.isRegularFile(path) && Files.mismatch(path, pathToBeRemoved) == -1;
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					return false;
//				})) {
//					if (pathsToBeKept.count() > 0) {
//
//						System.out.println(pathToBeRemoved);
//
//						try {
//							Files.deleteIfExists(pathToBeRemoved);
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			});
		}

	}
}
