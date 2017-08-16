# AdReader-Android
 					
Steps for genarating Local artifact:

1. Open Terminal to navigate to AdReader directory.

1. run ./gradlew install command.

Note:- You need install maven for genarate artifact.
        
   (for every GitHub commit Jitpack will update new snapshot so you need to commit for changes in frame work.) 
         
### Steps for useing artifact:

#### 1  Add following lines in Build.gradle.
         
To use local maven repo Artifact :-
```
allprojects {
     repositories {
        jcenter()
        maven{
            url '../.m2/repository'   //add your local maven .m2 path
        }
    }
}
```
To use Artifact from Jitpack maven repo:-
```
            allprojects {
                repositories {
                        jcenter()
                        maven { url "https://jitpack.io" }
                        }
                    }
```
#### 2. Add Dependancy in app's gradle file:
For local repo :- 
```
 compile 'com.zelering.AdReader:AdReader:1.0'
```
For jitpack repo local:
 ```
 compile 'com.github.Zelering-Ltd:AdReader-Android:-SNAPSHOT'
```
Step 3: Add following line in your main activity
  ```java
  Config config= new Config()
 Config.Builder builder = config.Builder();
 builder.setHeading(R.string.app_description);
```
###### //You can use verious builder setter mathod to custumize UI.

### Following setter methods are available to customize UI:- 
---
```java 
builder.setToolbarColor(String toolbarColor) 
builder.setToolbarTextColor(String toolbarTextColor)
builder.setBackgroundColor(String backgroundColor)
builder.setTexColor(String texColor)
builder.setTitleColor(String titleColor)
builder.setButtonColor(String ButtonColor)
builder.setHeadingColor(String headingColor)
builder.setHomeTitle(int homeTitle)
builder.setScanImgTitle(int scanImgTitle)
builder.setHeading(int heading)
builder.setDiscription(int discription)
builder.setBtnStart(int btnStart)
builder.setAplicationLogo(int applicationLogo)
builder.setBackArrowColor(String backArrowColor) 
```
##### Note:- String are # code for color ex.#FFFFFF and int are drawable resources from Drawable ex.R.drawable.image.

---
```java
Intent intent = ActivityAdreadHome.CreateIntent(<YourActivity>.this, vuforia_lic_key,    AccessKey, SecretKey, builder.build());
startActivity(intent);
finish();
```
  
          
