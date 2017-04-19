/**
test tweets
510908133917487104
846231685750439936
260244087901413376
20		
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
          JS text header
        </Text>
		<RNTwitterKitView
			style={styles.twitter}
			tweetid={'846231685750439936'}/>			      
		<Text style={styles.instructions}>
          JS text footer
        </Text>
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
		backgroundColor: '#EEEEEE',
	},
	welcome: {
		fontSize: 20,
		textAlign: 'center',
		margin: 10,
	},
	instructions: {
		textAlign: 'center',
		color: '#FF0000',
		marginBottom: 5,
	},
	twitter: {
	  flex: 0,
      width: 400,
      height: 400,
	},
});

AppRegistry.registerComponent('example', () => example);
