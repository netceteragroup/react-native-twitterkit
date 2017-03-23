
# react-native-twitterkit

## Getting started

`$ npm install react-native-twitterkit --save`

### Mostly automatic installation

`$ react-native link react-native-twitterkit`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-twitterkit` and add `RNTwitterkit.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNTwitterkit.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
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

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNTwitterkit.sln` in `node_modules/react-native-twitterkit/windows/RNTwitterkit.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Com.Reactlibrary.RNTwitterkit;` to the usings at the top of the file
  - Add `new RNTwitterkitPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNTwitterkit from 'react-native-twitterkit';

// TODO: What to do with the module?
RNTwitterkit;
```
  