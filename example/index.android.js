/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 tweetid={styles.image.width}
 backgroundColor: '#000000',
 image 50x50
 		flex: 0,
		width: 50,
		height: 50,	
				backgroundColor: '#0000FF',
				510908133917487104
				846231685750439936
				260244087901413376
				20
				<RNTwitterKitView 
			style={styles.image2}
			tweetid={'260244087901413376'}/>
        <Text style={styles.instructions}>
          To get started, edit index.android.js
        </Text>
        <Text style={styles.instructions}>
          Double tap R on your keyboard to reload,{'\n'}
          Shake device for dev menu
        </Text>
		
		<Text style={styles.instructions}>
          Tweet with entities
        </Text>
		<RNTwitterKitView 
			style={styles.image}
			tweetid={'846231685750439936'}/>		
			
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
} from 'react-native';

import RNTwitterKitView from './RNTwitterKitView';

export default class example extends Component {
  render() {
    return (
      <View style={styles.container}>
	  <Text style={styles.instructions}>
          Tweet with entities
        </Text>
		<RNTwitterKitView 
			style={styles.image}
			tweetid={'510908133917487104'}/>			      
      </View>
    );
  }
}

const styles = StyleSheet.create({
	container: {
		flex: 1,
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
		backgroundColor: '#F5FCFF',
	},
	welcome: {
		fontSize: 20,
		textAlign: 'center',
		margin: 10,
	},
	instructions: {
		textAlign: 'center',
		color: '#333333',
		marginBottom: 5,
	},
	image: {
		flex: 0,
      width: 400,
      height: 400,
	},
	image2: {
		flex: 0,
      width: 400,
      height: 400,
	}
});

AppRegistry.registerComponent('example', () => example);
