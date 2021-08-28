package com.fishedee.reflection_boost;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExtractorTest {
    @Test
    public void basicTest(){
        GenericActualArgumentExtractor extractor = new GenericActualArgumentExtractor(MyTplImpl.class,MyTpl.class);

        assertEquals(extractor.getActualTypeLength(),2);
        assertEquals(extractor.getActualType(0),String.class);
        assertEquals(extractor.getActualType(1),Nothing2.class);


        assertEquals(extractor.getActualType("T1"),String.class);
        assertEquals(extractor.getActualType("T2"),Nothing2.class);
    }

    @Test
    public void extendTest(){
        GenericActualArgumentExtractor extractor = new GenericActualArgumentExtractor(MyTplImpl2.class,MyTpl.class);

        assertEquals(extractor.getActualTypeLength(),2);
        assertEquals(extractor.getActualType(0),String.class);
        assertEquals(extractor.getActualType(1),Nothing2.class);


        assertEquals(extractor.getActualType("T1"),String.class);
        assertEquals(extractor.getActualType("T2"),Nothing2.class);
    }

    @Test
    public void failExtendTest(){
        ReflectionBoostException e = assertThrows(ReflectionBoostException.class,()->{
            GenericActualArgumentExtractor extractor = new GenericActualArgumentExtractor(Nothing.class,MyTpl.class);
        });
        assertTrue(e.getMessage().contains("Chould not found genericInstanceType"));
    }

    @Test
    public void failActualArgument(){
        ReflectionBoostException e = assertThrows(ReflectionBoostException.class,()->{
            GenericActualArgumentExtractor extractor = new GenericActualArgumentExtractor(MyTplImpl2.class,MyTpl.class);
            assertEquals(extractor.getActualType("T3"),Nothing2.class);
        });
        assertTrue(e.getMessage().contains("Unknown Variable"));
    }
}
