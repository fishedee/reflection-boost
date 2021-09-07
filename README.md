![Release](https://jitpack.io/v/fishedee/reflection-boost.svg)
(https://jitpack.io/#fishedee/reflection-boost)

# reflection-boost

Java下的反射加强工具库，功能包括：

* 泛型计算，动态计算泛型类下的方法和字段的实际参数是什么

## 安装

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.fishedee</groupId>
    <artifactId>reflection-boost</artifactId>
    <version>1.1</version>
</dependency>
```

在项目的pom.xml加入以上配置即可

## 使用

```java
package com.fishedee.reflection_boost;

import java.util.List;
import java.util.Map;

public class MyTpl<T1, T2 extends Nothing> {

    public Map<T1,List<T2>> data;

    public void go(T1 a){

    }

    public void go2(T2 a, List<T2> b){

    }
}
```

一个复杂的泛型

```java
package com.fishedee.reflection_boost;

public class MyTplImpl  extends MyTpl<String,Long>{
}
```

一个泛型实现类

```java

public class ExtractorTest {
    @Test
    public void basicTest(){
        GenericActualArgumentExtractor extractor = new GenericActualArgumentExtractor(MyTplImpl.class,MyTpl.class);

        assertEquals(extractor.getActualTypeLength(),2);
        assertEquals(extractor.getActualType(0),String.class);
        assertEquals(extractor.getActualType(1),Long.class);


        assertEquals(extractor.getActualType("T1"),String.class);
        assertEquals(extractor.getActualType("T2"),Long.class);
    }
}
```

使用GenericActualArgumentExtractor，可以获取实际的泛型参数

```java
public class FillerTest {

    private GenericFormalArgumentFiller filler;
    private Class clazz;

    @BeforeEach
    public void init(){
        filler = new GenericFormalArgumentFiller(new GenericActualArgumentExtractor(MyTplImpl.class,MyTpl.class));
        clazz = MyTpl.class;
    }


    @Test
    public void goMethodTest() throws Exception{
        Method method = clazz.getMethod("go",Object.class);
        Type firstArgument = method.getGenericParameterTypes()[0];
        Type firstActualArgument = filler.fillType(firstArgument);
        assertEquals(firstActualArgument.getTypeName(),"java.lang.String");
    }
}
```

使用GenericFormalArgumentFiller，可以进行获取实际的范例实例



