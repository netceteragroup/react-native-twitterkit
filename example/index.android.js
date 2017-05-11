/**
test tweets
828627783119208449 - official tweet, taken from here: https://www.nzz.ch/sport/ski-wm-in-st-moritz-der-elektronische-russi-ld.143827
510908133917487104
846231685750439936
260244087901413376
20
1 - tweet that does not work
test on nexus 5
<Text style={styles.instructions}>
       Bad Tweet
     </Text>
<TweetView
  style={styles.twitter_that_doesnot_work}
  tweetid={'1'}/>

 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TextInput,
  Dimensions,
  DeviceEventEmitter,
  ScrollView
} from 'react-native';

import { TweetView } from './TwitterKit';

const screenWidth = Dimensions.get('window').width
const screenHeight = 0.9*screenWidth

export default class example extends Component {

  constructor(props){
    super(props);
    this.state = {
      text: '20'
    }
  }

  componentWillMount() {
    DeviceEventEmitter && DeviceEventEmitter.addListener('testEvent', (testText) => {
      this.setState({
        text: testText
      });
    });
  }

  testMethod = () => {
    this.setState({
      text: '50'
    });
  };

  render() {
    const height = parseInt(this.state.text);
    return (
      <ScrollView style={styles.container}>
	  	<Text style={styles.instructions}>
           Good Tweet 2
         </Text>
		  <TweetView
			style={{backgroundColor: 'white', height: height}}
			tweetid={'510908133917487104'}/>
      <TextInput
        onChangeText={(text) => this.setState({text})}
        value={this.state.text}
         />
      <View style={{backgroundColor: 'red', height: height}}></View>

      <Text>Test</Text>
      </ScrollView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
	/*container: {
		flex: 1,
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
		backgroundColor: '#EEEEEE',
	},*/
	instructions: {
		textAlign: 'center',
		color: '#000000',
	},
	twitter_that_works: {
    flex: 0,
    width: screenWidth,
  	height: 100,
    backgroundColor: '#FFFFFF',
	},
	twitter_that_doesnot_work: {
	  flex: 1,
      width: 400,
      height: 100,
	},
});

AppRegistry.registerComponent('example', () => example);
