/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React, {useState} from 'react';
import {View} from 'react-native';
import ContractsScreen from './screens/contract-screen';
import {Icon, TabBar} from '@ant-design/react-native';
import PrototypesScreen from './screens/prototypes-screen';

function App(): JSX.Element {
  const [activeTab, setActiveTab] = useState('contracts');
  function onChangeTab(tab: any) {
    setActiveTab(tab);
  }
  return (
    <View>
      <TabBar
        unselectedTintColor="#949494"
        tintColor="#33A3F4"
        barTintColor="#f5f5f5">
        <TabBar.Item
          title="Contratos"
          icon={<Icon name="home" />}
          selected={activeTab === 'contracts'}
          onPress={() => onChangeTab('contracts')}>
          <ContractsScreen></ContractsScreen>
        </TabBar.Item>
        <TabBar.Item
          title="Modelos"
          icon={<Icon name="ordered-list" />}
          selected={activeTab === 'prototypes'}
          onPress={() => onChangeTab('prototypes')}>
          <PrototypesScreen></PrototypesScreen>
        </TabBar.Item>
      </TabBar>
    </View>
  );
}
App.options = {
  topBar: {
    title: {
      text: 'Arking',
      color: 'blue',
    },
    background: {
      color: '#4d089a',
    },
  },
};
export default App;
