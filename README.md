# Device DNA Android [ ![Download](https://api.bintray.com/packages/judopay/maven/device-dna/images/download.svg) ](https://bintray.com/judopay/maven/device-dna/_latestVersion)

The Device DNA Android library allows you to identify devices using the Judopay's platform.

## Getting Started

### Initialize Device DNA

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
compile 'com.judopay:device-dna:0.9+'
```

##### 3. Initialize Device DNA with your Judopay credentials:
```java
Credentials credentials = new Credentials("<TOKEN>", "<SECRET>");
DeviceDna deviceDna = new DeviceDna(this, credentials);
```

## Server to server fraud prevention
Device DNA can be used for identifying the device for fraud prevention when performing server to server payments.

Follow the steps for integrating Device DNA as per the [Getting Started guide](#getting-started).

#### 1. Retrieve the device signals
Retrieve the ```key``` and ```value``` from the device at the time the user triggers the action that will result in a payment request from your server:
```java
deviceDna.deviceSignals()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Action1<String>() {
            @Override
            public void call(Map<String, String> deviceSignals) {
                String key = deviceSignals.get("key");
                String value = deviceSignals.get("value");
            }
        });
```

#### 2. Call to Device DNA to retrieve the deviceId:
```java
deviceDna.identifyDevice()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Action1<String>() {
            @Override
            public void call(String deviceId) {
                // use deviceId
            }
        });
```

#### 3. Send the fields to your server and include with the payment request:
Add the ```key```, ```value``` and ```deviceIdentifer``` in the ```clientDetails``` field of the request.

Example JSON
```json
{
"clientDetails": {
    "key": "m815g6LdYB973ks9DbA==",
    "value": "fjfjLluVOT0wJ7cMO8vv00qsULrtd6Osio4Ra0mwKEpdK7YsbA==",
    "deviceIdentifier" : "77dc2ee3-8d78-4051-b2ad-fb99e742d53d"
    }
}
```
