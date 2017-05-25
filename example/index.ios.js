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

import { TweetView } from './TwitterKit';

//var myVar = NativeModules.TweetViewManager;

export default class example extends Component {

  render() {

  //myVar.setTweetID('846231685750439936');

    return (
      <View style={styles.container}>
      <Text style={styles.welcome}>
          Welcome to React Native TwitterKit example!
        </Text>
      <Text style={styles.instructions}>
          This is a tweet without picture
        </Text>
      	<TweetView
      		style={styles.twitter1}
      		tweetid={'20'}
      		/>
      <Text style={styles.instructions}>
          And this one is a tweet with a picture
        </Text>
      	<TweetView
      		style={styles.twitter2}
      		tweetid={'846231685750439936'}
      		/>
        <Text style={styles.instructions}>
          You can tap on the tweet views in order to interract with them.
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
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
    flex: 0,
    width: 300,
  },
  twitter2: {
    flex: 0,
    width: 300,
  }
});

AppRegistry.registerComponent('example', () => example);
