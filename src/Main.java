import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Path path = Path.of("..");
        FileVisitor<Path> statsVisitor = new StatsVisitor();
        try {
            Files.walkFileTree(path,statsVisitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static class StatsVisitor extends SimpleFileVisitor<Path> {
        int level = 0;
        int files = 0;
        int folder = 0;
        long size = 0;
        FileTime ft;
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            if(level == 1) {
                folder = 0;
                files = 0;
                ft = attrs.creationTime();
            }
            else  folder++;
            level++;
            Objects.requireNonNull(dir);
            Objects.requireNonNull(attrs);
            return FileVisitResult.CONTINUE;
        }

        private String convertTime(FileTime ft) {
            Instant instant = ft.toInstant();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withZone(ZoneId.systemDefault());

            String humanReadableTime = formatter.format(instant);
            return humanReadableTime;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

            files++;
            Objects.requireNonNull(file);
            Objects.requireNonNull(attrs);
            size +=attrs.size();
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return super.visitFileFailed(file, exc);
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            level--;
            if(level == 1) {
                String dt = convertTime(ft);
                System.out.println("[" + dir.getFileName() + "] -> Subfolders: " + folder
                                   + ", Files: " + files + ", Total Size: " + size + " bytes, Creation Time: [" + dt + "]");

            }
            Objects.requireNonNull(dir);
            if (exc != null)
                throw exc;
            return FileVisitResult.CONTINUE;
        }
    }
}