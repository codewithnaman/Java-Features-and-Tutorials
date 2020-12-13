package com.example.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CounterInfoTest {

    @Test
    public void testGetInfo(){
        CounterInfo counterInfo = new CounterInfo();
        assertEquals("Test",counterInfo.getInfo());
    }

}
