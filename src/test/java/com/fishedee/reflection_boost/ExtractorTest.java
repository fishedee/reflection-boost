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
    public void noTplTest(){
        GenericActualArgumentExtractor extractor = new GenericActualArgumentExtractor(Nothing2.class,Nothing.class);

        assertEquals(extractor.getActualTypeLength(),0);

        GenericActualArgumentExtractor extractor2 = new GenericActualArgumentExtractor(Nothing.class,Nothing.class);

        assertEquals(extractor2.getActualTypeLength(),0);

        GenericActualArgumentExtractor extractor3 = new GenericActualArgumentExtractor(Nothing2.class,Nothing2.class);

        assertEquals(extractor3.getActualTypeLength(),0);
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
