package com.tsypanov.ovn;

import java.util.concurrent.ThreadLocalRandom;

final class RandomStringGenerator {

  String randomString(String alphabet, int length) {
    char[] chars = alphabet.toCharArray();

    ThreadLocalRandom random = ThreadLocalRandom.current();

    char[] array = new char[length];
    for (int i = 0; i < length; i++) {
      array[i] = chars[random.nextInt(chars.length)];
    }

    return new String(array);
  }
}
