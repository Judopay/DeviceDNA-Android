# Genome Sample Android [ ![Download](https://api.bintray.com/packages/judopay/maven/device-dna/images/download.svg) ](https://bintray.com/judopay/maven/device-dna/_latestVersion)

The DeviceDNA Android library allows you to identify devices using the Judopay Genome service

## Getting Started

### Step 1: Initialize DeviceDNA

##### 1. Add the Judopay Maven repository to your root build.gradle file:
```groovy
allprojects {
    repositories {
        maven {
            url "http://dl.bintray.com/judopay/maven"
        }
    }
}
```

##### 2. Add DeviceDNA as a dependency in your app's build.gradle file:
```groovy
compile 'com.judopay:device-dna:0.1.1'
```

##### 3. Initialize DeviceDNA with your Judo account details:
```java
Credentials credentials = new Credentials("<TOKEN>", "<SECRET>");
DeviceDna deviceDna = new DeviceDna(this, credentials);
```

### Step 2: Identify a device

##### Call DeviceDNA to identify the device, this returns an RxJava ```Single<String>``` containing the deviceId:
```java
deviceDna.identifyDevice()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Action1<String>() {
            @Override
            public void call(String deviceId) {
                // unique deviceId
            }
        });
```

### Step 3: Check the device profile
##### Using the ```deviceId``` returned in step 2, call to retrieve the device profile
```java
deviceDna.getDeviceProfile(deviceId)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Action1<JsonObject>() {
            @Override
            public void call(JsonObject json) {
                String identityScore = json.get("IdentityScore").getAsString();
                String createdAt = json.get("CreatedAt").getAsString();
                String lastSeen = json.get("LastSeen").getAsString();
            }
        });
```
## Device signals for server to server fraud prevention
When performing server to server payments using the Judopay API, you may wish to identify the device at the time of payment. To obtain the device signals necessary for fraud prevention, use DeviceDNA to obtain the encrypted signals which will be passed in the ```clientDetails``` JSON field of the request body:

```java
Map<String,String> deviceSignals = deviceDna.deviceSignals();
String key = deviceSignals.get("key");
String value = deviceSignals.get("value");
```
