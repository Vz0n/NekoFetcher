package io.github.Vz0n.neko.util;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class StringUtils {

    /**
     * Computes the SHA256 hash of a string
     *
     * @param string The string to hash
     * @return pretty obvious I think.
     */
    public static String computeHash(String string){
        HashFunction function = Hashing.sha256();
        HashCode hash = function.hashBytes(string.getBytes());

        return hash.toString();
    }
}
