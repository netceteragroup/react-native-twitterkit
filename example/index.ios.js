/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View
} from 'react-native';

//import { NativeModules } from 'react-native';
//import { requireNativeComponent } from 'react-native';
import TwitterView from './TwitterView';

//var myVar = NativeModules.RNTwitterKitViewManager;

export default class example extends Component {
  
  render() {
  
  //myVar.setTweetID('846231685750439936');
  
    return (
      <View style={styles.container}>
      <Text style={styles.welcome}>
          Welcome to React Native TwitterKit example!
        </Text>
      <Text style={styles.instructions}>
          This is a simple tweet without picture
        </Text>
      <Text style={styles.instructions}>
          And this one is a tweet with a picture
        </Text>
      	<TwitterView 
      		style={styles.twitter2} 
      		tweetid={'873299426923601921'}
      		/>
        <Text style={styles.bottom}>
          You can tap on the tweet views in order to interract with them.
        </Text>
        <TwitterView 
      		style={styles.twitter2} 
      		tweetid={'260244087901413376'}
      		/>
      		<Text style={styles.bottom}>
          You can tap on the tweet views in order to interract with them.
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    //justifyContent: 'center',
    //alignItems: 'center',
    backgroundColor: '#E5ECEF',
  },
  welcome: {
    fontSize: 15,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
  	fontSize: 10,
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  twitter1: {
  },
  twitter2: {
  },
  top: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
    justifyContent: 'flex-start',
    alignItems: 'flex-start',
  },
  bottom: {
    //textAlign: 'center',
    color: '#333333',
    //marginBottom: 5,
    justifyContent: 'flex-end',
    alignItems: 'flex-end',
  }
});

AppRegistry.registerComponent('example', () => example);
