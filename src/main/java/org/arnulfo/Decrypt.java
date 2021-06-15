package org.arnulfo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Decrypt 
{
    public static final int AES_KEY_LENGTH = 256;
    public static final int GCM_AAD_LENGTH = 16;
    public static final int GCM_TAG_LENGTH = 16;
    public static final int GCM_NONCE_LENGTH = 12;

    public static void main( String[] args ) throws Exception {
        String cryptographyKey = System.getenv().get("NEWS_CRYPTO_KEY");
        System.out.println(cryptographyKey);

        // Decode Base64 cypher key
        byte[] cypherKey = Base64.getDecoder().decode(cryptographyKey);

        // Load body
        Path path = Paths.get("/workspaces/sqs-client/body.txt");
        String body = Files.readAllLines(path).get(0);
        System.out.println("Body:");
        System.out.println(body);

        // Decode Base64 body
        byte[] binaryBody = Base64.getDecoder().decode(body);

        // First retrieve AAD bytes
        byte[] aad =  Arrays.copyOf(binaryBody, GCM_AAD_LENGTH);

        // Remove AAD from the binary body to have the cipher text
        byte[] cipherText = Arrays.copyOfRange(binaryBody, GCM_AAD_LENGTH, binaryBody.length);

        // Prepare NONCE (IV) by copying bytes 4 – 16 from AAD. 
        byte[] iv = Arrays.copyOfRange(aad, 4, 16);
        System.out.println("iv.length=" + iv.length);

        SecretKeySpec secretKeySpec = new SecretKeySpec(cypherKey, "AES");
        
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);
        cipher.updateAAD(aad);

        byte[] decryptedText = cipher.doFinal(cipherText);

        String plainText = new String(decryptedText);

        System.out.println("Plain Text:");
        System.out.println(plainText);

    }
}
