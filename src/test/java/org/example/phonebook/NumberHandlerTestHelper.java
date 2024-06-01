package org.example.phonebook;

import java.lang.reflect.Field;

// needed to set(change) the file path for the NumberHandler instance (since it is a singleton)
public class NumberHandlerTestHelper {
    public static void setFilePath(NumberHandler handler, String filePath) throws Exception {
        Field field = NumberHandler.class.getDeclaredField("FILE_PATH");
        field.setAccessible(true);
        field.set(null, filePath);
    }
}