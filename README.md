# spring-boot-common

基于SpringBoot3的通用工具集，为更快摸鱼而生。

# Maven Dependence

```xml
<dependency>
    <groupId>com.matauchuu</groupId>
    <artifactId>spring-boot-starter-common</artifactId>
    <version>0.0.3-SNAPSHOT</version>
</dependency>
```

# Feature

- @ResponseBodyWrapping

@ResponseBody的增强版，能自动包裹一层指定的对象，用于统一json格式的返回值。

```java
// 省略部分代码
    @GetMapping("/category")
    @ResponseBodyWrapping
    public List<Category> category() {
        return categoryService.listCategory();
    }
// 省略部分代码
```

接口返回（原本只有一个List，即data中的数据）：

```json
{
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 1,
            "name": "class1"
        },
        {
            "id": 2,
            "name": "class2"
        },
        {
            "id": 3,
            "name": "class3"
        },
        {
            "id": 4,
            "name": "class4"
        },
        {
            "id": 5,
            "name": "class5"
        }
    ]
}
```

用法：

```java
@Data
@ToString
@EqualsAndHashCode
public class R<T> {
    private Integer code;
    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setCode(200);
        r.setMsg("ok");
        r.setData(data);
        return r;
    }

    public static <T> R<T> fail(T data) {
        R<T> r = new R<>();
        r.setCode(500);
        r.setMsg("fail");
        r.setData(data);
        return r;
    }
}
```

```yaml
# application.yml
metauchuu:
  wrapper:
    enable: true # 是否开启，默认不开启（false）
    response-template: com.metauchuu.common.model.R # 修改成包装对象的全限定名，和上面模板字段必须一致，可增加新字段
    default-code: 200 # 接口返回代码
    default-msg: success # 接口返回信息
```

- GlobalExceptionHandler

全局异常处理。接口发生异常并且未捕获时，自动返回异常信息。

```java
...
    @GetMapping("/hello")
    @ResponseBodyWrapping
    public String hello() {
        System.out.println(1 / 0);
        return "Hi";
    }
...
```

请求返回

```json
{
    "code": 500,
    "msg": "fail",
    "data": "/ by zero"
}
```

用法：

```yaml
# application.yml
metauchuu:
  handler:
    global-exception: true
```