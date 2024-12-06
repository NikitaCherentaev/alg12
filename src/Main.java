import java.awt.*;
import java.io.*;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        String imagePath = "C:/jev/image.jpg";
        String audioPath = "C:/jev/file.mp3";
        String videoPath = "C:/jev/video.mp4";

        Thread image = new Thread(() -> downloadFile(
                "https://avatars.mds.yandex.net/i?id=207777aadf6a147bdd6ec7e3cbca01ee_l-5233360-images-thumbs&n=13", imagePath));

        Thread audio = new Thread(() -> downloadFile(
                "https://rus.hitmotop.com/get/music/20110213/Rammstein_-_Rammstein_420734.mp3", audioPath));

        Thread video = new Thread(() -> downloadFile(
                "https://videocdn.cdnpk.net/joy/content/video/free/2019-09/large_preview/190828_27_SuperTrees_HD_17.mp4?token=exp=1729354189~hmac=b9a4712bd4e6a58d97cf857c57025be328bed49d8ef6c0da5a8fc700e9df1a51", videoPath));

        image.start();
        audio.start();
        video.start();

        try {
            image.join();
            audio.join();
            video.join();
        } catch (InterruptedException e) {
            System.err.println("Поток прерван: " + e.getMessage());
        }

        System.out.println("Загрузка файлов завершена.");

        openFile(imagePath);
        openFile(audioPath);
        openFile(videoPath);
    }

    private static void downloadFile(String fileURL, String output) {
        try (InputStream in = new URL(fileURL).openStream();
             OutputStream out = new FileOutputStream(output)) {
            byte[] buffer = new byte[4096];
            int len;
            while ((len = in.read(buffer)) > 0) out.write(buffer, 0, len);
            System.out.println("Файл " + output + " успешно загружен.");
        } catch (IOException e) {
            System.err.println("Ошибка при скачивании " + output + ": " + e.getMessage());
        }
    }

    private static void openFile(String filePath) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(filePath));
            } else {
                System.err.println("Desktop не поддерживается.");
            }
        } catch (IOException e) {
            System.err.println("Ошибка при попытке открыть файл " + filePath + ": " + e.getMessage());
        }
    }
}