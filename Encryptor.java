// Code from https://stackoverflow.com/questions/15554296/simple-java-aes-encrypt-decrypt-example
// DO NOT USE IN ANY PRODUCTION CODE -- THIS CODE IS VULNERABLE TO KNOW ATTACKS

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.io.*;


public class Encryptor {
    public static byte[] encrypt(byte[] key, byte[] initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            return cipher.doFinal(value.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(byte[] key, byte[] initVector, byte[] ciphertext) throws BadPaddingException {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(ciphertext);

            return new String(original);
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws Exception {

        byte[] s = {
                (byte) 0x63, (byte) 0x7C, (byte) 0x77, (byte) 0x7B, (byte) 0xF2, (byte) 0x6B, (byte) 0x6F, (byte) 0xC5,
                (byte) 0x30, (byte) 0x01, (byte) 0x67, (byte) 0x2B, (byte) 0xFE, (byte) 0xD7, (byte) 0xAB, (byte) 0x76,
                (byte) 0xCA, (byte) 0x82, (byte) 0xC9, (byte) 0x7D, (byte) 0xFA, (byte) 0x59, (byte) 0x47, (byte) 0xF0,
                (byte) 0xAD, (byte) 0xD4, (byte) 0xA2, (byte) 0xAF, (byte) 0x9C, (byte) 0xA4, (byte) 0x72, (byte) 0xC0,
                (byte) 0xB7, (byte) 0xFD, (byte) 0x93, (byte) 0x26, (byte) 0x36, (byte) 0x3F, (byte) 0xF7, (byte) 0xCC,
                (byte) 0x34, (byte) 0xA5, (byte) 0xE5, (byte) 0xF1, (byte) 0x71, (byte) 0xD8, (byte) 0x31, (byte) 0x15,
                (byte) 0x04, (byte) 0xC7, (byte) 0x23, (byte) 0xC3, (byte) 0x18, (byte) 0x96, (byte) 0x05, (byte) 0x9A,
                (byte) 0x07, (byte) 0x12, (byte) 0x80, (byte) 0xE2, (byte) 0xEB, (byte) 0x27, (byte) 0xB2, (byte) 0x75,
                (byte) 0x09, (byte) 0x83, (byte) 0x2C, (byte) 0x1A, (byte) 0x1B, (byte) 0x6E, (byte) 0x5A, (byte) 0xA0,
                (byte) 0x52, (byte) 0x3B, (byte) 0xD6, (byte) 0xB3, (byte) 0x29, (byte) 0xE3, (byte) 0x2F, (byte) 0x84,
                (byte) 0x53, (byte) 0xD1, (byte) 0x00, (byte) 0xED, (byte) 0x20, (byte) 0xFC, (byte) 0xB1, (byte) 0x5B,
                (byte) 0x6A, (byte) 0xCB, (byte) 0xBE, (byte) 0x39, (byte) 0x4A, (byte) 0x4C, (byte) 0x58, (byte) 0xCF,
                (byte) 0xD0, (byte) 0xEF, (byte) 0xAA, (byte) 0xFB, (byte) 0x43, (byte) 0x4D, (byte) 0x33, (byte) 0x85,
                (byte) 0x45, (byte) 0xF9, (byte) 0x02, (byte) 0x7F, (byte) 0x50, (byte) 0x3C, (byte) 0x9F, (byte) 0xA8,
                (byte) 0x51, (byte) 0xA3, (byte) 0x40, (byte) 0x8F, (byte) 0x92, (byte) 0x9D, (byte) 0x38, (byte) 0xF5,
                (byte) 0xBC, (byte) 0xB6, (byte) 0xDA, (byte) 0x21, (byte) 0x10, (byte) 0xFF, (byte) 0xF3, (byte) 0xD2,
                (byte) 0xCD, (byte) 0x0C, (byte) 0x13, (byte) 0xEC, (byte) 0x5F, (byte) 0x97, (byte) 0x44, (byte) 0x17,
                (byte) 0xC4, (byte) 0xA7, (byte) 0x7E, (byte) 0x3D, (byte) 0x64, (byte) 0x5D, (byte) 0x19, (byte) 0x73,
                (byte) 0x60, (byte) 0x81, (byte) 0x4F, (byte) 0xDC, (byte) 0x22, (byte) 0x2A, (byte) 0x90, (byte) 0x88,
                (byte) 0x46, (byte) 0xEE, (byte) 0xB8, (byte) 0x14, (byte) 0xDE, (byte) 0x5E, (byte) 0x0B, (byte) 0xDB,
                (byte) 0xE0, (byte) 0x32, (byte) 0x3A, (byte) 0x0A, (byte) 0x49, (byte) 0x06, (byte) 0x24, (byte) 0x5C,
                (byte) 0xC2, (byte) 0xD3, (byte) 0xAC, (byte) 0x62, (byte) 0x91, (byte) 0x95, (byte) 0xE4, (byte) 0x79,
                (byte) 0xE7, (byte) 0xC8, (byte) 0x37, (byte) 0x6D, (byte) 0x8D, (byte) 0xD5, (byte) 0x4E, (byte) 0xA9,
                (byte) 0x6C, (byte) 0x56, (byte) 0xF4, (byte) 0xEA, (byte) 0x65, (byte) 0x7A, (byte) 0xAE, (byte) 0x08,
                (byte) 0xBA, (byte) 0x78, (byte) 0x25, (byte) 0x2E, (byte) 0x1C, (byte) 0xA6, (byte) 0xB4, (byte) 0xC6,
                (byte) 0xE8, (byte) 0xDD, (byte) 0x74, (byte) 0x1F, (byte) 0x4B, (byte) 0xBD, (byte) 0x8B, (byte) 0x8A,
                (byte) 0x70, (byte) 0x3E, (byte) 0xB5, (byte) 0x66, (byte) 0x48, (byte) 0x03, (byte) 0xF6, (byte) 0x0E,
                (byte) 0x61, (byte) 0x35, (byte) 0x57, (byte) 0xB9, (byte) 0x86, (byte) 0xC1, (byte) 0x1D, (byte) 0x9E,
                (byte) 0xE1, (byte) 0xF8, (byte) 0x98, (byte) 0x11, (byte) 0x69, (byte) 0xD9, (byte) 0x8E, (byte) 0x94,
                (byte) 0x9B, (byte) 0x1E, (byte) 0x87, (byte) 0xE9, (byte) 0xCE, (byte) 0x55, (byte) 0x28, (byte) 0xDF,
                (byte) 0x8C, (byte) 0xA1, (byte) 0x89, (byte) 0x0D, (byte) 0xBF, (byte) 0xE6, (byte) 0x42, (byte) 0x68,
                (byte) 0x41, (byte) 0x99, (byte) 0x2D, (byte) 0x0F, (byte) 0xB0, (byte) 0x54, (byte) 0xBB, (byte) 0x16};

        byte[] key = new byte[16];
        key[0] = (byte) 0x00;
        key[1] = (byte) 0x00;
        key[2] = (byte) 0x00;
        key[3] = (byte) 0x00;
        key[4] = (byte) 0x00;
        key[5] = (byte) 0x00;
        key[6] = (byte) 0x00;
        key[7] = (byte) 0x00;
        key[8] = (byte) 0x00;
        key[9] = (byte) 0x00;
        key[10] = (byte) 0x00;
        key[11] = (byte) 0x00;
        key[12] = (byte) 0x00;
        key[13] = (byte) 0x00;
        key[14] = (byte) 0x00;
        key[15] = (byte) 0x00;
        //byte[] key = {'t', 'e', 's', 't', 't', 'e', 's', 't', 't', 'e', 's', 't', 't', 'e', 's', 't'}; // 128 bit key

        byte[] IV;
        IV = Files.readAllBytes(Paths.get("C:\\Users\\jayso\\IdeaProjects\\Cryptography\\src\\keysize_16_bits_group_10.iv"));

        byte[] cipher;
        cipher = Files.readAllBytes(Paths.get("C:\\Users\\jayso\\IdeaProjects\\Cryptography\\src\\keysize_16_bits_group_10.enc"));

        long startTime = System.nanoTime();
        for (byte i : s) {
            for (byte j : s) {

                                key[0] = i;
                                key[1] = j;

                                if ((Arrays.equals(encrypt(key, IV, "Hello world"), cipher))) {

                                    System.out.printf("0x%02X\n", i);
                                    System.out.printf("0x%02X\n", j);


                                    try (FileOutputStream fos = new FileOutputStream(("C:\\Users\\jayso\\IdeaProjects\\Cryptography\\src\\keysize_16_bits_group_10.key"))) {
                                        fos.write(key);

                                        long endTime = System.nanoTime();
                                        System.out.println("Took " + (endTime - startTime) + " ns");
                                    }
                                }
                            }
                        }
                    }
                }



























