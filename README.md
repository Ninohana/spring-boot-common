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

例如：

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
public class Response<T> {
    private Integer code;
    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> Response<T> ok(T data) {
        Response<T> response = new Response<>();
        response.setCode(200);
        response.setMsg("ok");
        response.setData(data);
        return response;
    }

    public static <T> Response<T> fail(T data) {
        Response<T> response = new Response<>();
        response.setCode(500);
        response.setMsg("fail");
        response.setData(data);
        return response;
    }
}
```

```yaml
# application.yml
metauchuu:
  wrapper:
    enable: true # 是否开启，默认不开启（false）
    response-template: com.metauchuu.model.Response # 修改成上面模板的全限定名，默认为空
    default-code: 200 # 接口返回代码
    default-msg: success # 接口返回信息
```
