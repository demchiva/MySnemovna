package cz.my.snemovna.service.loader;

import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * The utils for manage the ZIP archives and files.
 */
@Slf4j
@Service
public class ArchiveUtils {

    /**
     * The basic source file path.
     */
    @Value("${base.loader.path}")
    public String baseDestination;

    /**
     * The method load the zip archive, unzip it and save to file system path {@link this#baseDestination}.
     * @param url the internet resource to load from.
     * @param dirName the name of the directory for saving files.
     */
    @SneakyThrows
    public void unZipAndSave(@NotNull final String url, @NotNull final String dirName) {
        String zipFileName = baseDestination + dirName + ".zip";
        loadArchive(url, zipFileName);

        File zipSource =  new File(zipFileName);
        File directory = new File(baseDestination + dirName);

        LOGGER.info("Start unzip file: {}", zipFileName);
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipSource));
        ZipEntry zipEntry = zis.getNextEntry();

        byte[] buffer = new byte[1024];
        while (zipEntry != null) {
            File newFile = newFile(directory, zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }

            zipEntry = zis.getNextEntry();
        }

        stopWatch.stop();
        LOGGER.info("End unzip file: {}. Unzip took {} millis", zipFileName, stopWatch.getTotalTimeMillis());

        zis.closeEntry();
        zis.close();
        deleteSourceFile(dirName);
    }

    /**
     * The method checks if the directory with given name exist.
     * @param dirName the directory name.
     * @return 'true' if directory exist, otherwise 'false'.
     */
    public boolean isDirectoryExist(@NotNull final String dirName) {
        final File dir = new File(baseDestination + dirName);
        return dir.exists() && dir.isDirectory();
    }

    @SneakyThrows
    private void deleteSourceFile(@NotNull final String fileName) {
        FileUtils.forceDelete(new File(baseDestination + fileName + ".zip"));
    }

    @SneakyThrows
    private static void loadArchive(@NotNull final String url, @NotNull final String zipFileName) {
        LOGGER.info("Start loading url: {}", url);
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(url).openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(zipFileName);
        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        fileOutputStream.close();

        stopWatch.stop();
        LOGGER.info("End loading url: {}. Loading took {} millis", url, stopWatch.getTotalTimeMillis());
    }

    private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }


    /**
     * The method deletes directory with given name.
     * @param dirName the directory name.
     */
    @SneakyThrows
    public void deleteDirectory(@NotNull final String dirName) {
        FileUtils.deleteDirectory(new File(baseDestination + dirName));
    }
}
