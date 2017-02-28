# Genome Sample Android [ ![Download](https://api.bintray.com/packages/judopay/maven/device-dna/images/download.svg) ](https://bintray.com/judopay/maven/device-dna/_latestVersion)

The Device DNA Android library allows you to identify devices using the Judopay Genome service

## Getting Started

### Step 1: Initialize Device DNA

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

##### 2. Add Device DNA as a dependency in your app's build.gradle file:
```groovy
compile 'com.judopay:device-dna:0.5'
```

##### 3. Initialize Device DNA with your Judopay credentials:
```java
Credentials credentials = new Credentials("<TOKEN>", "<SECRET>");
DeviceDna deviceDna = new DeviceDna(this, credentials);
```

### Step 2: Identify a device

##### Call Device DNA to identify the device, this returns an RxJava ```Single<String>``` containing the deviceId:
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

## Server to server fraud prevention
Device DNA can be used for identifying the device for fraud prevention when performing server to server payments.

#### 1. Retrieve the device signals
Retrieve the "key" and "value" representing the device at the time the user triggers the action that will result in a server to server payment on your server:

```java
Map<String,String> deviceSignals = deviceDna.deviceSignals();
String key = deviceSignals.get("key");
String value = deviceSignals.get("value");
```

#### 2. Call to Device DNA to retrieve the deviceId:
```java
String deviceId = deviceDna.identifyDevice().toBlocking().value();
```

#### 3. Add the device identifiers when calling the Judo API:
```json
{
"clientDetails": {
    "key": "",
    "value": "",
    "deviceIdentifier" : "77dc2ee3-8d78-4051-b2ad-fb99e742d53d"
    }
}
```
