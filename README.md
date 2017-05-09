# React Native Twitter Kit
[![License](https://img.shields.io/badge/license-MIT-blue.svg?style=flat)](https://github.com/netceteragroup/react-native-twitterkit/blob/master/LICENSE)

## Getting started

`$ npm install react-native-twitterkit --save`

### Mostly automatic installation

`$ react-native link react-native-twitterkit`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-twitterkit` and add `RNTwitterkit.xcodeproj`
3. In XCode, in the project navigator, select your project, and select the target. Add `libRNTwitterkit.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.netcetera.reactnative.twitterkit.RNTwitterkitPackage;` to the imports at the top of the file
  - Add `new RNTwitterkitPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-twitterkit'
  	project(':react-native-twitterkit').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-twitterkit/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-twitterkit')
  	```


## Usage
```javascript
import {TweetView} from 'react-native-twitterkit';

function Section({tweetId}) {
  return (
    <TweetView {...tweetId} />
  );
}
```
  
