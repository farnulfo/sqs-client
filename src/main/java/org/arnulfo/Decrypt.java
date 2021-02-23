package org.arnulfo;

import java.util.Base64;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Decrypt 
{
    public static void main( String[] args ) throws Exception {
        String cryptographyKey = System.getenv().get("CRYPTO_KEY");
        System.out.println(cryptographyKey);

        // Decode Base64 cypher key
        byte[] cypherKey = Base64.getDecoder().decode(cryptographyKey);

        // Load body
        Path path = Paths.get("/home/codespace/workspace/sqs-client/body.txt");
        String body = Files.readAllLines(path).get(0);
        System.out.println("Body:");
        System.out.println(body);
    }
}