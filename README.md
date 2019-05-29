
# react-native-crypton

## Getting started

`$ npm install react-native-crypton --save`

### Mostly automatic installation

`$ react-native link react-native-crypton`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-crypton` and add `RNCrypton.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNCrypton.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNCryptonPackage;` to the imports at the top of the file
  - Add `new RNCryptonPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-crypton'
  	project(':react-native-crypton').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-crypton/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-crypton')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNCrypton.sln` in `node_modules/react-native-crypton/windows/RNCrypton.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Crypton.RNCrypton;` to the usings at the top of the file
  - Add `new RNCryptonPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNCrypton from 'react-native-crypton';

// TODO: What to do with the module?
RNCrypton;
```
  