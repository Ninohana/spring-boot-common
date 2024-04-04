# spring-boot-common

基于SpringBoot3的通用工具集，为更快摸鱼而生。

# Maven Dependence

```xml
<dependency>
    <groupId>com.matauchuu</groupId>
    <artifactId>spring-boot-starter-common</artifactId>
    <version>{{pom.version}}</version>
</dependency>
```

# Feature

- **@ResponseBodyWrapping**

`@ResponseBody`的增强版，能自动包裹一层指定的对象，用于统一Json格式的返回值。

```java
// ...省略部分代码
    @GetMapping("/category")
    @ResponseBodyWrapping
    public List<Category> category() {
        return categoryService.listCategory();
    }
// ...省略部分代码
```

接口返回（原本只有一个List，即data中的数据）：

```json
{
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 1,
            "name": "category1"
        },
        {
            "id": 2,
            "name": "category2"
        },
        {
            "id": 3,
            "name": "category3"
        }
    ]
}
```

*用法：*

```yaml
# application.yml
metauchuu:
  wrapper:
    enable: true # 是否开启，默认不开启（false）
    response-template: com.metauchuu.common.model.R # 修改成包装对象的全限定名，和该模板字段必须一致，可增加新字段，不可减少原有字段
    default-code: 200 # 返回代码
    default-msg: success # 返回信息
```

- **@EnableExceptionHandle**

全局异常处理。接口发生异常并且未捕获时，返回异常信息。

```java
// ...
    @GetMapping("/hello")
    @ResponseBodyWrapping
    public String hello() {
        System.out.println(1 / 0);
        return "Hi";
    }
// ...
```

接口返回：

```json
{
    "code": 500,
    "msg": "/ by zero"
}
```

*用法：*

启动类上添加`@EnableExceptionHandle`

> 通常是`@SpringBootApplication`标注的类
