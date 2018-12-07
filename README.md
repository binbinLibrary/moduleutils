# 各种自定义工具
### 添加Gradle依赖
先在项目根目录的 build.gradle 的 repositories 添加:
```
     allprojects {
         repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
```
然后在dependencies添加:
```
        dependencies {
        ...
        implementation 'com.github.binbinLibrary:moduleutils:2.0.0'
        }
```
## Log配置
```
   UtilSettings.setDebugMode(true);
```