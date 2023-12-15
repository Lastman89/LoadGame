import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        String pathFromZip = "D:\\Учеба\\JAVA\\Progi\\Core\\Games\\Games\\savegames\\";

        File folder = new File(pathFromZip);
        File[] files = folder.listFiles();

        for (int i = 0; i < files.length; i++) {
            String pathToZip = pathFromZip + files[i].getName();
            openZip(pathToZip, pathFromZip);
            System.out.println(openProgress(pathFromZip));
        }
    }

    public static void openZip(String pathToZip, String pathFromZip) {

        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathToZip))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {

                name = entry.getName(); // получим название файла
                System.out.printf("File name: %s \n", name);

                // распаковка
                FileOutputStream fout = new FileOutputStream(pathFromZip + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
    }

    public static Object openProgress(String path) {
        File filesName = new File("D:\\Учеба\\JAVA\\Progi\\Core\\Games\\Games\\savegames\\");
        String saveGame;
        for (int i = 0; i < filesName.list().length; i++) {

            if (filesName.list()[i].contains("zip")) {
                continue;
            } else {
                saveGame = path + filesName.list()[i];
            }
            try (FileInputStream fis = new FileInputStream(saveGame);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                // десериализуем объект и скастим его в класс
                Object gameProgress = (GameProgress) ois.readObject();
                return gameProgress;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }
}