package com.example.myfirstapp.telegram;

import com.example.myfirstapp.libNeuronNetw.LearnDataSet;
import com.example.myfirstapp.libNeuronNetw.Network;
import com.example.myfirstapp.libNeuronNetw.NetworkUtil;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class Core {
    private static Network network;
    //private static double[][] picture = new double[12][3];

    // Метод создания нейросети
    public static void creationNetwork(double learningRate, int iterations, int innerLayer1,
                                int innerLayer2, int innerLayer3) {

        // Пожгружаем изображения для обучения нейросети
        BufferedImage bufferedImage = null;
        double[][] picture = new double[12][3];
        try {
            for (int i = 0; i < picture.length; i++) {
                // "/home/leonid/Изображения/Neuron network picture/pic"+(i)+".jpeg"));
                bufferedImage = ImageIO.read(new File(
                        "src/main/resources/images/pic"+(i)+".jpeg"));

                picture[i] = Core.imageToDouble(bufferedImage);
            }
        } catch (IOException e) {

        }

        // Создаем нейросеть
        network = new Network(3, innerLayer1, innerLayer2, innerLayer3, 3);

        // Скармливаем ей изображения
        LearnDataSet lds = new LearnDataSet();
        lds.addData(picture[0], new double[] {1, 0, 0})
                //.addData(picture[1], new double[] {1, 0, 0})
                .addData(picture[2], new double[] {1, 0, 0})
                .addData(picture[3], new double[] {1, 0, 0})
                .addData(picture[4], new double[] {0, 1, 0})
                .addData(picture[5], new double[] {0, 1, 0})
                .addData(picture[6], new double[] {0, 1, 0})
                .addData(picture[7], new double[] {0, 1, 0})
                .addData(picture[8], new double[] {0, 0, 1})
                .addData(picture[9], new double[] {0, 0, 1})
                .addData(picture[10], new double[] {0, 0, 1})
                .addData(picture[11], new double[] {0, 0, 1});

        lds.learning_rate = learningRate; // 0.05
        lds.iterations = iterations;  // 100000

        // Запуск обучения
        Thread t = network.train(lds);
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Метод отклика нейросети на подаваемое изображение
    public static String responseNetwork(File d) {

        double picture[] = new double[3];

        try {
            BufferedImage bufferedImage = ImageIO.read(d);
            picture = Core.imageToDouble(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String response = "";

        if (network.query(picture)[0] > network.query(picture)[1] &&
                network.query(picture)[0] > network.query(picture)[2]) {
            response = "Похоже, на картинке кот";
        }

        if (network.query(picture)[1] > network.query(picture)[0] &&
                network.query(picture)[1] > network.query(picture)[2]) {
            response = "Похоже, на картинке автомобиль";
        }

        if (network.query(picture)[2] > network.query(picture)[0] &&
                network.query(picture)[2] > network.query(picture)[1]) {
            response = "Похоже, на картинке самолет";
        }

        SendMessage message = new SendMessage();
        return response;
    }

    // Метод перевода картинки в массив
    public static double[] imageToDouble(BufferedImage bufferedImage) {
        return NetworkUtil.imageToData(bufferedImage, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    public static File uploadFiles(String fileName, String fileId) throws IOException {
        URL url = new URL("https://api.telegram.org/bot" + MyAmazingBot.BOT_TOKEN +
                "/getFile?file_id=" + fileId);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        String getFileResponse = bufferedReader.readLine();

        JSONObject result = new JSONObject(getFileResponse);
        JSONObject path = result.getJSONObject("result");
        String file_path = path.getString("file_path");

        File file = new File("src/main/resources/uploadedFile/" + fileName);
        InputStream inputStream = new URL("https://api.telegram.org/file/bot" + MyAmazingBot.BOT_TOKEN +
                "/" + file_path).openStream();

        FileUtils.copyInputStreamToFile(inputStream, file);
        bufferedReader.close();
        inputStream.close();
        System.out.println("Read: OK!");

        return file;
    }

}
