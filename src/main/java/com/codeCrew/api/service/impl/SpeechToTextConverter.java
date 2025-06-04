package com.codeCrew.api.service.impl;

import com.azure.storage.blob.*;

import java.io.*;

import com.microsoft.cognitiveservices.speech.OutputFormat;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

public class SpeechToTextConverter {
    // Azure Speech API credentials
//
    private static final String SPEECH_API_KEY =  "8v5BUEZuvDSNKkENjFyJMDKcE6hv9MJf3ugs5XdCfgWv6gkJEyN6JQQJ99BFACqBBLyXJ3w3AAAYACOGU0Ee";
    private static final String SPEECH_API_REGION = "southeastasia";
    private static final String BLOB_CONNECTION_STRING = "DefaultEndpointsProtocol=https;AccountName=bankinstablob;AccountKey=x9dYNIgvWT/nuoRLH6l3qexmDACk1L1FsGyh0fTjS8HAo6aPitvRJLaZQ++dr7NFBB8xnv90QAjJ+AStwwn4CQ==;EndpointSuffix=core.windows.net";
    private static final String CONTAINER_NAME = "call-recordings";
    private static final String BLOB_NAME = "sampledata_1.wav"; // Or the format of your audio file
    private static final String blobPath = "https://bankinstablob.blob.core.windows.net/call-recordings/Speech%20Off.wav";

    public static void main(String[] args) {
//        createWaveFile();
        azureBlobDownload();
        speechToText();
    }

    private static void createWaveFile() {
        String text = "Hello, this is one line of content!";
        byte[] audioData = text.getBytes();

        File file = new File(BLOB_NAME);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(audioData);
            System.out.println("WAV file created: " + file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void speechToText() {
        try {
            SpeechConfig config = SpeechConfig.fromSubscription(SPEECH_API_KEY, SPEECH_API_REGION);
            config.setSpeechRecognitionLanguage("en-IN");
            config.setOutputFormat(OutputFormat.Detailed);


            AudioConfig audioConfig = AudioConfig.fromWavFileInput(BLOB_NAME);
            SpeechRecognizer recognizer = new SpeechRecognizer(config, audioConfig);

            System.out.println("speech to text conversion start.");


            recognizer.recognizing.addEventListener((s, e) -> System.out.println("Recognizing: " + e.getResult().getText()));
            recognizer.recognized.addEventListener((s, e) -> System.out.println("Final Result: " + e.getResult().getText()));

            recognizer.sessionStarted.addEventListener((s, e) ->
                    System.out.println("Session started."));
            recognizer.sessionStopped.addEventListener((s, e) ->
                    System.out.println("Session stopped."));
            recognizer.canceled.addEventListener((s, e) ->
                    System.out.println("Error: " + e.getErrorDetails()));

            System.out.println("speech to text conversion end.");
            recognizer.startContinuousRecognitionAsync().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void azureBlobDownload() {

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(BLOB_CONNECTION_STRING)
                .buildClient();

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(CONTAINER_NAME);
        BlobClient blobClient = containerClient.getBlobClient(BLOB_NAME);

        blobClient.downloadToFile(BLOB_NAME);
        System.out.println("Download complete: " + BLOB_NAME);
    }

    private static void createFileIfNotExist(String downloadPath) {
        File file = new File(downloadPath);

        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

}

