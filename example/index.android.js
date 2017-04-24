/**
test tweets
828627783119208449 - official tweet, taken from here: https://www.nzz.ch/sport/ski-wm-in-st-moritz-der-elektronische-russi-ld.143827
510908133917487104
846231685750439936
260244087901413376
20
1 - tweet that does not work
test on nexus 5
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
} from 'react-native';

import { TweetView } from './TwitterKit';

export default class example extends Component {

  render() {
    return (
      <View style={styles.container}>
	  	<Text style={styles.instructions}>
           Good Tweet
         </Text>
		<TweetView
			style={styles.twitter_that_works}
			tweetid={'828627783119208449'}/>
		<Text style={styles.instructions}>
           Bad Tweet
         </Text>
		<TweetView
			style={styles.twitter_that_doesnot_work}
			tweetid={'1'}/>
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
	instructions: {
		textAlign: 'center',
		color: '#000000',
	},
	twitter_that_works: {
	  flex: 0,
      width: 400,
      height: 325,
	},
	twitter_that_doesnot_work: {
	  flex: 0,
      width: 400,
      height: 300,
	},
});

AppRegistry.registerComponent('example', () => example);
