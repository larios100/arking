/**
 * @format
 */

//import {AppRegistry} from 'react-native';
import {Navigation} from 'react-native-navigation';
import App from './App';
import ContractsScreen from './screens/contract-screen';
import PrototypesScreen from './screens/prototypes-screen';
import LoginScreen from './screens/login-screen';
import PartScreen from './screens/part-screen';
import CameraComponent from './components/camera-component';
import OtisScreen from './screens/otis-screen';
import SettingsScreen from './screens/settings-screen';

//import {name as appName} from './app.json';

//AppRegistry.registerComponent(appName, () => App);
Navigation.registerComponent('CameraComponent', () => CameraComponent);
Navigation.registerComponent('Login', () => LoginScreen);
Navigation.registerComponent('Contracts', () => ContractsScreen);
Navigation.registerComponent('Prototypes', () => PrototypesScreen);
Navigation.registerComponent('Part', () => PartScreen);
Navigation.registerComponent('Otis', () => OtisScreen);
Navigation.registerComponent('Settings', () => SettingsScreen);
Navigation.setDefaultOptions({
  statusBar: {
    backgroundColor: '#315982',
  },
  topBar: {
    title: {
      color: 'white',
    },
    backButton: {
      color: 'white',
    },
    background: {
      color: '#315982',
    },
  },
  bottomTab: {
    fontSize: 14,
    selectedFontSize: 14,
  },
});
const mainRoot = {
  root: {
    bottomTabs: {
      children: [
        {
          stack: {
            children: [
              {
                component: {
                  name: 'Contracts',
                },
              },
            ],
          },
        },
        {
          stack: {
            children: [
              {
                component: {
                  name: 'Prototypes',
                },
              },
            ],
          },
        },
        {
          stack: {
            children: [
              {
                component: {
                  name: 'Otis',
                },
              },
            ],
          },
        },
        {
          stack: {
            children: [
              {
                component: {
                  name: 'Settings',
                },
              },
            ],
          },
        },
      ],
    },
  },
};
const loginRoot = {
  root: {
    component: {
      name: 'Login',
    },
  },
};
Navigation.events().registerAppLaunchedListener(async () => {
  Navigation.setRoot(isLoggedIn() ? mainRoot : loginRoot);
});

function isLoggedIn() {
  // TODO: your business logic goes here
  return true;
}
