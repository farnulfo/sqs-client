package org.arnulfo;

import java.util.Base64;

public class Decrypt 
{
    public static void main( String[] args ) {
        String cryptographyKey = System.getenv().get("CRYPTO_KEY");
        System.out.println(cryptographyKey);

        // Decode Base64 cypher key
        byte[] cypherKey = Base64.getDecoder().decode(cryptographyKey);

        // Load body
        Path path = Paths.get("src/test/resources/fileTest.txt");

        String read = Files.readAllLines(path).get(0);
    }
}